package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.app.Activity;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

class AutoFrameRateHelper {
    private static final String TAG = AutoFrameRateHelper.class.getSimpleName();
    private final Activity mContext;
    protected DisplaySyncHelper mSyncHelper;
    private final ExoPreferences mPrefs;
    private SimpleExoPlayer mPlayer;
    private AutoFrameRateListener mListener;

    public AutoFrameRateHelper(Activity context, DisplaySyncHelper syncHelper) {
        mContext = context;
        mSyncHelper = syncHelper;
        mPrefs = ExoPreferences.instance(mContext);
    }

    public void apply() {
        if (!getEnabled()) {
            Log.d(TAG, "apply: autoframerate not enabled... exiting...");
            return;
        }

        if (mPlayer == null || mPlayer.getVideoFormat() == null) {
            Log.e(TAG, "Can't apply mode change: player or format is null");
            return;
        }


        Format videoFormat = mPlayer.getVideoFormat();
        float frameRate = videoFormat.frameRate;
        int width = videoFormat.width;
        Log.d(TAG, String.format("Applying mode change... Video fps: %s, width: %s", frameRate, width));
        boolean result = mSyncHelper.syncDisplayMode(mContext.getWindow(), width, frameRate);

        if (result && mListener != null) {
            mListener.onModeStart();
        }
    }

    public boolean getEnabled() {
        return mPrefs.getAutoframerateChecked();
    }

    public void setEnabled(boolean enabled) {
        if (!DisplaySyncHelper.supportsDisplayModeChange()) {
            MessageHelpers.showMessage(mContext, R.string.autoframerate_not_supported);
            enabled = false;
        }

        mPrefs.setAutoframerateChecked(enabled);
        apply();
    }

    public void saveOriginalState() {
        if (!getEnabled()) {
            return;
        }

        mSyncHelper.saveOriginalState();
    }

    public void restoreOriginalState() {
        if (!getEnabled()) {
            Log.d(TAG, "restoreOriginalState: autoframerate not enabled... exiting...");
            return;
        }

        Log.d(TAG, "Restoring original mode...");

        boolean result = mSyncHelper.restoreOriginalState(mContext.getWindow());

        if (result && mListener != null) {
            mListener.onModeStart();
        }
    }
    
    public void setPlayer(SimpleExoPlayer player) {
        mPlayer = player;
    }

    public void setListener(AutoFrameRateListener listener) {
        mListener = listener;
    }

    interface AutoFrameRateListener {
        void onModeStart();
    }
}
