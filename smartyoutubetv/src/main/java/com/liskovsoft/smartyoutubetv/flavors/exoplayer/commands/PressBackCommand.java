package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class PressBackCommand extends PressCommandBase {
    @Override
    public boolean call() {
        return pressButtonBySelector(GoogleConstants.BUTTON_BACK);
    }
}
