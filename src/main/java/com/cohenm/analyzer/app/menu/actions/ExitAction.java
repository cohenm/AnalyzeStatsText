package com.cohenm.analyzer.app.menu.actions;

import com.cohenm.analyzer.app.menu.MenuAction;

public class ExitAction implements MenuAction {

    @Override
    public void execute() {
        System.out.println("Koniec. Do zobaczenia!");
        System.exit(0);
    }

    @Override
    public String label() {
        return "0) Wyjście";
    }
}
