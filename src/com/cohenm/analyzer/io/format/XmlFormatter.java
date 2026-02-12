package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.io.util.Escape;
import com.cohenm.analyzer.io.util.TimeUtil;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

public class XmlFormatter implements Formatter {

    @Override
    public String formatBasic(TextStats stats) {
        return """
                <report type="basic_stats" generatedAt="%s">
                  <stats>
                    <words>%d</words>
                    <charsWithSpaces>%d</charsWithSpaces>
                    <charsWithoutSpaces>%d</charsWithoutSpaces>
                    <sentences>%d</sentences>
                  </stats>
                </report>
                """.formatted(
                TimeUtil.now(),
                stats.words(),
                stats.charsWithSpaces(),
                stats.charsWithoutSpaces(),
                stats.sentences()
        );
    }

    @Override
    public String formatFull(TextStats stats, Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder("""
                <report type="full_stats" generatedAt="%s">
                  <stats>
                    <words>%d</words>
                    <charsWithSpaces>%d</charsWithSpaces>
                    <charsWithoutSpaces>%d</charsWithoutSpaces>
                    <sentences>%d</sentences>
                  </stats>
                  <frequency>
                """.formatted(
                TimeUtil.now(),
                stats.words(),
                stats.charsWithSpaces(),
                stats.charsWithoutSpaces(),
                stats.sentences()
        ));

        freq.forEach((w, c) ->
                sb.append("    <word text=\"%s\" count=\"%d\" />\n"
                        .formatted(Escape.xml(w), c))
        );

        sb.append("  </frequency>\n</report>");
        return sb.toString();
    }

    @Override
    public String formatFrequency(Map<String, Integer> freq) {
        StringBuilder sb = new StringBuilder("""
                <report type="frequency" generatedAt="%s">
                  <frequency>
                """.formatted(TimeUtil.now()));

        freq.forEach((w, c) ->
                sb.append("    <word text=\"%s\" count=\"%d\" />\n"
                        .formatted(Escape.xml(w), c))
        );

        sb.append("  </frequency>\n</report>");
        return sb.toString();
    }
}
