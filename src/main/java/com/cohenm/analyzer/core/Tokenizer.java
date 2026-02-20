package com.cohenm.analyzer.core;

import java.util.List;

public interface Tokenizer {

    List<String> words(String normalizedText);
}
