package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class PressAnyButtonCommand extends PressCommandBase {
    private final String mSelector;
    private final String mJSCode;

    public PressAnyButtonCommand(String selector) {
        this(selector, null);
    }

    public PressAnyButtonCommand(String selector, String additionalJSCode) {
        mSelector = selector;
        mJSCode = additionalJSCode;
    }

    @Override
    public boolean call() {
        setAdditionalJSCode(mJSCode);
        return pressButtonBySelector(mSelector);
    }

}
