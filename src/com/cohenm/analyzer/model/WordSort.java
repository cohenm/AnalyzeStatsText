package com.cohenm.analyzer.model;


// plik: model/WordSort.java

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Enum definiujący dostępne tryby sortowania listy {@link WordCount}.
 * Każda wartość udostępnia własny komparator, który określa sposób
 * porządkowania słów w raportach oraz wynikach analizy.
 *
 * <p>Sortowanie odbywa się z użyciem {@link Collator} skonfigurowanego
 * dla języka polskiego, dzięki czemu poprawnie obsługiwane są znaki
 * diakrytyczne (ą, ć, ę, ł, ń, ó, ś, ź, ż).</p>
 *
 * <p>Dostępne tryby:</p>
 * <ul>
 *     <li><b>ALPHABETIC</b> – sortowanie alfabetyczne rosnąco,</li>
 *     <li><b>FREQUENCY_DESC</b> – sortowanie malejąco po liczbie wystąpień,
 *         a następnie alfabetycznie,</li>
 *     <li><b>FREQUENCY_ASC</b> – sortowanie rosnąco po liczbie wystąpień,
 *         a następnie alfabetycznie.</li>
 * </ul>
 *
 * <p>Enum jest wykorzystywany m.in. przez {@link com.cohenm.analyzer.core.TextAnalyzer}
 * oraz formattery raportów.</p>
 *
 * @see WordCount
 * @see com.cohenm.analyzer.core.TextAnalyzer
 */
public enum WordSort {

    /**
     * Sortowanie alfabetyczne rosnące według zasad języka polskiego.
     */
    ALPHABETIC {
        @Override
        public Comparator<WordCount> comparator() {
            return (a, b) -> localeStringComparator(Collator.PRIMARY)
                    .compare(a.word(), b.word());
        }
    },

    /**
     * Sortowanie malejące po liczbie wystąpień.
     * Przy remisie – sortowanie alfabetyczne.
     */
    FREQUENCY_DESC {
        @Override
        public Comparator<WordCount> comparator() {
            Comparator<String> strCmp = localeStringComparator(Collator.PRIMARY);
            return (a, b) -> {
                int c = Integer.compare(b.count(), a.count());
                return (c != 0) ? c : strCmp.compare(a.word(), b.word());
            };
        }
    },

    /**
     * Sortowanie rosnące po liczbie wystąpień.
     * Przy remisie – sortowanie alfabetyczne.
     */
    FREQUENCY_ASC {
        @Override
        public Comparator<WordCount> comparator() {
            Comparator<String> strCmp = localeStringComparator(Collator.PRIMARY);
            return (a, b) -> {
                int c = Integer.compare(a.count(), b.count());
                return (c != 0) ? c : strCmp.compare(a.word(), b.word());
            };
        }
    };

    /**
     * Zwraca komparator odpowiedzialny za sortowanie zgodnie z trybem.
     *
     * @return komparator dla {@link WordCount}
     */
    public abstract Comparator<WordCount> comparator();

    /**
     * Tworzy komparator napisów oparty o {@link Collator} dla języka polskiego.
     *
     * @param strength poziom czułości porównania (np. {@link Collator#PRIMARY})
     * @return komparator napisów zgodny z zasadami języka polskiego
     */
    private static Comparator<String> localeStringComparator(int strength) {
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(strength); // PRIMARY: ignoruje case/akcenty; TERTIARY: pełne rozróżnienie
        return collator::compare;
    }
}