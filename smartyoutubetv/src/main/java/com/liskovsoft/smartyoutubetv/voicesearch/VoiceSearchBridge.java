package com.liskovsoft.smartyoutubetv.voicesearch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.KeyEvent;
import androidx.appcompat.app.AppCompatActivity;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator.YouTubeHelpers;

import java.util.ArrayList;

public class VoiceSearchBridge implements SearchCallback {
    private final VoiceSearchConnector mConnector;
    private final ArrayList<VoiceDialog> mDialogs;
    private final Activity mActivity;

    public VoiceSearchBridge(AppCompatActivity activity) {
        mActivity = activity;
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
        boolean isSearchKey;

        // open voice search activity on mic/search key or joystick Y key
        if (VERSION.SDK_INT >= 21) {
            isSearchKey =
                    event.getKeyCode() == KeyEvent.KEYCODE_SEARCH ||
                    event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Y ||
                    event.getKeyCode() == KeyEvent.KEYCODE_VOICE_ASSIST;
        } else {
            isSearchKey =
                event.getKeyCode() == KeyEvent.KEYCODE_SEARCH ||
                event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Y;
        }

        if (isSearchKey) {
            boolean isDown = event.getAction() == KeyEvent.ACTION_DOWN; // user holding button

            if (isDown) { // remove on up action only
                displaySpeechRecognizers();
            }

            return true;
        }

        return false;
    }

    public void displaySpeechRecognizers() {
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
        if (searchText == null) {
            return;
        }

        // close exo player (if opened)
        if (mActivity instanceof SearchListener) {
            ((SearchListener) mActivity).onSearchQueryReceived();
        }

        mConnector.openSearchPage(searchText);
    }

    public void openSearchPage(Uri pageUrl) {
        if (pageUrl == null) {
            return;
        }

        String searchString = null;

        try {
            searchString = YouTubeHelpers.extractSearchString(pageUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
            MessageHelpers.showLongMessage(mActivity, e.getMessage());
        }

        if (searchString != null) {
            openSearchPage(searchString);
        }
    }

    public interface SearchListener {
        void onSearchQueryReceived();
    }
}
