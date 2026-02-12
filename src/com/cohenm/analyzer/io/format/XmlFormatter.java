package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.io.util.Escape;
import com.cohenm.analyzer.io.util.SortUtil;
import com.cohenm.analyzer.io.util.TimeUtil;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

/**
 * Implementacja interfejsu {@link Formatter} generująca raporty
 * w formacie XML. Klasa tworzy trzy typy raportów:
 * <ul>
 *     <li>raport podstawowy – ogólne statystyki tekstu,</li>
 *     <li>raport pełny – statystyki + częstotliwości słów,</li>
 *     <li>raport częstotliwości – tylko mapa słów.</li>
 * </ul>
 *
 * <p>Wszystkie raporty zawierają atrybut <code>generatedAt</code>,
 * którego wartość jest pobierana z {@link com.cohenm.analyzer.io.util.TimeUtil#now()}.
 * Klasa dba o poprawne escapowanie znaków XML za pomocą
 * {@link com.cohenm.analyzer.io.util.Escape#xml(String)}.</p>
 *
 * <p>Elementy częstotliwości są sortowane przy użyciu
 * {@link com.cohenm.analyzer.io.util.SortUtil#sorted(Map)} i zapisywane
 * jako elementy <code>&lt;item&gt;</code> z atrybutami <code>word</code>
 * oraz <code>count</code>.</p>
 *
 * <p>XML jest generowany ręcznie, bez użycia bibliotek zewnętrznych,
 * co zapewnia pełną kontrolę nad strukturą dokumentu.</p>
 *
 * @see Formatter
 * @see TextStats
 * @see com.cohenm.analyzer.io.util.SortUtil
 * @see com.cohenm.analyzer.io.util.Escape
 * @see com.cohenm.analyzer.io.util.TimeUtil
 */
public class XmlFormatter implements Formatter {

    /**
     * Generuje podstawowy raport XML zawierający ogólne statystyki tekstu.
     *
     * <p>Przykład struktury:</p>
     * <pre>
     * &lt;report type="basic_stats" generatedAt="2025-01-01T12:00:00"&gt;
     *   &lt;stats&gt;
     *     &lt;words&gt;123&lt;/words&gt;
     *     &lt;charsWithSpaces&gt;456&lt;/charsWithSpaces&gt;
     *     &lt;charsWithoutSpaces&gt;321&lt;/charsWithoutSpaces&gt;
     *     &lt;sentences&gt;10&lt;/sentences&gt;
     *   &lt;/stats&gt;
     * &lt;/report&gt;
     * </pre>
     *
     * @param s statystyki tekstu
     * @return raport XML jako String
     */
    @Override
    public String basic(TextStats s) {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <report type="basic_stats" generatedAt="%s">
                  <stats>
                    <words>%d</words>
                    <charsWithSpaces>%d</charsWithSpaces>
                    <charsWithoutSpaces>%d</charsWithoutSpaces>
                    <sentences>%d</sentences>
                  </stats>
                </report>
                """.formatted(
                Escape.xml(TimeUtil.now()),
                s.words(),
                s.charsWithSpaces(),
                s.charsWithoutSpaces(),
                s.sentences()
        );
    }

    /**
     * Generuje pełny raport XML zawierający statystyki oraz
     * posortowaną mapę częstotliwości słów.
     *
     * <p>Każde słowo jest reprezentowane jako element:</p>
     * <pre>
     * &lt;item word="ala" count="5"/&gt;
     * </pre>
     *
     * @param s    statystyki tekstu
     * @param freq mapa słowo → liczba wystąpień
     * @return raport XML jako String
     */
    @Override
    public String full(TextStats s, Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<report type=\"full_stats\" generatedAt=\"")
                .append(Escape.xml(TimeUtil.now()))
                .append("\">\n");

        sb.append("  <stats>\n");
        sb.append("    <words>").append(s.words()).append("</words>\n");
        sb.append("    <charsWithSpaces>").append(s.charsWithSpaces()).append("</charsWithSpaces>\n");
        sb.append("    <charsWithoutSpaces>").append(s.charsWithoutSpaces()).append("</charsWithoutSpaces>\n");
        sb.append("    <sentences>").append(s.sentences()).append("</sentences>\n");
        sb.append("  </stats>\n");

        sb.append("  <frequency>\n");
        for (var e : SortUtil.sorted(freq)) {
            sb.append("    <item word=\"")
                    .append(Escape.xml(e.getKey()))
                    .append("\" count=\"")
                    .append(e.getValue())
                    .append("\"/>\n");
        }
        sb.append("  </frequency>\n");

        sb.append("</report>\n");
        return sb.toString();
    }

    /**
     * Generuje raport XML zawierający wyłącznie częstotliwości słów.
     *
     * @param freq mapa słowo → liczba wystąpień
     * @return raport XML jako String
     */
    @Override
    public String frequency(Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<report type=\"word_frequency\" generatedAt=\"")
                .append(Escape.xml(TimeUtil.now()))
                .append("\">\n");

        sb.append("  <frequency>\n");
        for (var e : SortUtil.sorted(freq)) {
            sb.append("    <item word=\"")
                    .append(Escape.xml(e.getKey()))
                    .append("\" count=\"")
                    .append(e.getValue())
                    .append("\"/>\n");
        }
        sb.append("  </frequency>\n");

        sb.append("</report>\n");
        return sb.toString();
    }
}