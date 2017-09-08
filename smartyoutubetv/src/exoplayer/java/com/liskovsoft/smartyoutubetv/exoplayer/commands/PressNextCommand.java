package com.liskovsoft.smartyoutubetv.exoplayer.commands;

public class PressNextCommand extends PressCommandBase {
    private final GenericCommand mCommand;

    public PressNextCommand(GenericCommand command) {
        mCommand = command;
    }

    @Override
    public boolean call() {
        pressButtonByClassAsync("icon-player-next", mCommand);

        return true;
    }
}
