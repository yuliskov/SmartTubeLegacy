package com.liskovsoft.smartyoutubetv.voicesearch;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator.YouTubeHelpers;

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

    /**
     * Try to remap {@link KeyEvent#KEYCODE_VOICE_ASSIST} and {@link KeyEvent#KEYCODE_SEARCH}
     * @param event event
     * @return handled
     */
    public boolean onKeyEvent(KeyEvent event) {
        // open voice search activity on mic/search key or joystick Y key
        boolean isSearchKey =
                event.getKeyCode() == KeyEvent.KEYCODE_SEARCH ||
                event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Y;

        if (isSearchKey) {
            boolean isUp = event.getAction() == KeyEvent.ACTION_UP;
            if (isUp) { // remove on up action only
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

    public void openSearchPage(Uri pageUrl) {
        if (pageUrl == null) {
            return;
        }

        String searchString = YouTubeHelpers.extractSearchString(pageUrl.toString());

        if (searchString != null) {
            openSearchPage(searchString);
        }
    }
}
