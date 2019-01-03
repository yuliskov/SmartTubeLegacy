package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;

public class JavaScriptMessageHandler {
    private final static String MIC_CLICKED_MESSAGE = "mic_clicked_message";
    private final static String APP_LOADED_MESSAGE = "app_loaded_message";
    private final Context mContext;

    public JavaScriptMessageHandler(Context context) {
        mContext = context;
    }

    public void handleMessage(String message) {
        switch (message) {
            case MIC_CLICKED_MESSAGE:
                Browser.getBus().post(new MicClickedEvent());
                break;
            case APP_LOADED_MESSAGE:
                ((FragmentManagerActivity) mContext).getLoadingManager().hide();
                break;
        }
    }
}
