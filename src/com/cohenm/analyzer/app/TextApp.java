package com.cohenm.analyzer.app;

import com.cohenm.analyzer.io.FileReader;

import static com.cohenm.analyzer.app.TextMethod.*;

public class TextApp {

    public static void main(String[] args) {

        // wykorzystanie funkcji czytania z plików zasobów Resource
        String text = FileReader.readResource("file.txt");


        System.out.println("=== Zawartość pliku ===");

        System.out.println("Słowa: " + countWords(text));
        System.out.println("Znaki (ze spacjami): " + countCharsWithSpaces(text));
        System.out.println("Znaki (bez spacji): " + countCharsWithoutSpaces(text));

    }
}