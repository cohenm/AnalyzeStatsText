package com.cohenm.analyzer.io.util;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klasa narzędziowa odpowiedzialna za dostarczanie aktualnego czasu
 * w jednolitym formacie ISO-8601 z informacją o strefie czasowej.
 *
 * <p>Metoda {@link #now()} jest wykorzystywana m.in. przez formattery
 * raportów (JSON, XML, TXT), aby oznaczać moment wygenerowania raportu
 * w sposób spójny i zgodny ze standardami.</p>
 *
 * <p>Format zwracanej daty to:
 * <pre>
 * yyyy-MM-dd'T'HH:mm:ssXXX
 * </pre>
 * Przykład:
 * <pre>
 * 2025-01-01T12:34:56+01:00
 * </pre>
 * </p>
 *
 * <p>Klasa jest bezstanowa — wszystkie metody są statyczne.</p>
 *
 * @see java.time.OffsetDateTime
 * @see java.time.format.DateTimeFormatter#ISO_OFFSET_DATE_TIME
 */
public class TimeUtil {

    /**
     * Zwraca aktualny czas w formacie ISO-8601 z przesunięciem strefowym.
     *
     * @return aktualny czas jako String, np. "2025-01-01T12:34:56+01:00"
     */
    public static String now() {
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}