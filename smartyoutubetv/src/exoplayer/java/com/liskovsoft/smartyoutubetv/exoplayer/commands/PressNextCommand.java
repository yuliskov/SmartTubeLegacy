package com.liskovsoft.smartyoutubetv.exoplayer.commands;

public class PressNextCommand extends PressCommandBase {
    @Override
    public Boolean call() {
        return pressButtonByClass("icon-player-next");
    }
}
