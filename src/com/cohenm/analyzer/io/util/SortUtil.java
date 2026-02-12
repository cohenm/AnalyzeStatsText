package com.cohenm.analyzer.io.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasa narzędziowa odpowiedzialna za sortowanie map częstotliwości słów.
 * Udostępnia metodę zwracającą posortowaną listę wpisów mapy
 * (słowo → liczba wystąpień), zgodnie z jednolitymi zasadami:
 *
 * <ul>
 *     <li>najpierw sortowanie malejąco po wartości (częstotliwości),</li>
 *     <li>w przypadku remisu — sortowanie alfabetyczne po kluczu.</li>
 * </ul>
 *
 * <p>Metoda jest wykorzystywana m.in. przez formattery raportów
 * (CSV, JSON, XML, TXT), aby zapewnić spójny porządek danych
 * niezależnie od formatu wyjściowego.</p>
 *
 * <p>Klasa jest bezstanowa — wszystkie metody są statyczne.</p>
 *
 * @see com.cohenm.analyzer.io.format.CsvFormatter
 * @see com.cohenm.analyzer.io.format.JsonFormatter
 * @see com.cohenm.analyzer.io.format.XmlFormatter
 * @see com.cohenm.analyzer.io.format.TxtFormatter
 */
public class SortUtil {

    /**
     * Zwraca listę wpisów mapy posortowaną według:
     * <ol>
     *     <li>malejącej liczby wystąpień,</li>
     *     <li>alfabetycznie po słowie (w przypadku remisu).</li>
     * </ol>
     *
     * <p>Jeśli mapa jest pusta lub null, zwracana jest pusta lista.</p>
     *
     * @param freq mapa słowo → liczba wystąpień
     * @return posortowana lista wpisów mapy
     */
    public static List<Map.Entry<String,Integer>> sorted(Map<String,Integer> freq) {
        if (freq == null || freq.isEmpty()) return List.of();
        return freq.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .collect(Collectors.toList());
    }
}
