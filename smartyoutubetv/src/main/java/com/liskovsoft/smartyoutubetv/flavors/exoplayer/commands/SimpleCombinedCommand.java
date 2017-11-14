package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class SimpleCombinedCommand extends GenericCommand {
    private final GenericCommand mMainCommand;
    private final GenericCommand[] mCommands;

    public SimpleCombinedCommand(GenericCommand mainCommand, GenericCommand ...commands) {
        super();
        mMainCommand = mainCommand;
        mCommands = commands;
    }

    @Override
    public boolean call() {
        for (GenericCommand cmd : mCommands) {
            if (cmd != null) {
                cmd.call();
            }
        }
        if (mMainCommand != null) {
            mMainCommand.call();
        }
        return true;
    }
}
