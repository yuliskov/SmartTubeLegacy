package com.liskovsoft.smartyoutubetv.exoplayer.commands;

public class PausePlayerCommand extends GenericCommand {
    private final String mJSCode = "(function () {\n"
            + "\n"
            + "var pauseVideo = function() {\n"
            + "   console.log('pauseVideo called');\n"
            + "   var player = document.getElementsByTagName('video')[0];\n"
            + "   player.muted = true;\n"
            + "   player.pause();    \n"
            + "}\n"
            + "\n"
            + "pauseVideo();\n"
            + "\n"
            + "})();\n";
    private final String mJSCode2 = "(function () {\n"
            + "\n"
            + "var pauseVideo = function() {\n"
            + "   console.log('pauseVideo called');\n"
            + "   var player = document.getElementsByTagName('video')[0];\n"
            + "   player.muted = true;\n"
            + "   player.pause();    \n"
            + "}\n"
            + "\n"
            + "var onLoad1 = function() {\n"
            + "   pauseVideo();\n"
            + "   player.removeEventListener('loadeddata', onLoad1);\n"
            + "}\n"
            + "\n"
            + "var onLoad2 = function() {\n"
            + "   pauseVideo();\n"
            + "   player.removeEventListener('loadedmetadata', onLoad2);\n"
            + "}\n"
            + "\n"
            + "pauseVideo();\n"
            + "var player = document.getElementsByTagName('video')[0];\n"
            + "player.addEventListener('loadeddata', onLoad1, false);\n"
            + "player.addEventListener('loadedmetadata', onLoad2, false);\n"
            + "\n"
            + "})();\n";
    @Override
    public boolean call() {
        passToBrowser(mJSCode);
        return true;
    }
}
