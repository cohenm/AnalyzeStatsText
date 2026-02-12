package com.cohenm.analyzer.core;

import com.cohenm.analyzer.io.FileReader;
import com.cohenm.analyzer.model.TextStats;
import com.cohenm.analyzer.model.WordCount;
import com.cohenm.analyzer.model.WordSort;

import java.io.IOException;
import java.util.*; // Map, Set, List, Comparator, etc.
import java.util.stream.Collectors;

/**
 * Główna klasa odpowiedzialna za analizę tekstu. Łączy działanie
 * normalizatora, tokenizera oraz dzielnika zdań, dostarczając
 * spójny zestaw metod do obliczania statystyk tekstu, częstotliwości
 * słów oraz generowania rankingów.
 *
 *
 * <p>TextAnalyzer pełni rolę centralnego komponentu logiki aplikacji.</p>
 * <p>Wykorzystuje:</p>
 * <ul>
 *     <li>{@link Normalizer} – przygotowanie tekstu (np. usunięcie interpunkcji, lowercase),</li>
 *     <li>{@link Tokenizer} – dzielenie tekstu na słowa,</li>
 *     <li>{@link SentenceTokenizer} – dzielenie tekstu na zdania.</li>
 * </ul>
 *
 * @see Normalizer
 * @see Tokenizer
 * @see SentenceTokenizer
 * @see FileReader
 * @see WordSort
 * @see WordCount
 * @see TextStats
 */

public class TextAnalyzer {
    private final Normalizer normalizer;
    private final Tokenizer tokenizer;
    private final SentenceTokenizer sentenceTokenizer;


    /**
     * Tworzy nowy analizator tekstu, wymagający trzech komponentów:
     * normalizatora, tokenizatora oraz dzielnika zdań.
     *
     * @param normalizer implementacja normalizacji tekstu
     * @param tokenizer implementacja tokenizacji tekstu
     * @param sentenceTokenizer implementacja dzielenia na zdania
     * @throws NullPointerException jeśli którykolwiek argument jest null
     */
    public TextAnalyzer(Normalizer normalizer,
                        Tokenizer tokenizer,
                        SentenceTokenizer sentenceTokenizer) {
        this.normalizer = Objects.requireNonNull(normalizer, "normalizer must not be null");
        this.tokenizer = Objects.requireNonNull(tokenizer, "tokenizer must not be null");
        this.sentenceTokenizer = Objects.requireNonNull(sentenceTokenizer, "sentenceTokenizer must not be null");
    }


    /**
     * Analizuje przekazany tekst, obliczając podstawowe statystyki.
     * <ul>
     *     <li>liczbę znaków ze spacjami,</li>
     *     <li>liczbę znaków bez spacji,</li>
     *     <li>liczbę słów,</li>
     *     <li>liczbę zdań.</li>
     * </ul>
     *
     * @param text tekst wejściowy, może być null
     * @return obiekt {@link TextStats} z wynikami analizy
     */
    public TextStats analyze(String text) {
        String original = Objects.requireNonNullElse(text, "");

        int charsWithSpaces = original.length();
        int charsWithoutSpaces = original.replaceAll("\\s+", "").length();

        // Normalizacja + tokenizacja
        String normalized = normalizer.normalize(original);
        List<String> words = tokenizer.words(normalized);
        List<String> sentences = sentenceTokenizer.sentences(original);

        return new TextStats(charsWithSpaces, charsWithoutSpaces, words.size(), sentences.size());
    }


    /**
     * Analizuje zawartość pliku wskazanego ścieżką. Wczytuje plik
     * za pomocą {@link FileReader#readFile(String)} i deleguje analizę
     * do metody {@link TextAnalyzer#analyze(String)}.
     *
     * @param path ścieżka do pliku tekstowego
     * @return statystyki tekstu
     * @throws IOException jeśli plik nie może zostać odczytany
     */
    public TextStats analyzeFile(String path) throws IOException {
        String content = FileReader.readFile(path); // wywołanie static funkcji czytania pliku
        return analyze(content);
    }


    /**
     * Tworzy mapę częstotliwości słów na podstawie tekstu.
     * Tekst jest normalizowany i tokenizowany, a następnie
     * filtrowany zgodnie z:
     *
     * <ul>
     *     <li>listę stop-words (opcjonalnie),</li>
     *     <li>minimalną długością słowa.</li>
     * </ul>
     *
     * @param text tekst wejściowy
     * @param stopWords zbiór słów do pominięcia (może być nulL)
     * @param minWordLength minimalna długość słowa (min. 1)
     * @return mapa słowo -> liczba wystąpień
     */
    public Map<String, Integer> wordFrequencyFromText(String text,
                                                      Set<String> stopWords,
                                                      int minWordLength) {
        return tokenizer.words(normalizer.normalize(Objects.requireNonNullElse(text, "")))
                .stream()
                .filter(w -> (stopWords == null || !stopWords.contains(w)))
                .filter(w -> w.length() >= Math.max(1, minWordLength))
                .collect(Collectors.toMap(
                        w -> w,
                        w -> 1,
                        Integer::sum
                ));

    }

    /**
     * Wersja plikowa metody {@link #wordFrequencyFromText(String, Set, int)}.
     *
     * @param path ścieżka do pliku
     * @param stopWords zbiór stop-words
     * @param minWordLength minimalna długość słowa
     * @return mapa częstotliwości słów
     * @throws IOException jeśli plik nie może zostać wczytany
     */
    public Map<String, Integer> wordFrequencyFromFile(String path,
                                                      Set<String> stopWords,
                                                      int minWordLength) throws IOException {
        return wordFrequencyFromText(FileReader.readFile(path), stopWords, minWordLength);
    }


    /**
     * Zwraca listę najczęściej występujących słów, posortowaną zgodnie
     * z trybem sortowania określonym przez {@link WordSort}. Wynik jest
     * ograniczony do topN elementów.
     *
     * @param text tekst wejściowy
     * @param topN liczba elementów do zwrócenia (min. 1)
     * @param stopWords zbiór stop-words
     * @param minWordLength minimalna długość słowa
     * @param sortMode tryb sortowania
     * @return lista obiektów {@link WordCount}
     */
    public List<WordCount> topWordsFromText(String text,
                                            int topN,
                                            Set<String> stopWords,
                                            int minWordLength,
                                            WordSort sortMode) {
        Map<String, Integer> freq = wordFrequencyFromText(text, stopWords, minWordLength);

        return freq.entrySet().stream()
                .map(e -> new WordCount(e.getKey(), e.getValue()))
                .sorted(sortMode.comparator())
                .limit(Math.max(1, topN))
                .collect(Collectors.toList());
    }

    /**
     * Wersja plikowa metody {@link #topWordsFromText(String, int, Set, int, WordSort)}.
     */
    public List<WordCount> topWordsFromFile(String path,
                                            int topN,
                                            Set<String> stopWords,
                                            int minWordLength,
                                            WordSort sortMode) throws IOException {
        String content = FileReader.readFile(path);
        return topWordsFromText(content, topN, stopWords, minWordLength, sortMode);
    }

    /**
     * Zwraca pełną listę słów wraz z ich częstotliwościami, posortowaną
     * zgodnie z trybem sortowania określonym przez {@link WordSort}
     *
     * @param text tekst wejściowy
     * @param stopWords zbiór stop-words
     * @param minWordLength minimalna długość słowa
     * @param sortMode tryb sortowania
     * @return lista obiektów {@link WordCount}
     */
    public List<WordCount> allWordsFromTextSorted(String text,
                                                  Set<String> stopWords,
                                                  int minWordLength,
                                                  WordSort sortMode) {
        Map<String, Integer> freq = wordFrequencyFromText(text, stopWords, minWordLength);

        return freq.entrySet().stream()
                .map(e -> new WordCount(e.getKey(), e.getValue()))
                .sorted(sortMode.comparator())
                .collect(Collectors.toList());
    }


    /**
     * @param text tekst wejściwowy
     * @param stopWords zbiór stop-words
     * @param minWordLength minimalna długość słowa
     * @param sortMode tryb sortowania
     * @return posortowana mapa słowo -> liczba wystąpień
     */
    public Map<String, Integer> wordFrequencySorted(String text,
                                                    Set<String> stopWords,
                                                    int minWordLength,
                                                    WordSort sortMode) {
        Map<String, Integer> freq = wordFrequencyFromText(text, stopWords, minWordLength);

        return freq.entrySet().stream()
                .map(e -> new WordCount(e.getKey(), e.getValue()))
                .sorted(sortMode.comparator())
                .collect(Collectors.toMap(
                        WordCount::word,
                        WordCount::count,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}