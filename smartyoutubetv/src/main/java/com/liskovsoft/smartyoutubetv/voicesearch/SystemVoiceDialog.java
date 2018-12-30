package com.liskovsoft.smartyoutubetv.voicesearch;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build.VERSION;
import android.speech.RecognizerIntent;
import java.util.List;

class SystemVoiceDialog implements VoiceDialog, ActivityListener {
    private static final int SPEECH_REQUEST_CODE = 11;
    private final Activity mActivity;
    private final SearchCallback mCallback;

    public SystemVoiceDialog(Activity activity, SearchCallback callback) {
        mActivity = activity;
        mCallback = callback;
    }

    public boolean displaySpeechRecognizer() {
        //if (VERSION.SDK_INT < 21) {
        //    return false;
        //}

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        try {
            mActivity.startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // got speech-to-text result, switch to the search page
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == -1) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            if (results != null && results.size() > 0) {
                mCallback.openSearchPage(results.get(0));
            }
        }
    }
}
