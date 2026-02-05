package com.cohenm.analyzer.core;

import java.util.List;

/**
 * Tokenizer -> odpowiedzialny za rozbicie na słowa
 */

public interface Tokenizer {
    List<String> words (String normalizedText);
}
