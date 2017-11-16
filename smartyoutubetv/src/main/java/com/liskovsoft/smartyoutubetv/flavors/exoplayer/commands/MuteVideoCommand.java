package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class MuteVideoCommand extends GenericCommand {
    @Override
    public boolean call() {
        passToBrowser("helpers.muteVideo();");
        return true;
    }
}
