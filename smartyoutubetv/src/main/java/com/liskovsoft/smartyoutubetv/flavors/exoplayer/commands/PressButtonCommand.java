package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class PressButtonCommand extends PressCommandBase {
    private final String mSelector;
    private final String mJSCode;

    public PressButtonCommand(String selector) {
        this(selector, null);
    }

    public PressButtonCommand(String selector, String additionalJSCode) {
        mSelector = selector;
        mJSCode = additionalJSCode;
    }

    @Override
    public boolean call() {
        setAdditionalJSCode(mJSCode);
        return pressButtonByClass(mSelector);
    }

}
