package com.liskovsoft.smartyoutubetv.voicesearch;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import java.util.List;

public class SystemVoiceDialog implements VoiceDialog {
    private static final int SPEECH_REQUEST_CODE = 11;
    private final Activity mActivity;

    public SystemVoiceDialog(Activity activity) {
        mActivity = activity;
    }

    public boolean displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        try {
            mActivity.startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // open alt voice input app
            // MessageHelpers.showMessage(mActivity, e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String onSearchResult(int requestCode, int resultCode, Intent data) {
        // got speech-to-text result, switch to the search page
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == -1) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            return spokenText;
        }

        return null;
    }
}
