package com.liskovsoft.smartyoutubetv.exoplayer.commands;

import java.util.concurrent.Callable;

public class PressNextCommand extends PressCommandBase {
    private final Callable<Boolean> mCommand;

    public PressNextCommand(Callable<Boolean> command) {
        mCommand = command;
    }

    @Override
    public Boolean call() {
        pressButtonByClass("icon-player-next", mCommand);

        return true;
    }
}
