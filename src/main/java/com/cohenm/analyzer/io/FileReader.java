package com.cohenm.analyzer.io;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileReader {

    public static String readFile(String path) throws IOException {

        InputStream in = FileReader.class
                .getClassLoader()
                .getResourceAsStream(path);

        if (in == null) {
            throw new RuntimeException("Zasób został nieznaleziony: " + path);
        }
        try (InputStream input = in) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Błąd ładowania zasobów: " + path, e);
        }
    }
}