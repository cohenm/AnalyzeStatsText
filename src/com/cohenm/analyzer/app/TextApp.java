package com.cohenm.analyzer.app;

import com.cohenm.analyzer.core.*;
import com.cohenm.analyzer.model.*;
import java.util.*;

/**
 * TextApp -> główna klasa aplikacji odpowiedzialna za uruchomienie programu
 * analizującego tekst. Inicjalizuje komponenty przetwarzania tekstu,
 * pobiera od użytkownika nazwę pliku wejściowego oraz uruchamia
 * interaktywne menu operacji na tekście.
 *
 * <p>Aplikacja wykorzystuje {@link TextAnalyzer}, który składa się z:</p>
 *    <ul>
 *        <li>{@link DefaultNormalizer} - normalizuje tekst wejściowy,</li>
 *        <li>{@link WhitespaceTokenizer} - dzieli tekst na tokeny na podstawie białych znaków</li>
 *        <li>{@link DefaultNormalizer} - dzieli tekst na zdania</li>
 *    </ul>
 *
 *    <p>Po uruchomieniu program prosi użytkownika o podanie bazowej nazwy pliku
 *    (bez rozszerzenia), a następnie tworzy ścieżkę do p;iku tekstowego.
 *    Kolejno inicjalizuje menu {@link TextMenu}, które umożliwia wykonanie
 *    operacji na wskazanym pliku.</p>
 *
 *    <p>Przykład użycia:</p>
 *    <pre>
 *        java TextApp
 *    </pre>
 */
public class TextApp {
    public static void main(String[] args) {

        TextAnalyzer analyzer = new TextAnalyzer(
                new DefaultNormalizer(),
                new WhitespaceTokenizer(),
                new DefaultSentenceTokenizer());

        Scanner sc = new Scanner(System.in);

        System.out.print("Podaj bazową nazwę pliku (bez .txt): ");
        String baseName = sc.nextLine().trim();
        String path = baseName + ".txt";

        new TextMenu (analyzer, path, sc).run();
    }
}