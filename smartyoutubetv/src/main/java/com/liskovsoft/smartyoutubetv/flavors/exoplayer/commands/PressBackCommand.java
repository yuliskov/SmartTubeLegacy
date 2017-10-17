package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

public class PressBackCommand extends PressCommandBase {
    @Override
    public boolean call() {
        return pressButtonByClass("back no-model legend-item");
    }
}
