package com.cohenm.analyzer.io.builder;

import com.cohenm.analyzer.io.format.Formatter;
import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

public class ReportBuilder {

    public static String build(ReportType type,
                               TextStats stats,
                               Map<String, Integer> freq,
                               Formatter f) {

        return switch (type) {
            case BASIC -> f.formatBasic(stats);
            case FULL -> f.formatFull(stats, freq);
            case FREQUENCY -> f.formatFrequency(freq);
        };
    }
}