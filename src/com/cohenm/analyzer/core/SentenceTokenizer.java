package com.cohenm.analyzer.core;

import java.util.List;

/**
 * Interfejs definiujący mechanizm dzielenia tekstu na zdania.
 *
 * <p>Implementacje mogą wykorzystywać różne strategie segmentacji,
 * od prostych reguł opartych na znakach interpunkcyjnych,
 * po bardziej zaawansowane algorytmy uwzględniające kontekst językowy.</p>
 *
 * <p>Metoda powinna być odporna na wartości null i zwracać
 * pustą listę w przypadku braku danych wejściowych.</p>
 *
 * @see Tokenizer
 * @see Normalizer
 *
 */
public interface SentenceTokenizer {

    /**
     * Dzieli tekst na listę zdań zgodnie z logiką implementacji.
     *
     * @param text tekst wejściowy, może być null
     * @return lista zdań, nigdy null
     */
    List<String> sentences(String text);
}
