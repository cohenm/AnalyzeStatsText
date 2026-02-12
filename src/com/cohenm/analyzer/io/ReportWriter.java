package com.cohenm.analyzer.io;

import com.cohenm.analyzer.io.builder.ReportBuilder;
import com.cohenm.analyzer.io.format.*;
import com.cohenm.analyzer.io.format.Formatter;
import com.cohenm.analyzer.model.TextStats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*; // ArrayList, Collections, List, Map
import java.util.stream.Collectors;

/**
 * Klasa odpowiedzialna za zapisywanie raportów do plików w różnych formatach.
 * Udostępnia statyczne metody umożliwiające zapis:
 * <ul>
 *     <li>podstawowych statystyk,</li>
 *     <li>pełnych statystyk (statystyki + częstotliwości słów),</li>
 *     <li>samej częstotliwości słów.</li>
 * </ul>
 *
 * <p>Format wyjściowy jest określany przez enum {@link Format}, a właściwa
 * implementacja formattera wybierana jest przez metodę {@link #formatter(Format)}.
 * Generowanie treści raportu delegowane jest do {@link ReportBuilder}.</p>
 *
 * <p>Klasa jest narzędziowa — posiada prywatny konstruktor i wyłącznie metody statyczne.</p>
 *
 * @see ReportBuilder
 * @see Formatter
 * @see CsvFormatter
 * @see TxtFormatter
 * @see JsonFormatter
 * @see XmlFormatter
 */
public class ReportWriter {

    /**
     * Dostępne formaty raportów obsługiwane przez {@link ReportWriter}.
     */
    public enum Format {CSV, TXT, JSON, XML}

    /**
     * Prywatny konstruktor uniemożliwiający tworzenie instancji klasy narzędziowej.
     */
    private ReportWriter() {
    }

    // ======= API publiczne =======

    /**
     * Zapisuje podstawowe statystyki tekstu do pliku w wybranym formacie.
     *
     * @param stats  obiekt statystyk tekstu
     * @param out    ścieżka docelowa pliku
     * @param format format raportu
     * @throws IOException jeśli zapis pliku się nie powiedzie
     */
    public static void writeBasicStats(TextStats stats, Path out, Format format) throws IOException {
        write(out, ReportBuilder.basic(stats, formatter(format)));
    }

    /**
     * Zapisuje pełne statystyki tekstu (statystyki + częstotliwości słów)
     * do pliku w wybranym formacie.
     *
     * @param stats  statystyki tekstu
     * @param freq   mapa słowo → liczba wystąpień
     * @param out    ścieżka docelowa pliku
     * @param format format raportu
     * @throws IOException jeśli zapis pliku się nie powiedzie
     */
    public static void writeFullStats(TextStats stats, java.util.Map<String, Integer> freq, Path out, Format format) throws IOException {
        write(out, ReportBuilder.full(stats, freq, formatter(format)));
    }

    /**
     * Zapisuje raport zawierający wyłącznie częstotliwości słów.
     *
     * @param freq   mapa słowo → liczba wystąpień
     * @param out    ścieżka docelowa pliku
     * @param format format raportu
     * @throws IOException jeśli zapis pliku się nie powiedzie
     */
    public static void writeWordFrequency(java.util.Map<String, Integer> freq, Path out, Format format) throws IOException {
        write(out, ReportBuilder.frequency(freq, formatter(format)));
    }

    /**
     * Zapisuje treść raportu do pliku, tworząc katalogi pośrednie jeśli to konieczne.
     *
     * @param out     ścieżka docelowa
     * @param content treść raportu
     * @throws IOException jeśli zapis pliku się nie powiedzie
     */
    private static void write(Path out, String content) throws IOException {
        Path parent = out.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(out, content, StandardCharsets.UTF_8);
    }

    /**
     * Zwraca odpowiednią implementację {@link Formatter} na podstawie wybranego formatu.
     *
     * @param f format raportu
     * @return instancja formattera
     */
    private static Formatter formatter(Format f) {
        return switch (f) {
            case CSV -> new CsvFormatter();
            case TXT -> new TxtFormatter();
            case JSON -> new JsonFormatter();
            case XML -> new XmlFormatter();
        };
    }
}