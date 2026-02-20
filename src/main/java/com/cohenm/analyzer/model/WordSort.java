package com.cohenm.analyzer.model;


// plik: model/WordSort.java

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public enum WordSort {

    ALPHABETIC {
        @Override
        public Comparator<WordCount> comparator() {
            return (a, b) -> localeStringComparator(Collator.PRIMARY)
                    .compare(a.word(), b.word());
        }
    },

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

    public abstract Comparator<WordCount> comparator();

    private static Comparator<String> localeStringComparator(int strength) {
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(strength); // PRIMARY: ignoruje case/akcenty; TERTIARY: pełne rozróżnienie
        return collator::compare;
    }
}