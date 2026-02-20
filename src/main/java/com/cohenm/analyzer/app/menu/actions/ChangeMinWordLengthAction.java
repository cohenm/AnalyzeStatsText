package com.cohenm.analyzer.app.menu.actions;

import com.cohenm.analyzer.app.Settings;
import com.cohenm.analyzer.app.menu.MenuAction;
import com.cohenm.analyzer.ui.UserInput;

public class ChangeMinWordLengthAction implements MenuAction {

    private final UserInput input;
    private final Settings settings;

    public ChangeMinWordLengthAction(UserInput input, Settings settings) {
        this.input = input;
        this.settings = settings;
    }

    @Override
    public void execute() {
        int current = settings.getMinWordLength();
        int updated = input.askMinWordLength(current);
        settings.setMinWordLength(updated);
    }

    @Override
    public String label() {
        return "4) Zmień minWordLength";
    }
}
