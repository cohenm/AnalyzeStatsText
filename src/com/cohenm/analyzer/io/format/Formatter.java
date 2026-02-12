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


    String formatBasic(TextStats stats);
    String formatFull(TextStats stats, Map<String,Integer> freq);
    String formatFrequency(Map<String,Integer> freq);

}