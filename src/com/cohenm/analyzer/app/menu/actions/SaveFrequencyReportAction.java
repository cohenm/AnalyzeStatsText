package com.cohenm.analyzer.app.menu.actions;

import com.cohenm.analyzer.app.menu.MenuAction;
import com.cohenm.analyzer.io.ReportWriter;
import com.cohenm.analyzer.io.builder.ReportType;
import com.cohenm.analyzer.model.WordSort;
import com.cohenm.analyzer.ui.ReportSaver;
import com.cohenm.analyzer.ui.UserInput;

import java.nio.file.Path;
import java.util.Set;

public class SaveFrequencyReportAction implements MenuAction {

    private final ReportSaver saver;
    private final UserInput input;
    private final String path;
    private final Set<String> stopWords;
    private final int[] minWordLengthRef;

    public SaveFrequencyReportAction(ReportSaver saver, UserInput input, String path,
                                     Set<String> stopWords, int[] minWordLengthRef) {
        this.saver = saver;
        this.input = input;
        this.path = path;
        this.stopWords = stopWords;
        this.minWordLengthRef = minWordLengthRef;
    }

    @Override
    public void execute() {
        Path output = input.askOutputPath("frequency_report.txt");
        ReportWriter.Format format = input.askReportFormat();
        WordSort sort = input.askSortMode();
        saver.saveReport(output, path, ReportType.FREQUENCY, stopWords, minWordLengthRef[0],
                sort, 0, format);
    }

    @Override
    public String label() {
        return "8) Zapisz częstotliwości słów";
    }
}
