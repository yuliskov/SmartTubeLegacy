package com.liskovsoft.smartyoutubetv.voicesearch;

import android.app.Activity;
import com.algolia.instantsearch.voice.VoiceSpeechRecognizer;
import com.algolia.instantsearch.voice.ui.Voice;
import com.algolia.instantsearch.voice.ui.VoicePermissionDialogFragment;

public class VoiceOverlayDialog implements VoiceDialog, VoiceSpeechRecognizer.ResultsListener {
    private final Activity mActivity;

    private enum Tag {
        Permission,
        Voice
    }

    public VoiceOverlayDialog(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean displaySpeechRecognizer() {
        if (!Voice.isRecordAudioPermissionGranted(mActivity)) {
            new VoicePermissionDialogFragment().show(supportFragmentManager, Tag.Permission.name());
        } else {
            showVoiceDialog();
        }

        return true;
    }

    private void showVoiceDialog() {
        getPermissionDialog()?.dismiss()
        (getVoiceDialog() ?: VoiceInputDialogFragment()).let {
            it.setSuggestions(
                    "Phone case",
                    "Running shoes"
            )
            it.show(supportFragmentManager, Tag.Voice.name)
        }
    }

    private boolean getPermissionDialog() {
        return false;
    }

    @Override
    public void onResults(String[] strings) {

    }
}
