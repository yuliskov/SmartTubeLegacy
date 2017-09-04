package com.liskovsoft.smartyoutubetv.exoplayer.commands;

import java.util.concurrent.Callable;

public class PressNextCommand extends PressCommandBase {
    private final GenericCommand mCommand;

    public PressNextCommand(GenericCommand command) {
        mCommand = command;
    }

    @Override
    public boolean call() {
        pressButtonByClass("icon-player-next", mCommand);

        return true;
    }
}
