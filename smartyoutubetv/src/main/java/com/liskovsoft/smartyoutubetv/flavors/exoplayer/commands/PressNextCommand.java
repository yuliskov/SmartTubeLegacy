package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class PressNextCommand extends PressCommandBase {
    private final GenericCommand mCommand;

    public PressNextCommand(GenericCommand command) {
        mCommand = command;
    }

    @Override
    public boolean call() {
        pressButtonByClassAsync(GoogleConstants.BUTTON_NEXT, mCommand);

        return true;
    }
}
