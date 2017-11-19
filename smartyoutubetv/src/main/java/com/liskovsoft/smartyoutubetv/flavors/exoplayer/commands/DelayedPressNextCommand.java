package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class DelayedPressNextCommand extends DelayedPressCommandBase {
    private final GenericCommand mCommand;

    public DelayedPressNextCommand(GenericCommand command, int delayMillis) {
        super(delayMillis);
        mCommand = command;
    }

    @Override
    public boolean call() {
        pressButtonByClassAsync(GoogleConstants.BUTTON_NEXT, mCommand);
        return true;
    }
}
