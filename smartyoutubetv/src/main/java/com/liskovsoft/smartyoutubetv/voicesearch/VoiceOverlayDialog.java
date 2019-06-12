package com.liskovsoft.smartyoutubetv.voicesearch;

import androidx.appcompat.app.AppCompatActivity;
import com.algolia.instantsearch.voice.VoiceSpeechRecognizer;
import com.algolia.instantsearch.voice.ui.Voice;
import com.algolia.instantsearch.voice.ui.VoiceInputDialogFragment;
import com.algolia.instantsearch.voice.ui.VoicePermissionDialogFragment;

class VoiceOverlayDialog implements VoiceDialog, VoiceSpeechRecognizer.ResultsListener {
    private final AppCompatActivity mActivity;
    private final SearchCallback mCallback;

    private enum Tag {
        Permission,
        Voice
    }

    public VoiceOverlayDialog(AppCompatActivity activity, SearchCallback callback) {
        mActivity = activity;
        mCallback = callback;
    }

    @Override
    public boolean displaySpeechRecognizer() {
        if (!Voice.isRecordAudioPermissionGranted(mActivity)) {
            new VoicePermissionDialogFragment()
                    .show(mActivity.getSupportFragmentManager(), Tag.Permission.name());
        } else {
            showVoiceDialog();
        }

        return true;
    }

    private void showVoiceDialog() {
        VoicePermissionDialogFragment dialog = getPermissionDialog();

        if (dialog != null) {
            dialog.dismiss();
        }

        VoiceInputDialogFragment voiceDialog = getVoiceDialog();

        if (voiceDialog == null) {
            voiceDialog = new VoiceInputDialogFragment();
        } else {
            voiceDialog.dismiss();
        }

        voiceDialog.show(mActivity.getSupportFragmentManager(), Tag.Voice.name());
    }

    private VoicePermissionDialogFragment getPermissionDialog() {
        return (VoicePermissionDialogFragment) mActivity.getSupportFragmentManager().findFragmentByTag(Tag.Permission.name());
    }

    private VoiceInputDialogFragment getVoiceDialog() {
        return (VoiceInputDialogFragment) mActivity.getSupportFragmentManager().findFragmentByTag(Tag.Voice.name());
    }

    @Override
    public void onResults(String[] strings) {
        if (strings.length > 0) {
            mCallback.openSearchPage(strings[0]);
        }
    }
}
