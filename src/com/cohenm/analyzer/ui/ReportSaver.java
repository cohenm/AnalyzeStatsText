package com.cohenm.analyzer.ui;

import com.cohenm.analyzer.core.TextAnalyzer;
import com.cohenm.analyzer.core.WordAnalysisMode;
import com.cohenm.analyzer.io.ReportWriter;
import com.cohenm.analyzer.io.builder.ReportBuilder;
import com.cohenm.analyzer.io.builder.ReportType;
import com.cohenm.analyzer.model.TextStats;
import com.cohenm.analyzer.model.WordSort;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class ReportSaver {

    private final TextAnalyzer analyzer;

    public ReportSaver(TextAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void saveReport(Path outputPath,
                           String inputPath,
                           ReportType type,
                           Set<String> stopWords,
                           int minWordLength,
                           WordSort sortMode,
                           int topN,
                           ReportWriter.Format format) {

        try {
            // 1. Statystyki
            TextStats stats = analyzer.analyzeFile(inputPath);

            // 2. Częstotliwości słów (zawsze potrzebne)
            @SuppressWarnings("unchecked")
            Map<String, Integer> freq = (Map<String, Integer>) analyzer.analyzeWordsFromFile(
                    inputPath,
                    stopWords,
                    minWordLength,
                    sortMode,
                    topN,
                    WordAnalysisMode.FREQUENCY_MAP
            );

            // 3. Budowanie treści raportu
            String content = ReportBuilder.build(type, stats, freq, ReportWriter.formatter(format));

            // 4. Zapis do pliku
            ReportWriter.write(outputPath, content);

            System.out.println("✔ Raport zapisany: " + outputPath);

        } catch (Exception e) {
            System.err.println("❌ Błąd zapisu raportu: " + e.getMessage());
        }
    }
}