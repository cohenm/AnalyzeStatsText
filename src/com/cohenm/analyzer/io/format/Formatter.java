package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

/**
 * Interfejs definiujący kontrakt dla formatterów odpowiedzialnych
 * za generowanie raportów tekstowych na podstawie statystyk oraz
 * map częstotliwości słów. Implementacje mogą tworzyć raporty
 * w różnych formatach (np. tekstowych, markdown, CSV), zachowując
 * wspólny zestaw metod.
 *
 * <p>Formatter udostępnia trzy podstawowe operacje:</p>
 * <ul>
 *     <li>tworzenie raportu podstawowego,</li>
 *     <li>tworzenie raportu pełnego (statystyki + częstotliwości),</li>
 *     <li>tworzenie raportu zawierającego wyłącznie częstotliwości słów.</li>
 * </ul>
 *
 * <p>Interfejs jest wykorzystywany m.in. przez {@link com.cohenm.analyzer.io.builder.ReportBuilder},
 * który deleguje do niego generowanie treści raportów.</p>
 *
 * @see TextStats
 * @see com.cohenm.analyzer.io.builder.ReportBuilder
 */

public interface Formatter {

    /**
     * Generuje podstawowy raport zawierający ogólne statystyki tekstu.
     *
     * @param textStats obiekt statystyk tekstu
     * @return sformatowany raport tekstowy
     */
    String basic(TextStats textStats);

    /**
     * Generuje pełny raport zawierający statystyki oraz mapę
     * częstotliwości słów.
     *
     * @param textStats statystyki tekstu
     * @param freq      mapa słowo → liczba wystąpień
     * @return sformatowany raport tekstowy
     */
    String full(TextStats textStats, Map<String, Integer> freq);

    /**
     * Generuje raport zawierający wyłącznie częstotliwości słów.
     *
     * @param freq mapa słowo → liczba wystąpień
     * @return sformatowany raport tekstowy
     */
    String frequency(Map<String, Integer> freq);
}