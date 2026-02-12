package com.cohenm.analyzer.app;

import com.cohenm.analyzer.core.*;
import com.cohenm.analyzer.model.*;
import java.util.*;

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