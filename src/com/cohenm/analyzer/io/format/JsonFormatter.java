package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.io.util.Escape;
import com.cohenm.analyzer.io.util.SortUtil;
import com.cohenm.analyzer.io.util.TimeUtil;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

/**
 * Implementacja interfejsu {@link Formatter} generująca raporty
 * w formacie JSON. Klasa tworzy trzy typy raportów:
 * <ul>
 *     <li>raport podstawowy (statystyki tekstu),</li>
 *     <li>raport pełny (statystyki + częstotliwości słów),</li>
 *     <li>raport częstotliwości (tylko mapa słów).</li>
 * </ul>
 *
 * <p>Wszystkie raporty zawierają pole <code>generatedAt</code>,
 * które określa czas wygenerowania raportu, pobierany z
 * {@link com.cohenm.analyzer.io.util.TimeUtil#now()}.</p>
 *
 * <p>Klasa wykorzystuje:
 * <ul>
 *     <li>{@link com.cohenm.analyzer.io.util.SortUtil} – do sortowania map częstotliwości,</li>
 *     <li>{@link com.cohenm.analyzer.io.util.Escape#json(String)} – do bezpiecznego
 *         escapowania kluczy JSON.</li>
 * </ul>
 * </p>
 *
 * <p>JSON jest generowany ręcznie (bez bibliotek zewnętrznych), co zapewnia
 * pełną kontrolę nad formatem i strukturą danych.</p>
 *
 * @see Formatter
 * @see TextStats
 * @see com.cohenm.analyzer.io.util.SortUtil
 * @see com.cohenm.analyzer.io.util.Escape
 * @see com.cohenm.analyzer.io.util.TimeUtil
 */
public class JsonFormatter implements Formatter {

    /**
     * Generuje podstawowy raport JSON zawierający ogólne statystyki tekstu.
     *
     * <p>Przykładowy format:</p>
     * <pre>
     * {
     *   "type": "basic_stats",
     *   "generatedAt": "2025-01-01T12:00:00",
     *   "stats": {
     *     "words": 123,
     *     "charsWithSpaces": 456,
     *     "charsWithoutSpaces": 321,
     *     "sentences": 10
     *   }
     * }
     * </pre>
     *
     * @param s obiekt statystyk tekstu
     * @return raport JSON jako String
     */
    @Override
    public String basic(TextStats s) {
        return """
                {
                  "type": "basic_stats",
                  "generatedAt": "%s",
                  "stats": {
                    "words": %d,
                    "charsWithSpaces": %d,
                    "charsWithoutSpaces": %d,
                    "sentences": %d
                  }
                }
                """.formatted(
                TimeUtil.now(),
                s.words(),
                s.charsWithSpaces(),
                s.charsWithoutSpaces(),
                s.sentences()
        );
    }

    /**
     * Generuje pełny raport JSON zawierający statystyki oraz
     * posortowaną mapę częstotliwości słów.
     *
     * <p>Przykład struktury:</p>
     * <pre>
     * {
     *   "type": "full_stats",
     *   "generatedAt": "...",
     *   "stats": { ... },
     *   "frequency": {
     *     "ala": 5,
     *     "kot": 3
     *   }
     * }
     * </pre>
     *
     * @param s    statystyki tekstu
     * @param freq mapa słowo → liczba wystąpień
     * @return raport JSON jako String
     */
    @Override
    public String full(TextStats s, Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"type\": \"full_stats\",\n");
        sb.append("  \"generatedAt\": \"").append(TimeUtil.now()).append("\",\n");
        sb.append("  \"stats\": {\n");
        sb.append("    \"words\": ").append(s.words()).append(",\n");
        sb.append("    \"charsWithSpaces\": ").append(s.charsWithSpaces()).append(",\n");
        sb.append("    \"charsWithoutSpaces\": ").append(s.charsWithoutSpaces()).append(",\n");
        sb.append("    \"sentences\": ").append(s.sentences()).append("\n");
        sb.append("  },\n");
        sb.append("  \"frequency\": {\n");

        var entries = SortUtil.sorted(freq);
        for (int i = 0; i < entries.size(); i++) {
            var e = entries.get(i);
            sb.append("    \"")
                    .append(Escape.json(e.getKey()))
                    .append("\": ")
                    .append(e.getValue());
            sb.append(i < entries.size() - 1 ? ",\n" : "\n");
        }

        sb.append("  }\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Generuje raport JSON zawierający wyłącznie częstotliwości słów.
     *
     * <p>Przykład:</p>
     * <pre>
     * {
     *   "type": "word_frequency",
     *   "generatedAt": "...",
     *   "frequency": {
     *     "ala": 5,
     *     "kot": 3
     *   }
     * }
     * </pre>
     *
     * @param freq mapa słowo → liczba wystąpień
     * @return raport JSON jako String
     */
    @Override
    public String frequency(Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"type\": \"word_frequency\",\n");
        sb.append("  \"generatedAt\": \"").append(TimeUtil.now()).append("\",\n");
        sb.append("  \"frequency\": {\n");

        var entries = SortUtil.sorted(freq);
        for (int i = 0; i < entries.size(); i++) {
            var e = entries.get(i);
            sb.append("    \"")
                    .append(Escape.json(e.getKey()))
                    .append("\": ")
                    .append(e.getValue());
            sb.append(i < entries.size() - 1 ? ",\n" : "\n");
        }

        sb.append("  }\n");
        sb.append("}\n");
        return sb.toString();
    }
}