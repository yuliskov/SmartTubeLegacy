package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class SyncStateCommand extends BooleanCommandBase {
    private final String mButtonSelector;
    private final boolean mIsChecked;

    public SyncStateCommand(String buttonSelector, boolean isChecked) {
        super();
        mButtonSelector = buttonSelector;
        mIsChecked = isChecked;
    }

    @Override
    protected String getActionFunction() {
        return String.format("YouButton.fromSelector('%s').setChecked(%s);", mButtonSelector, mIsChecked);
    }
}
