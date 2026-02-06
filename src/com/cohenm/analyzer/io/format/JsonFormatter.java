package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.io.util.Escape;
import com.cohenm.analyzer.io.util.SortUtil;
import com.cohenm.analyzer.io.util.TimeUtil;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

public class JsonFormatter implements Formatter {

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