package com.cohenm.analyzer.core;

import com.cohenm.analyzer.io.FileReader;
import com.cohenm.analyzer.model.TextStats;
import com.cohenm.analyzer.model.WordCount;
import com.cohenm.analyzer.model.WordSort;

import java.io.IOException;
import java.util.*; // Map, Set, List, Comparator, etc.
import java.util.stream.Collectors;

/**
 * TextAnalyzer -> serce core/ scala wykorzystanie interfejsów i ich implementacji
 */

public class TextAnalyzer {
    private final Normalizer normalizer;
    private final Tokenizer tokenizer;
    private final SentenceTokenizer sentenceTokenizer;

    /**
     * Konstruktor - wywołujący normalizera i tokenizery
     */
    public TextAnalyzer(Normalizer normalizer,
                        Tokenizer tokenizer,
                        SentenceTokenizer sentenceTokenizer) {
        this.normalizer = Objects.requireNonNull(normalizer, "normalizer must not be null");
        this.tokenizer = Objects.requireNonNull(tokenizer, "tokenizer must not be null");
        this.sentenceTokenizer = Objects.requireNonNull(sentenceTokenizer, "sentenceTokenizer must not be null");
    }

    /**
     * analiza tekstu
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
     * Analiza pliku (delegacja do FileReader)
     */
    public TextStats analyzeFile(String path) throws IOException {
        String content = FileReader.readFile(path); // wywołanie static funkcji czytania pliku
        return analyze(content);
    }

    // częstotliwości słów

    /**
     * Pełna mapa częstotliwości (po normalizacji), z opcjonalnymi stop‑words i minimalną długością słowa.
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
     * Wersje plikowe (delegują do readFileToString).
     */
    public Map<String, Integer> wordFrequencyFromFile(String path,
                                                      Set<String> stopWords,
                                                      int minWordLength) throws IOException {
        return wordFrequencyFromText(FileReader.readFile(path), stopWords, minWordLength);
    }

    /**
     * Zwraca listę słów posortowaną zgodnie z WordSort; limit topN (min. 1).
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

    public List<WordCount> topWordsFromFile(String path,
                                            int topN,
                                            Set<String> stopWords,
                                            int minWordLength,
                                            WordSort sortMode) throws IOException {
        String content = FileReader.readFile(path);
        return topWordsFromText(content, topN, stopWords, minWordLength, sortMode);
    }


    /**
     * Pełna lista posortowana wg WordSort (bez limitu).
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
     * (Opcjonalnie) Zwraca posortowaną mapę częstotliwości jako LinkedHashMap (kolejność wg sortMode).
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