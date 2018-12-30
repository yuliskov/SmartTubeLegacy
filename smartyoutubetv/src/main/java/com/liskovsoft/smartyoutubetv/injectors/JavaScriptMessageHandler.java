package com.liskovsoft.smartyoutubetv.injectors;

import com.liskovsoft.browser.Browser;

public class JavaScriptMessageHandler {
    private final static String MIC_CLICKED_MESSAGE = "mic_clicked_message";

    public void handleMessage(String message) {
        switch (message) {
            case MIC_CLICKED_MESSAGE:
                Browser.getBus().post(new MicClickedEvent());
                break;
        }
    }
}
