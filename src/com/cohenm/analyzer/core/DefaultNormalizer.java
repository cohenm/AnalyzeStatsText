package com.cohenm.analyzer.core;

import java.util.Objects;

/**
 * Implementacja domyślnego normalizatora tekstu.
 * Odpowiada za podstawowe czyszczenie tekstu przed dalszą analizą,
 * obejmujące:
 *
 * <ul>
 *     <li>usunięcie nadmiarowych białych znaków (trim),</li>
 *     <li>zamianę wszystkich liter na małe (lowercase),</li>
 *     <li>usunięcie znaków interpunkcyjnych oraz cudzysłowów,</li>
 *     <li>zastąpienie interpunkcji pojedynczą spacją.</li>
 * </ul>
 *
 * <p>Klasa wykorzysutje wyrażenie regularne {@code PUNCT_REGEX}
 * do identyfikacji znaków interpunkcyjnych zgodnych z {@code \p{Punct}}
 * oraz dodatkowych symboli typograficznych.</p>
 *
 * <p>Normalizacja jest odporna na wartości null - w takim przypadku traktuje wejście jako pusty ciąg znaków.</p>
 *
 * @see Normalizer
 *
 */

public class DefaultNormalizer implements Normalizer {

    /**
     * Wyrażenie regularne dopasowujące znaki interpunkcyjne oraz cudzysłowy.
     */
    private static final String PUNCT_REGEX = "[\\p{Punct}„”»«]";

    /**
     * Normalizuje tekst poprzez:
     * <ol>
     *     <li>zastąpienie wartości null pustym ciągiem,</li>
     *     <li>przycięcie białych znaków,</li>
     *     <li>zamianę na małe litery,</li>
     *     <li>usunięcie interpunkcji i zastąpenie jej spacją.</li>
     * </ol>
     *
     * @param text tekst wejściowy, może być null
     * @return znormalizowany tekst, nigdy null
     */
    @Override
    public String normalize(String text) {
        String t = Objects.requireNonNullElse(text, "").trim();
        if (t.isEmpty()) return "";
        return t.toLowerCase().replaceAll(PUNCT_REGEX, " ");
    }
}
