package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.google.gson.Gson;

import java.util.Map;

public class SendMessageCommand extends GenericCommand {
    private static final String COMMAND = "JavaMessageHandler.handleMessage";
    private final String mMessageId;
    private final String mMessageData;

    public SendMessageCommand(String messageId, String messageData) {
        mMessageId = messageId;
        mMessageData = messageData;
    }

    @Override
    public boolean call() {
        runJSCommand(COMMAND, mMessageId, mMessageData);
        return false;
    }

    private void runJSCommand(String command, String messageId, String messageData) {
        String pattern = "%s('%s', '%s');";
        String patternWithNull = "%s('%s');";

        passToBrowser(String.format(pattern, command, messageId, messageData));
    }

    private String toJson(Map<String, Object> states) {
        Gson gson = new Gson();
        return gson.toJson(states);
    }
}
