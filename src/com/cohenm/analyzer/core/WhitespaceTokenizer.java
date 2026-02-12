package com.cohenm.analyzer.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementacja tokenizera dzielącego tekst na słowa na podstawie
 * białych znaków. Oczekuje, że tekst wejściowy został wcześniej
 * znormalizowany (np. poprzez usunięcie interpunkcji i zamianę na
 * małe litery), dzięki czemu tokenizacja może być wykonana w sposób
 * prosty i szybki.
 *
 * <p>Tokenizer wykorzystuje wyrażenie regularne {@code WHITESPACE_REGEX}
 * do rozdzielania słów na podstawie jednego lub wielu białych znaków.
 * Metoda jest odporna na wartości null oraz puste ciągi znaków -
 * w takich przypadkach zwracana jest pusta lista.</p>
 *
 * @see Tokenizer
 */

public class WhitespaceTokenizer implements Tokenizer {

    /**
     * Wyrażenie regularne dopasowujące jeden lub więcej białych znaków.
     */
    private static final String WHITESPACE_REGEX = "\\s+"; //regex dla białych znaków

    /**
     * Dzieli znormalizowany tekst na listę słów, wykorzystując białe znaki
     * jako separator. Każdy fragment jest zwracany w postaci tokenu.
     * @param normalizedText tekst po normalizacji, może być null
     * @return lista słów, nigdy null
     */
    @Override
    public List<String> words(String normalizedText) {
        if (normalizedText == null || normalizedText.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = normalizedText.trim().split(WHITESPACE_REGEX);
        return new ArrayList<>(Arrays.asList(parts));
    }
}
