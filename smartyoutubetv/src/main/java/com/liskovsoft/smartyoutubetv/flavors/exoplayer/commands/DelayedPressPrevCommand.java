package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class DelayedPressPrevCommand extends DelayedPressCommandBase {
    private final GenericCommand mCommand;

    public DelayedPressPrevCommand(GenericCommand command, int delayMillis) {
        super(delayMillis);
        mCommand = command;
    }

    @Override
    public boolean call() {
        pressButtonByClassAsync(GoogleConstants.BUTTON_PREV, mCommand);
        return true;
    }

}
