package com.cohenm.analyzer.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Domyślna implementacja dzielnika zdań.
 * Odpowiada za proste segmentowanie tekstu na zdania na podstawie
 * podstawowych znaków kończących wypowiedzenia: kropki, wykrzyknika
 * oraz znaku zapytania.
 *
 * <p>Tokenizer działa w sposób uproszczony - nie uwzględnia skrótów,
 * wielokrotnych kropek w skrótach (np. "itd."), ani bardziej złożonych
 * reguł językowych. Jego celem jest szybkie i lekkie dzielenie tekstu
 * na potrzeby dalszej analizy.</p>
 *
 * <p>Metoda jest odporna na wartości null oraz puste ciągi znaków -
 * w takich przypadkach zwracana jest pusta lista.</p>
 *
 * @see SentenceTokenizer
 */

public class DefaultSentenceTokenizer implements SentenceTokenizer {

    /**
     * Dzieli tekst na zdania, wykorzystując proste rozdzielanie
     * po znakach: kropka (.), wykrzytnik (!) oraz znak zapytania (?).
     * Każdy fragment jest dodatkowo przycinany z białych znaków.
     *
     * <p>Przykład działania:</p>
     * <pre>
     *     "Ala ma kota. Kot jest biały!"
     *      → ["Ala ma kota", "Kot jest biały"]
     * </pre>
     *
     * @param text tekst wejściowy, może być null
     * @return lista zdań bez pustych elementów
     */
    @Override
    public List<String> sentences (String text) {
        List<String> result = new ArrayList<>();
        if (text == null) return result;
        String trimmed = text.trim();
        if (trimmed.isEmpty()) return result;

        // Proste dzielenie po . ! ?
        String[] parts = trimmed.split("[.!?]+");
        for (String p : parts) {
            String s = p.trim();
            if (!s.isEmpty()) {
                result.add(s);
            }
        }
        return result;
    }
}