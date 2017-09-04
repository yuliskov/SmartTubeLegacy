package com.liskovsoft.smartyoutubetv.exoplayer.commands;

import java.util.concurrent.Callable;

public class PressPrevCommand extends PressCommandBase {
    private final Callable<Boolean> mCommand;

    public PressPrevCommand(Callable<Boolean> command) {
        mCommand = command;
    }

    @Override
    public Boolean call() {
        pressButtonByClass("icon-player-prev", mCommand);
        return true;
    }

}
