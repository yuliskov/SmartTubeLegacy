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

    public void onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        if (action == KeyEvent.ACTION_DOWN) {
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
