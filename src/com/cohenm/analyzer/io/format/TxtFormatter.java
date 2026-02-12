package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.io.util.SortUtil;
import com.cohenm.analyzer.io.util.TimeUtil;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

/**
 * Implementacja interfejsu {@link Formatter} generująca raporty
 * w klasycznym formacie tekstowym (TXT). Raporty są przeznaczone
 * do czytelnego wyświetlania w konsoli lub zapisu do pliku.
 *
 * <p>Formatter udostępnia trzy typy raportów:
 * <ul>
 *     <li>raport podstawowy – ogólne statystyki tekstu,</li>
 *     <li>raport pełny – statystyki + częstotliwości słów,</li>
 *     <li>raport częstotliwości – tylko mapa słów.</li>
 * </ul>
 *
 * <p>Klasa korzysta z:
 * <ul>
 *     <li>{@link com.cohenm.analyzer.io.util.TimeUtil#now()} – do oznaczania czasu generowania raportu,</li>
 *     <li>{@link com.cohenm.analyzer.io.util.SortUtil#sorted(Map)} – do sortowania częstotliwości słów.</li>
 * </ul>
 * </p>
 *
 * <p>Format wyjściowy jest przejrzysty i przyjazny dla użytkownika,
 * z nagłówkami i wyrównaniem kolumn dla czytelności.</p>
 *
 * @see Formatter
 * @see TextStats
 * @see com.cohenm.analyzer.io.util.SortUtil
 * @see com.cohenm.analyzer.io.util.TimeUtil
 */
public class TxtFormatter implements Formatter {

    /**
     * Separator linii zależny od systemu operacyjnego.
     */
    private static final String NL = System.lineSeparator();

    /**
     * Generuje podstawowy raport tekstowy zawierający ogólne statystyki.
     *
     * <p>Przykład:</p>
     * <pre>
     * === Podstawowe statystyki ===
     * Wygenerowano: 2025-01-01T12:00:00
     * Słowa: 123
     * Znaki (ze spacjami): 456
     * Znaki (bez spacji): 321
     * Zdania: 10
     * </pre>
     *
     * @param s obiekt statystyk tekstu
     * @return raport w formacie TXT
     */
    @Override
    public String basic(TextStats s) {
        return "=== Podstawowe statystyki ===" + NL +
                "Wygenerowano: " + TimeUtil.now() + NL +
                "Słowa: " + s.words() + NL +
                "Znaki (ze spacjami): " + s.charsWithSpaces() + NL +
                "Znaki (bez spacji): " + s.charsWithoutSpaces() + NL +
                "Zdania: " + s.sentences() + NL;
    }

    /**
     * Generuje pełny raport tekstowy zawierający statystyki oraz
     * posortowaną listę słów wraz z ich częstotliwościami.
     *
     * <p>Przykład fragmentu:</p>
     * <pre>
     * === Częstotliwość słów ===
     * ala                  : 5
     * kot                  : 3
     * </pre>
     *
     * @param s    statystyki tekstu
     * @param freq mapa słowo → liczba wystąpień
     * @return raport w formacie TXT
     */
    @Override
    public String full(TextStats s, Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder();
        sb.append(basic(s)).append(NL);
        sb.append("=== Częstotliwość słów ===").append(NL);

        for (var e : SortUtil.sorted(freq)) {
            sb.append(String.format("%-20s : %d", e.getKey(), e.getValue()))
                    .append(NL);
        }
        return sb.toString();
    }

    /**
     * Generuje raport tekstowy zawierający wyłącznie częstotliwości słów.
     *
     * @param freq mapa słowo → liczba wystąpień
     * @return raport w formacie TXT
     */
    @Override
    public String frequency(Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Częstotliwość słów ===").append(NL);

        for (var e : SortUtil.sorted(freq)) {
            sb.append(String.format("%-20s : %d", e.getKey(), e.getValue()))
                    .append(NL);
        }
        return sb.toString();
    }
}
