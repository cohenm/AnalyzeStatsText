package com.cohenm.analyzer.model;

/**
 * Rekord przechowujący podstawowe statystyki tekstu obliczane przez
 * {@link com.cohenm.analyzer.core.TextAnalyzer}. Zawiera cztery kluczowe
 * metryki:
 * <ul>
 *     <li><b>charsWithSpaces</b> – liczba znaków łącznie ze spacjami,</li>
 *     <li><b>charsWithoutSpaces</b> – liczba znaków po usunięciu białych znaków,</li>
 *     <li><b>words</b> – liczba słów w tekście,</li>
 *     <li><b>sentences</b> – liczba zdań.</li>
 * </ul>
 *
 * <p>Rekord jest niezmienny (immutable) i służy jako prosty kontener danych
 * wykorzystywany przez analizator oraz formattery raportów.</p>
 *
 * <p>Metoda {@link #toString()} zwraca czytelny, tekstowy podgląd statystyk,
 * przydatny do debugowania lub wyświetlania w konsoli.</p>
 *
 * @see com.cohenm.analyzer.core.TextAnalyzer
 */
public record TextStats (
        int charsWithSpaces,
        int charsWithoutSpaces,
        int words,
        int sentences
) {

    /**
     * Zwraca czytelny, wieloliniowy opis statystyk tekstu.
     *
     * @return sformatowany tekstowy raport statystyk
     */
    @Override
    public String toString() {
        return """
                === STATYSTYKI ===
                Słowa: %d
                Znaki (ze spacjami): %d
                Znaki (bez spacji): %d
                Zdania: %d
                """.formatted(words, charsWithSpaces, charsWithoutSpaces, sentences);
    }
}
