package com.cohenm.analyzer.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SortUtil -> narzędzie sortujące
 */

public class SortUtil {

    public static List<Map.Entry<String,Integer>> sorted(Map<String,Integer> freq) {
        if (freq == null || freq.isEmpty()) return List.of();
        return freq.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .collect(Collectors.toList());
    }
}
