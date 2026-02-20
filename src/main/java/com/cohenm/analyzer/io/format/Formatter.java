package com.cohenm.analyzer.io.format;

import com.cohenm.analyzer.model.TextStats;

import java.util.Map;

public interface Formatter {


    String formatBasic(TextStats stats);
    String formatFull(TextStats stats, Map<String,Integer> freq);
    String formatFrequency(Map<String,Integer> freq);

}