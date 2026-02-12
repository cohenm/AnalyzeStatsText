package com.cohenm.analyzer.core;

import java.util.List;

/**
 * Interfejs definiujący mechanizm dzielenia tekstu na słowa.
 *
 * <p>Tokenizer przyjmuje tekst już znormalizowany (np. po usunięciu
 * interpunkcji i zamianie na małe litery) i rozbija go na listę tokenów,
 * zwykle oddzielonych białymi znakami. Implementacje mogą stosować
 * różne strategie, w zależności od potrzeb analizy.</p>
 *
 * <p>Metoda powinna być odporna na wartości null i zwracać pustą listę
 * w przypadku braku danych wejściowych.</p>
 *
 * @see Normalizer
 * @see SentenceTokenizer
 */
public interface Tokenizer {

    /**
     * Dzieli znormalizowany tekst na listę słów.
     *
     * @param normalizedText tekst po normalizacji, może być null
     * @return lista słów, nigdy null
     */
    List<String> words(String normalizedText);
}
