package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;

public class MuteVideoCommand extends GenericCommand {
    private final String mJSCode = "(function () {\n"
            + "\n"
            + "var player = document.getElementsByTagName('video')[0];\n"
            + "if (!player)\n"
            + "    return;\n"
            + "\n"
            + "// we can't pause video because history will not work\n"
            + "function muteVideo() {\n"
            + "    var player = document.getElementsByTagName('video')[0];\n"
            + "    console.log('muteVideo called');\n"
            + "    player.muted = true;\n"
            + "    player.setAttribute('style', '-webkit-filter:brightness(0)');\n"
            + "}\n"
            + "\n"
            + "function onLoadData() {\n"
            + "    console.log('loadeddata called');\n"
            + "    muteVideo();\n"
            + "    player.removeEventListener('loadeddata', onLoadData);\n"
            + "}\n"
            + "\n"
            + "// load events: loadedmetadata, loadeddata\n"
            + "player.addEventListener('loadeddata', onLoadData, false);\n"
            + "\n"
            + "})();\n";
    @Override
    public boolean call() {
        passToBrowser(mJSCode);
        return true;
    }
}
