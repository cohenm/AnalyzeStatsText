package com.cohenm.analyzer.ui;

import com.cohenm.analyzer.core.TextAnalyzer;
import com.cohenm.analyzer.model.TextStats;
import com.cohenm.analyzer.model.WordCount;
import com.cohenm.analyzer.model.WordSort;

import java.util.*;

/**
 * Klasa odpowiedzialna za prezentację wyników analizy tekstu w konsoli.
 * Udostępnia metody wyświetlające:
 * <ul>
 *     <li>podstawowe statystyki tekstu,</li>
 *     <li>listę najczęściej występujących słów (Top N),</li>
 *     <li>fragment mapy częstotliwości (pierwsze 50 pozycji).</li>
 * </ul>
 *
 * <p>StatsPrinter pełni rolę warstwy UI — nie zapisuje danych do plików
 * ani nie generuje raportów, lecz jedynie prezentuje wyniki użytkownikowi.
 * Dane są pobierane z {@link TextAnalyzer}, a błędy odczytu pliku są
 * obsługiwane w sposób przyjazny dla użytkownika.</p>
 *
 * @see TextAnalyzer
 * @see WordCount
 * @see WordSort
 */
public class StatsPrinter {

    // ===================== PODSTAWOWE STATYSTYKI =====================

    /**
     * Wyświetla podstawowe statystyki tekstu w konsoli.
     * W przypadku błędu odczytu pliku wypisywany jest komunikat błędu.
     *
     * @param analyzer analizator tekstu
     * @param path     ścieżka do pliku wejściowego
     */
    public void printBasic(TextAnalyzer analyzer, String path) {
        try {
            TextStats stats = analyzer.analyzeFile(path);

            System.out.println("=== STATYSTYKI ===");
            System.out.println("Słowa: " + stats.words());
            System.out.println("Znaki (ze spacjami): " + stats.charsWithSpaces());
            System.out.println("Znaki (bez spacji): " + stats.charsWithoutSpaces());
            System.out.println("Zdania: " + stats.sentences());

        } catch (Exception e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }

    // ===================== TOP N SŁÓW =====================

    /**
     * Wyświetla listę najczęściej występujących słów (Top N), posortowaną
     * zgodnie z trybem określonym przez {@link WordSort}.
     *
     * @param analyzer      analizator tekstu
     * @param path          ścieżka do pliku wejściowego
     * @param n             liczba słów do wyświetlenia
     * @param stopWords     zbiór słów do pominięcia (opcjonalnie)
     * @param minWordLength minimalna długość słowa
     * @param sortMode      tryb sortowania
     */
    public void printTop(TextAnalyzer analyzer,
                         String path,
                         int n,
                         Set<String> stopWords,
                         int minWordLength,
                         WordSort sortMode) {

        try {
            List<WordCount> top = analyzer.topWordsFromFile(
                    path,
                    n,
                    stopWords != null && !stopWords.isEmpty() ? stopWords : null,
                    minWordLength,
                    sortMode
            );

            System.out.println("=== TOP " + n + " słów — sortowanie: " + sortMode + " ===");
            for (WordCount wc : top) {
                System.out.printf("%-20s : %d%n", wc.word(), wc.count());
            }

        } catch (Exception e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }

    // ===================== FRAGMENT CZĘSTOTLIWOŚCI =====================

    /**
     * Wyświetla fragment mapy częstotliwości słów — maksymalnie pierwsze 50 pozycji,
     * posortowane malejąco po liczbie wystąpień, a następnie alfabetycznie.
     *
     * @param analyzer      analizator tekstu
     * @param path          ścieżka do pliku wejściowego
     * @param stopWords     zbiór słów do pominięcia (opcjonalnie)
     * @param minWordLength minimalna długość słowa
     */
    public void printFrequencyFragment(TextAnalyzer analyzer,
                                       String path,
                                       Set<String> stopWords,
                                       int minWordLength) {

        try {
            Map<String, Integer> freq = analyzer.wordFrequencyFromFile(
                    path,
                    stopWords != null && !stopWords.isEmpty() ? stopWords : null,
                    minWordLength
            );

            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(freq.entrySet());
            sorted.sort(Map.Entry.<String, Integer>comparingByValue().reversed()
                    .thenComparing(Map.Entry::getKey));

            int limit = Math.min(50, sorted.size());
            System.out.println("=== Częstotliwości (pierwsze " + limit + ") ===");

            for (int i = 0; i < limit; i++) {
                var e = sorted.get(i);
                System.out.printf("%-20s : %d%n", e.getKey(), e.getValue());
            }

            if (sorted.size() > limit) {
                System.out.println("... (razem: " + sorted.size() + ")");
            }

        } catch (Exception e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }
}
