package com.cohenm.analyzer.core;

import java.util.List;

/**
 * SentenceTokenizer -> odpowiedzialny za rozbicie na zdania
 */

public interface SentenceTokenizer {

    List<String> sentences(String text);
}
