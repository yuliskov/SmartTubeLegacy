package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.google.gson.Gson;

import java.util.Map;

public class SyncButtonsCommand extends GenericCommand {
    private static final String COMMAND = "ExoUtils.syncButtons";
    private final Map<String, Object> mStates;

    public SyncButtonsCommand(Map<String, Object> buttonStates) {
        mStates = buttonStates;
    }

    @Override
    public boolean call() {
        String json = toJson(mStates);
        runJSCommand(COMMAND, json);
        return false;
    }

    private void runJSCommand(String command, String ...params) {
        String pattern = "%s(%s);";
        StringBuilder concatParams = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            concatParams.append(params[i]);
            boolean notLast = i < (params.length - 1);
            if (notLast) concatParams.append(", ");
        }
        passToBrowser(String.format(pattern, command, concatParams));
    }

    private String toJson(Map<String, Object> states) {
        Gson gson = new Gson();
        return gson.toJson(states);
    }
}
