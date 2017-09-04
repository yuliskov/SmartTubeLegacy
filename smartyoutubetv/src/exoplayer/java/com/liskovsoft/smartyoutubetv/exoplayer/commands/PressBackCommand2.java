package com.liskovsoft.smartyoutubetv.exoplayer.commands;

public class PressBackCommand2 extends PressCommandBase {
    @Override
    public boolean call() {
        return pressButtonByClass("back no-model legend-item");
    }
}
