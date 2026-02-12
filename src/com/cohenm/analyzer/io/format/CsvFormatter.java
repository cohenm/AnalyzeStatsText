package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.io.util.Escape;
import com.cohenm.analyzer.io.util.SortUtil;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

/**
 * Implementacja interfejsu {@link Formatter} generująca raporty
 * w formacie CSV. Klasa tworzy trzy typy raportów:
 * <ul>
 *     <li>raport podstawowy (statystyki tekstu),</li>
 *     <li>raport pełny (statystyki + częstotliwości słów),</li>
 *     <li>raport częstotliwości (tylko mapa słów).</li>
 * </ul>
 *
 * <p>Wszystkie dane są formatowane zgodnie z zasadami CSV:
 * wartości są oddzielane przecinkami, a słowa są odpowiednio
 * escapowane za pomocą {@link com.cohenm.analyzer.io.util.Escape#csv(String)}.
 * Częstotliwości są sortowane przy użyciu {@link com.cohenm.analyzer.io.util.SortUtil}.</p>
 *
 * <p>Klasa nie przechowuje stanu — wszystkie operacje są czysto
 * funkcyjne i deterministyczne.</p>
 *
 * @see Formatter
 * @see TextStats
 * @see com.cohenm.analyzer.io.util.SortUtil
 * @see com.cohenm.analyzer.io.util.Escape
 */
public class CsvFormatter implements Formatter {

    /**
     * Generuje podstawowy raport CSV zawierający ogólne statystyki tekstu.
     * Zwracany format:
     *
     * <pre>
     * metric,value
     * words,123
     * chars_with_spaces,456
     * chars_without_spaces,321
     * sentences,10
     * </pre>
     *
     * @param s obiekt statystyk tekstu
     * @return raport CSV jako String
     */
    @Override
    public String basic(TextStats s) {
        return """ 
                metric,value 
                words,%d 
                chars_with_spaces,%d 
                chars_without_spaces,%d 
                sentences,%d """.formatted(
                        s.words(),
                s.charsWithSpaces(),
                s.charsWithoutSpaces(),
                s.sentences()
        );
    }

    /**
     * Generuje pełny raport CSV zawierający statystyki oraz
     * posortowaną listę słów wraz z ich częstotliwościami.
     *
     * <p>Format:</p>
     * <pre>
     * metric,value
     * ...
     * word,count
     * ala,5
     * kot,3
     * ...
     * </pre>
     *
     * @param s    statystyki tekstu
     * @param freq mapa słowo → liczba wystąpień
     * @return raport CSV jako String
     */
    @Override
    public String full(TextStats s, Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder(basic(s));
        sb.append("\nword,count\n");
        for (var e : SortUtil.sorted(freq)) {
            sb.append(Escape.csv(e.getKey())).append(",").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Generuje raport CSV zawierający wyłącznie częstotliwości słów.
     *
     * <p>Format:</p>
     * <pre>
     * word,count
     * ala,5
     * kot,3
     * ...
     * </pre>
     *
     * @param freq mapa słowo → liczba wystąpień
     * @return raport CSV jako String
     */
    @Override
    public String frequency(Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder("word,count\n");
        for (var e : SortUtil.sorted(freq)) {
            sb.append(Escape.csv(e.getKey())).append(",").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}