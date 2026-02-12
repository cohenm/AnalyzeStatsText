package com.cohenm.analyzer.io;

import com.cohenm.analyzer.io.builder.ReportBuilder;
import com.cohenm.analyzer.io.builder.ReportType;
import com.cohenm.analyzer.io.format.*;
import com.cohenm.analyzer.model.TextStats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ReportWriter {

    public enum Format {
        CSV, TXT, JSON, XML
    }

    public static void writeReport(Path outputPath,
                                   ReportType type,
                                   TextStats stats,
                                   Map<String, Integer> freq,
                                   Format format) {

        try {
            Formatter formatter = formatter(format);

            String content = ReportBuilder.build(
                    type,
                    stats,
                    freq,
                    formatter
            );

            write(outputPath, content);

        } catch (Exception e) {
            throw new RuntimeException("Nie udało się zapisać raportu: " + e.getMessage(), e);
        }
    }

    public static Formatter formatter(Format format) {
        return switch (format) {
            case CSV -> new CsvFormatter();
            case TXT -> new TxtFormatter();
            case JSON -> new JsonFormatter();
            case XML -> new XmlFormatter();
        };
    }

    public static void write(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, content);
    }
}
