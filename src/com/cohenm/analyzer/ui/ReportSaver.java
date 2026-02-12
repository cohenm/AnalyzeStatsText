package com.cohenm.analyzer.ui;

import com.cohenm.analyzer.core.TextAnalyzer;
import com.cohenm.analyzer.io.ReportWriter;
import com.cohenm.analyzer.model.TextStats;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Klasa odpowiedzialna za interaktywny zapis raportów na podstawie danych
 * dostarczanych przez {@link TextAnalyzer}. Współpracuje z komponentem
 * {@link UserInput}, który pobiera od użytkownika format raportu oraz
 * ścieżkę wyjściową.
 *
 * <p>ReportSaver udostępnia trzy główne operacje:</p>
 * <ul>
 *     <li>zapis podstawowych statystyk,</li>
 *     <li>zapis pełnych statystyk (statystyki + częstotliwości słów),</li>
 *     <li>zapis samej częstotliwości słów.</li>
 * </ul>
 *
 * <p>Każda metoda:</p>
 * <ol>
 *     <li>pyta użytkownika o format raportu,</li>
 *     <li>proponuje domyślną nazwę pliku,</li>
 *     <li>wykonuje zapis poprzez {@link ReportWriter},</li>
 *     <li>obsługuje błędy zapisu w sposób przyjazny użytkownikowi.</li>
 * </ol>
 *
 * <p>Klasa pełni rolę „warstwy UI” dla operacji zapisu raportów.</p>
 *
 * @see TextAnalyzer
 * @see ReportWriter
 * @see UserInput
 */
public class ReportSaver {

    private final TextAnalyzer analyzer;
    private final UserInput input;

    /**
     * Tworzy obiekt odpowiedzialny za zapisywanie raportów z użyciem
     * wskazanego analizatora oraz komponentu wejścia użytkownika.
     *
     * @param analyzer analizator tekstu
     * @param input    komponent pobierający dane od użytkownika
     */
    public ReportSaver(TextAnalyzer analyzer, UserInput input) {
        this.analyzer = analyzer;
        this.input = input;
    }

    // ===================== PUBLICZNE METODY ZAPISU =====================

    /**
     * Zapisuje podstawowe statystyki tekstu do pliku w formacie wybranym
     * przez użytkownika. Nazwa pliku jest generowana automatycznie na
     * podstawie nazwy wejściowego pliku oraz typu raportu.
     *
     * @param path          ścieżka do pliku wejściowego
     * @param stopWords     zbiór słów do pominięcia (opcjonalnie)
     * @param minWordLength minimalna długość słowa
     */
    public void saveBasic(String path, Set<String> stopWords, int minWordLength) {
        ReportWriter.Format format = input.askFormat();
        Path out = input.askOutputPath(defaultName(path, "basic_stats", format));

        saveReport(out, () -> {
            TextStats stats = analyzer.analyzeFile(path);
            ReportWriter.writeBasicStats(stats, out, format);
        });
    }

    /**
     * Zapisuje pełny raport zawierający statystyki oraz częstotliwości słów.
     *
     * @param path          ścieżka do pliku wejściowego
     * @param stopWords     zbiór słów do pominięcia (opcjonalnie)
     * @param minWordLength minimalna długość słowa
     */
    public void saveFull(String path, Set<String> stopWords, int minWordLength) {
        ReportWriter.Format format = input.askFormat();
        Path out = input.askOutputPath(defaultName(path, "full_stats", format));

        saveReport(out, () -> {
            TextStats stats = analyzer.analyzeFile(path);
            Map<String,Integer> freq = analyzer.wordFrequencyFromFile(
                    path,
                    stopWords != null && !stopWords.isEmpty() ? stopWords : null,
                    minWordLength
            );
            ReportWriter.writeFullStats(stats, freq, out, format);
        });
    }

    /**
     * Zapisuje raport zawierający wyłącznie częstotliwości słów.
     *
     * @param path          ścieżka do pliku wejściowego
     * @param stopWords     zbiór słów do pominięcia (opcjonalnie)
     * @param minWordLength minimalna długość słowa
     */
    public void saveFrequency(String path, Set<String> stopWords, int minWordLength) {
        ReportWriter.Format format = input.askFormat();
        Path out = input.askOutputPath(defaultName(path, "word_frequency", format));

        saveReport(out, () -> {
            Map<String,Integer> freq = analyzer.wordFrequencyFromFile(
                    path,
                    stopWords != null && !stopWords.isEmpty() ? stopWords : null,
                    minWordLength
            );
            ReportWriter.writeWordFrequency(freq, out, format);
        });
    }

    // ===================== WSPÓLNY MECHANIZM ZAPISU =====================

    /**
     * Funkcyjny interfejs reprezentujący operację wejścia/wyjścia,
     * która może zgłosić wyjątek.
     */
    @FunctionalInterface
    private interface IOAction {
        void run() throws Exception;
    }

    /**
     * Wykonuje operację zapisu raportu, obsługując błędy w sposób
     * przyjazny użytkownikowi.
     *
     * @param out    ścieżka docelowa
     * @param action operacja zapisu
     */
    private void saveReport(Path out, IOAction action) {
        try {
            action.run();
            System.out.println("Zapisano: " + out.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Błąd zapisu: " + e.getMessage());
        }
    }

    // ===================== GENEROWANIE NAZW PLIKÓW =====================

    /**
     * Generuje domyślną nazwę pliku raportu na podstawie:
     * <ul>
     *     <li>nazwy pliku wejściowego,</li>
     *     <li>typu raportu (np. basic_stats),</li>
     *     <li>formatu wyjściowego.</li>
     * </ul>
     *
     * @param inputPath ścieżka wejściowa
     * @param base      typ raportu
     * @param f         format raportu
     * @return proponowana nazwa pliku
     */
    private String defaultName(String inputPath, String base, ReportWriter.Format f) {
        String ext = switch (f) {
            case CSV -> "csv";
            case TXT -> "txt";
            case JSON -> "json";
            case XML -> "xml";
        };

        String stem = stripTxtSuffix(inputPath);
        return stem + "-" + base + "." + ext;
    }

    /**
     * Usuwa końcówkę ".txt" z nazwy pliku, jeśli występuje.
     *
     * @param p nazwa pliku
     * @return nazwa bez rozszerzenia .txt
     */
    private String stripTxtSuffix(String p) {
        if (p != null && p.toLowerCase(Locale.ROOT).endsWith(".txt")) {
            return p.substring(0, p.length() - 4);
        }
        return p;
    }
}