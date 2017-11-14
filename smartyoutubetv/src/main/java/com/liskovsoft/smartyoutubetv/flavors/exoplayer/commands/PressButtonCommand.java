package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class PressButtonCommand extends PressCommandBase {
    private final String mSelector;

    public PressButtonCommand(String selector) {
        mSelector = selector;
    }

    @Override
    public boolean call() {
        return pressButtonByClass(mSelector);
    }
}
