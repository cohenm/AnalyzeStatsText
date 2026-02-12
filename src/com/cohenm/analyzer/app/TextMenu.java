package com.cohenm.analyzer.app;

import com.cohenm.analyzer.core.TextAnalyzer;
import com.cohenm.analyzer.model.WordCount;
import com.cohenm.analyzer.model.WordSort;
import com.cohenm.analyzer.ui.UserInput;
import com.cohenm.analyzer.ui.StatsPrinter;
import com.cohenm.analyzer.ui.ReportSaver;

import java.util.*;

/**
 * Klasa odpowiedzialna za obsługę interaktywnego menu aplikacji.
 * Pozwala użytkownikowi wykonywać różne operacje analizy tekstu,
 * takie jak wyświetlanie statystyk, filtrowanie słów, zapisywanie raportów
 * oraz konfiguracja parametrów analizy.
 *
 * <p>Menu współpracuje z obiektem {@link TextAnalyzer}, który wykonuje
 * właściwą analizę tekstu na podstawie pliku wskazanego przez użytkownika.
 * Dodatkowo korzysta z komponentów UI:</p>
 * <ul>
 *     <li>{@link UserInput} - obsługa wejścia użytkownika,</li>
 *     <li>{@link StatsPrinter} - wyświetlanie wyników analizy,</li>
 *     <li>{@link ReportSaver} - zapisywanie raportów do plików.</li>
 * </ul>
 *
 * <p>Klasa przechowuje również zestaw stop-words oraz minimalną długość słowa,
 * które mogą być dynamicznie zmieniane przez użytkownika.</p>
 *
 * <p>Główna metoda {@link #run()} uruchamia pętle menu i reaguje na wybory użytkownika.</p>
 */
public class TextMenu {

    private final TextAnalyzer analyzer;
    private final String path;

    private final UserInput input;
    private final StatsPrinter printer;
    private final ReportSaver saver;

    private final Set<String> stopWords = new HashSet<>(List.of(
            "i","oraz","że","to","w","na","z","do","się","jest","nie","a","o","po","u","ten","ta","to",
            "jak","który","która","które","te","dla","przy","albo","lub","czy","tam","tu","nad","pod",
            "od","bez","więc","co","tak","tylko","mnie","ciebie","jego","jej","ich"
    ));

    private int minWordLength = 2;

    /**
     * Tworzy nowe menu tekstowe powiązane z analizatorem oraz ścieżką pliku.
     * @param analyzer obiekt analizujący tekst
     * @param path ścieżka do pliku tekstowego
     * @param sc skaner używany do odczytu danych od użytkownika
     */
    public TextMenu(TextAnalyzer analyzer, String path, Scanner sc) {
        this.analyzer = analyzer;
        this.path = path;
        this.input = new UserInput(sc);
        this.printer = new StatsPrinter();
        this.saver = new ReportSaver(analyzer, input);
    }

    // ===================== MENU =====================

    /**
     * Uruchamia główną pętlę menu. Metoda wyświetla dostępne opcje,
     * odczytuje wybór użytkownika i wykonuje odpowiednie operacje.
     * Pętla trwa do momentu wybrania opcji zakończenia programu.
     */
    public void run() {
        while (true) {
            printMenu();
            switch (input.readLine().trim()) {
                case "1" -> printer.printBasic(analyzer, path);
                case "2" -> showTopWords();
                case "3" -> printer.printFrequencyFragment(analyzer, path, stopWords, minWordLength);
                case "4" -> minWordLength = input.askMinWordLength(minWordLength);
                case "5" -> toggleStopWords();
                case "6" -> saver.saveBasic(path, stopWords, minWordLength);
                case "7" -> saver.saveFull(path, stopWords, minWordLength);
                case "8" -> saver.saveFrequency(path, stopWords, minWordLength);
                case "0" -> { System.out.println("Koniec. Do zobaczenia!"); return; }
                default -> System.out.println("Nieznana opcja.");
            }
        }
    }


    // ===================== LOGIKA OPCJI =====================

    /**
     * Wyświetla listę najczęściej występujących słów.
     * Użytkownik podaje liczbę N oraz sposób sortowania.
     * Wyniki są drukowane przez {@link StatsPrinter}.
     */
    private void showTopWords() {
        int n = input.askInt("Podaj N", 20);
        WordSort sort = input.askSortMode();
        printer.printTop(analyzer, path, n, stopWords, minWordLength, sort);
    }


    /**
     * Włącza lub wyłącza filtr stop-words.
     * Jeśli lista stop-words jest pusta - zostaje ponownie załadowana.
     * Jeśli nie - zostaje wyczyszczona.
     */
    private void toggleStopWords() {
        if (stopWords.isEmpty()) {
            stopWords.addAll(List.of("i","oraz","że","to","w","na","z","do","się","jest","nie","a","o","po","u",
                    "ten","ta","to","jak","który","która","które","te","dla","przy","albo","lub",
                    "czy","tam","tu","nad","pod","od","bez","więc","co","tak","tylko","mnie",
                    "ciebie","jego","jej","ich"));
            System.out.println("Stop‑words: WŁĄCZONE");
        } else {
            stopWords.clear();
            System.out.println("Stop‑words: WYŁĄCZONE");
        }
    }

    private boolean stopWordsEnabled() {
        return !stopWords.isEmpty();
    }

    /**
     * Wyświetla tekstowe menu dostępnych opcji.
     */
    private void printMenu() {
        System.out.println("""
                
                === MENU ===
                1) Podstawowe statystyki
                2) Top N słów
                3) Fragment częstotliwości
                4) Zmień minWordLength
                5) Włącz/wyłącz stop‑words
                6) Zapisz podstawowe statystyki
                7) Zapisz pełne statystyki
                8) Zapisz częstotliwości słów
                0) Wyjście
                Wybór: """);
    }
}