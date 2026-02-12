package com.cohenm.analyzer.io.builder;

import com.cohenm.analyzer.io.format.Formatter;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

/**
 * Klasa pomocnicza odpowiedzialna za budowanie raportów tekstowych
 * na podstawie statystyk oraz map częstotliwości słów. Pełni rolę
 * warstwy delegującej - wszystkie operacje formatowania są przekazywane
 * do obiektu {@link Formatter}, który definiuje sposób prezentacji danych.
 *
 * <p>ReportBuilder udostępnia trzy statyczne metody odpowiadające
 * trzem typom raportów.</p>
 * <ul>
 *     <li>raport podstawowy (statystyki ogólne),</li>
 *     <li>raport pełny (statystyki + częstotliwości słów),</li>
 *     <li>raport częstotliwości (tylko mapa słów).</li>
 * </ul>
 *
 * <p>Klasa nie przechowuje stanu - wszystkie metody są statyczne,
 * co pozwala na wygodne użycie w innych komponentach aplikacji.</p>
 *
 * @see Formatter
 * @see TextStats
 */

public class ReportBuilder {

    /**
     * Buduje podstawowy raport zawierający ogólne statystyki tekstu.
     *
     * @param stats obiekt statystyk tekstu
     * @param f     formatter odpowiedzialny za generowanie treści raportu
     * @return sformatowany raport tekstowy
     */
    public static String basic(TextStats stats, Formatter f) {
        return f.basic(stats);
    }

    /**
     * Buduje pełny raport zawierający statystyki oraz mapę częstotliwości słów.
     *
     * @param stats obiekt statystyk tekstu
     * @param freq  mapa słowo → liczba wystąpień
     * @param f     formatter odpowiedzialny za generowanie treści raportu
     * @return sformatowany raport tekstowy
     */
    public static String full(TextStats stats, Map<String, Integer> freq, Formatter f) {
        return f.full(stats, freq);
    }

    /**
     * Buduje raport zawierający wyłącznie częstotliwości słów.
     *
     * @param freq mapa słowo → liczba wystąpień
     * @param f    formatter odpowiedzialny za generowanie treści raportu
     * @return sformatowany raport tekstowy
     */
    public static String frequency(Map<String, Integer> freq, Formatter f) {
        return f.frequency(freq);
    }

}
