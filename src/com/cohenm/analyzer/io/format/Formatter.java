package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

public interface Formatter {
    String basic(TextStats textStats);
    String full(TextStats textStats, Map<String, Integer> freq);
    String frequency(Map<String, Integer> freq);
}
