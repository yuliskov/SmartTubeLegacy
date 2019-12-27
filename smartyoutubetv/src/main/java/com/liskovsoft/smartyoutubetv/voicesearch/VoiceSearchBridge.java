package com.liskovsoft.smartyoutubetv.voicesearch;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator.YouTubeHelpers;

import java.util.ArrayList;

public class VoiceSearchBridge implements SearchCallback {
    private final VoiceSearchConnector mConnector;
    private final ArrayList<VoiceDialog> mDialogs;
    private final AppCompatActivity mActivity;

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
        // open voice search activity on mic/search key or joystick Y key
        boolean isSearchKey =
                event.getKeyCode() == KeyEvent.KEYCODE_SEARCH ||
                event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Y;

        if (isSearchKey) {
            boolean isDown = event.getAction() == KeyEvent.ACTION_DOWN; // user holding button

            if (isDown) { // remove on up action only
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
        // close exo player (if opened)
        if (mActivity instanceof FragmentManagerActivity) {
            ((FragmentManagerActivity) mActivity).onSearchQuery();
        }

        mConnector.openSearchPage(searchText);
    }

    public boolean openSearchPage(Uri pageUrl) {
        if (pageUrl == null) {
            return false;
        }

        String searchString = YouTubeHelpers.extractSearchString(pageUrl.toString());

        if (searchString != null) {
            openSearchPage(searchString);
        }

        return searchString != null;
    }
}
