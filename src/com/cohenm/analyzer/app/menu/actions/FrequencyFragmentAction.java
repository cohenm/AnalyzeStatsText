package com.cohenm.analyzer.app.menu.actions;

import com.cohenm.analyzer.app.Settings;
import com.cohenm.analyzer.app.menu.MenuAction;
import com.cohenm.analyzer.core.TextAnalyzer;
import com.cohenm.analyzer.model.WordSort;
import com.cohenm.analyzer.ui.StatsPrinter;

public class FrequencyFragmentAction implements MenuAction {

    private final TextAnalyzer analyzer;
    private final StatsPrinter printer;
    private final String path;
    private final Settings settings;

    public FrequencyFragmentAction(TextAnalyzer analyzer, StatsPrinter printer, String path, Settings settings) {
        this.analyzer = analyzer;
        this.printer = printer;
        this.path = path;
        this.settings = settings;
    }

    @Override
    public void execute() {
        printer.printFrequencyPreview(
                analyzer, path, settings.getStopWords(), settings.getMinWordLength(), WordSort.FREQUENCY_DESC
        );
    }

    @Override
    public String label() {
        return "3) Fragment częstotliwości";
    }
}