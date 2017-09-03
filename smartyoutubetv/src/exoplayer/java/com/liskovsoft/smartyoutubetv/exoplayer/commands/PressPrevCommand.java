package com.liskovsoft.smartyoutubetv.exoplayer.commands;

public class PressPrevCommand extends PressCommandBase {
    @Override
    public Boolean call() {
        return pressButtonByClass("icon-player-prev");
    }
}
