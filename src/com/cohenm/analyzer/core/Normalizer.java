package com.cohenm.analyzer.core;

/**
 * Interfejs definiujący kontrakt dla klas odpowiedzialnych
 * za normalizację tekstu przed dalszą analizą.
 *
 * <p>Normalizer może wykonywać różne operacje przygotowawcze,
 * takie jak:
 * <ul>
 *     <li>usuwanie interpunkcji,</li>
 *     <li>przycinanie białych znaków,</li>
 *     <li>zamiana na małe litery,</li>
 *     <li>inne transformacje zależne od implementacji.</li>
 * </ul>
 *
 * <p>Implementacje powinny być odporne na wartości null
 * i zwracać wynik w postaci nie-nullowego ciągu znaków.</p>
 *
 * @see com.cohenm.analyzer.core.Tokenizer
 * @see com.cohenm.analyzer.core.SentenceTokenizer
 *
 */
public interface Normalizer {

    /**
     * Normalizuje przekazany tekst zgodnie z logiką implementacji.
     *
     * @param text tekst wejściowy, może być null
     * @return znormalizowany tekst, nigdy null
     */
    String normalize(String text);
}
