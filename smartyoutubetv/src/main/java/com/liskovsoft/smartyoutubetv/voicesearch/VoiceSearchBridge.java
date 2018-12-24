package com.liskovsoft.smartyoutubetv.voicesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.view.KeyEvent;

public class VoiceSearchBridge {
    private final Activity mActivity;
    private final VoiceSearchConnector mConnector;
    private final SystemVoiceDialog mSystemDialog;

    public VoiceSearchBridge(Activity activity) {
        mActivity = activity;
        mConnector = new VoiceSearchConnector();
        mSystemDialog = new SystemVoiceDialog(activity);
    }

    public void onKeyEvent(KeyEvent event) {
        if (VERSION.SDK_INT < 21) {
            return;
        }

        // open voice search activity on mic/search key
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_VOICE_ASSIST ||
            keyCode == KeyEvent.KEYCODE_SEARCH) {
            displaySpeechRecognizers();
        }
    }

    private void displaySpeechRecognizers() {
        boolean result = mSystemDialog.displaySpeechRecognizer();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String spokenText = mSystemDialog.onSearchResult(requestCode, resultCode, data);
        openSearchPage(spokenText);
    }

    private void openSearchPage(String searchText) {
        mConnector.openSearchPage(searchText);
    }
}
