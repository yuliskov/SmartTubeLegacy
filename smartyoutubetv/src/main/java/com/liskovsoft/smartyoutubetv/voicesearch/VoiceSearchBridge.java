package com.liskovsoft.smartyoutubetv.voicesearch;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build.VERSION;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;

import java.util.List;

public class VoiceSearchBridge {
    private static final int SPEECH_REQUEST_CODE = 11;
    private final Activity mActivity;
    private final VoiceSearchConnector mConnector;

    public VoiceSearchBridge(Activity activity) {
        mActivity = activity;
        mConnector = new VoiceSearchConnector();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // got speech-to-text result, switch to the search page
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == -1) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            openSearchPage(spokenText);
        }
    }

    public void onKeyEvent(KeyEvent event) {
        if (VERSION.SDK_INT < 21) {
            return;
        }

        // open voice search activity on mic key
        if (event.getKeyCode() != KeyEvent.KEYCODE_VOICE_ASSIST) {
            return;
        }

        displaySpeechRecognizer();
    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        try {
            mActivity.startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            MessageHelpers.showMessage(mActivity, e.getLocalizedMessage());
        }
    }

    private void openSearchPage(String searchText) {
        mConnector.openSearchPage(searchText);
    }
}
