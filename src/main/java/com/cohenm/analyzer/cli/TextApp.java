package com.cohenm.analyzer.cli;

import com.cohenm.analyzer.app.Settings;
import com.cohenm.analyzer.app.menu.MenuAction;
import com.cohenm.analyzer.app.menu.MenuActionFactory;
import com.cohenm.analyzer.app.menu.MenuOption;
import com.cohenm.analyzer.core.DefaultNormalizer;
import com.cohenm.analyzer.core.TextAnalyzer;
import com.cohenm.analyzer.core.WhitespaceTokenizer;
import com.cohenm.analyzer.core.DefaultSentenceTokenizer;
import com.cohenm.analyzer.ui.ReportSaver;
import com.cohenm.analyzer.ui.StatsPrinter;
import com.cohenm.analyzer.ui.UserInput;

import java.util.*;

public class TextApp {

    public static void main(String[] args) {

        TextAnalyzer analyzer = new TextAnalyzer(
                new DefaultNormalizer(),
                new WhitespaceTokenizer(),
                new DefaultSentenceTokenizer()
        );

        Scanner sc = new Scanner(System.in);
        UserInput input = new UserInput(sc);

        System.out.print("Podaj bazową nazwę pliku (bez .txt): ");
        String baseName = input.readLine().trim();
        String path = baseName + ".txt";

        StatsPrinter printer = new StatsPrinter();
        ReportSaver saver = new ReportSaver(analyzer);

        Settings settings = new Settings();

        Map<MenuOption, MenuAction> actions = MenuActionFactory.create(
                analyzer,
                path,
                input,
                printer,
                saver,
                settings
        );



        TextMenu menu = new TextMenu(input, actions);
        menu.run();
    }
}
