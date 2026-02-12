package com.cohenm.analyzer.io;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Klasa pomocnicza odpowiedzialna za wczytywanie plików tekstowych
 * z katalogu zasobów (resources). Udostępnia statyczną metodę
 * umożliwiającą odczyt zawartości pliku jako ciągu znaków UTF‑8.
 *
 * <p>Metoda korzysta z ClassLoadera, dzięki czemu obsługuje pliki
 * umieszczone w katalogu <code>resources/</code> zarówno podczas
 * uruchamiania aplikacji z IDE, jak i po spakowaniu do JAR.</p>
 *
 * <p>W przypadku braku zasobu lub błędu odczytu rzucany jest
 * {@link RuntimeException} z opisem problemu.</p>
 *
 * <p>Klasa nie przechowuje stanu — wszystkie operacje są statyczne.</p>
 */
public class FileReader {

    /**
     * Odczytuje plik tekstowy z katalogu zasobów i zwraca jego zawartość
     * jako ciąg znaków w kodowaniu UTF‑8.
     *
     * <p>Przykład użycia:</p>
     * <pre>
     * String text = FileReader.readFile("example.txt");
     * </pre>
     *
     * @param path nazwa pliku w katalogu zasobów (np. "data.txt")
     * @return zawartość pliku jako String
     * @throws IOException jeśli wystąpi problem z odczytem pliku
     * @throws RuntimeException jeśli zasób nie istnieje lub nie można go załadować
     */
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