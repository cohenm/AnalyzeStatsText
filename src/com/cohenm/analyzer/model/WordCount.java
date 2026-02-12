package com.cohenm.analyzer.model;

/**
 * Rekord reprezentujący pojedynczą parę: słowo oraz liczbę jego wystąpień.
 * Wykorzystywany m.in. przez {@link com.cohenm.analyzer.core.TextAnalyzer}
 * podczas generowania rankingów słów oraz przez formattery raportów.
 *
 * <p>Obiekt jest niezmienny (immutable) i służy jako prosty model danych
 * przekazywany między warstwami aplikacji.</p>
 *
 * @param word  słowo, którego dotyczy wpis
 * @param count liczba wystąpień słowa
 *
 * @see com.cohenm.analyzer.core.TextAnalyzer
 * @see com.cohenm.analyzer.model.WordSort
 */
public record WordCount (String word, int count) {
}
