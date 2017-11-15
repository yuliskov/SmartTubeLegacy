package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class SimpleCombinedCommand extends GenericCommand {
    private final GenericCommand[] mCommands;

    public SimpleCombinedCommand(GenericCommand ...commands) {
        super();
        mCommands = commands;
    }

    @Override
    public boolean call() {
        for (GenericCommand cmd : mCommands) {
            if (cmd != null) {
                cmd.call();
            }
        }
        return true;
    }
}
