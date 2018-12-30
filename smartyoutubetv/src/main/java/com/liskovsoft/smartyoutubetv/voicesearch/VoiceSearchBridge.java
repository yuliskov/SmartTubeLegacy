package com.liskovsoft.smartyoutubetv.voicesearch;

import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.ArrayList;

public class VoiceSearchBridge implements SearchCallback {
    private final VoiceSearchConnector mConnector;
    private final ArrayList<VoiceDialog> mDialogs;

    public VoiceSearchBridge(AppCompatActivity activity) {
        mConnector = new VoiceSearchConnector();
        mDialogs = new ArrayList<>();
        mDialogs.add(new SystemVoiceDialog(activity, this));
        mDialogs.add(new VoiceOverlayDialog(activity, this));
    }

    public boolean onKeyEvent(KeyEvent event) {
        // open voice search activity on mic/search key

        boolean isVoiceAssistKey = false;

        if (VERSION.SDK_INT >= 21) {
            isVoiceAssistKey = event.getKeyCode() == KeyEvent.KEYCODE_VOICE_ASSIST;
        }

        boolean isSearchKey = event.getKeyCode() == KeyEvent.KEYCODE_SEARCH;
        boolean isVoiceKey = isVoiceAssistKey || isSearchKey;

        if (isVoiceKey) {
            boolean isUp = event.getAction() == KeyEvent.ACTION_UP;
            if (isUp) { // run on up action only
                displaySpeechRecognizers();
            }
            return true;
        }

        return false;
    }

    protected void displaySpeechRecognizers() {
        for (VoiceDialog dialog : mDialogs) {
            if (dialog.displaySpeechRecognizer()) { // fist successful attempt is used
                break;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (VoiceDialog dialog : mDialogs) {
            if (dialog instanceof ActivityListener) {
                ((ActivityListener) dialog).onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void openSearchPage(String searchText) {
        mConnector.openSearchPage(searchText);
    }
}
