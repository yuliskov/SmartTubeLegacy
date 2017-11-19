package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class DelayedPressBackCommand extends DelayedPressCommandBase {
    public DelayedPressBackCommand(int delayMillis) {
        super(delayMillis);
    }

    @Override
    public boolean call() {
        return pressButtonBySelector(GoogleConstants.BUTTON_BACK);
    }
}
