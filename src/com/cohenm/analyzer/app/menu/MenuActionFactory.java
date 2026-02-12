package com.cohenm.analyzer.app.menu;

import com.cohenm.analyzer.app.menu.actions.*;
import com.cohenm.analyzer.core.TextAnalyzer;
import com.cohenm.analyzer.ui.ReportSaver;
import com.cohenm.analyzer.ui.StatsPrinter;
import com.cohenm.analyzer.ui.UserInput;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class MenuActionFactory {

    public static Map<MenuOption, MenuAction> create(
            TextAnalyzer analyzer,
            String path,
            UserInput input,
            StatsPrinter printer,
            ReportSaver saver,
            Set<String> stopWords,
            int[] minWordLengthRef
    ) {
        Map<MenuOption, MenuAction> map = new EnumMap<>(MenuOption.class);

        map.put(MenuOption.BASIC_STATS,
                new BasicStatsAction(analyzer, printer, path));

        map.put(MenuOption.TOP_WORDS,
                new TopWordsAction(analyzer, printer, input, path, stopWords, minWordLengthRef[0]));

        map.put(MenuOption.FREQUENCY_FRAGMENT,
                new FrequencyFragmentAction(analyzer, printer, path, stopWords, minWordLengthRef[0]));

        map.put(MenuOption.CHANGE_MIN_LENGTH,
                new ChangeMinWordLengthAction(input, minWordLengthRef));

        map.put(MenuOption.TOGGLE_STOPWORDS,
                new ToggleStopWordsAction(stopWords, Set.copyOf(stopWords)));

        map.put(MenuOption.SAVE_BASIC,
                new SaveBasicReportAction(saver, input, path, stopWords, minWordLengthRef));

        map.put(MenuOption.SAVE_FULL,
                new SaveFullReportAction(saver, input, path, stopWords, minWordLengthRef));

        map.put(MenuOption.SAVE_FREQUENCY,
                new SaveFrequencyReportAction(saver, input, path, stopWords, minWordLengthRef));

        map.put(MenuOption.EXIT,
                new ExitAction());

        return map;
    }
}