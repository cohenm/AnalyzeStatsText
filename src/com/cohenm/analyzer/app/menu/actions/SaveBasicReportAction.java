package com.cohenm.analyzer.app.menu.actions;

import com.cohenm.analyzer.app.Settings;
import com.cohenm.analyzer.app.menu.MenuAction;
import com.cohenm.analyzer.io.ReportWriter;
import com.cohenm.analyzer.io.builder.ReportType;
import com.cohenm.analyzer.ui.ReportSaver;
import com.cohenm.analyzer.ui.UserInput;

import java.nio.file.Path;

public class SaveBasicReportAction implements MenuAction {

    private final ReportSaver saver;
    private final UserInput input;
    private final String path;
    private final Settings settings;

    public SaveBasicReportAction(ReportSaver saver, UserInput input, String path, Settings settings) {
        this.saver = saver;
        this.input = input;
        this.path = path;
        this.settings = settings;
    }

    @Override
    public void execute() {
        Path output = input.askOutputPath("basic_report.txt");
        ReportWriter.Format format = input.askReportFormat();
        saver.saveReport(output, path, ReportType.BASIC, settings.getStopWords(),
                settings.getMinWordLength(), null, 0, format);
    }

    @Override
    public String label() {
        return "6) Zapisz podstawowe statystyki";
    }
}
