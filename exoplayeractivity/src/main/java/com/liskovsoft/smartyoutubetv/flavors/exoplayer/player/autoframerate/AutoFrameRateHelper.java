package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.app.Activity;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.DisplaySyncHelper.AutoFrameRateListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

class AutoFrameRateHelper {
    private static final String TAG = AutoFrameRateHelper.class.getSimpleName();
    private final Activity mContext;
    protected DisplaySyncHelper mSyncHelper;
    private final ExoPreferences mPrefs;
    private SimpleExoPlayer mPlayer;

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
        mSyncHelper.syncDisplayMode(mContext.getWindow(), width, frameRate);
    }

    public boolean getEnabled() {
        return mSyncHelper.supportsDisplayModeChangeComplex() && mPrefs.getAutoframerateChecked();
    }

    public void setEnabled(boolean enabled) {
        if (!mSyncHelper.supportsDisplayModeChangeComplex()) {
            MessageHelpers.showMessage(mContext, R.string.autoframerate_not_supported);
            enabled = false;
        }

        mPrefs.setAutoframerateChecked(enabled);
        apply();
    }

    public boolean getDelayEnabled() {
        return getEnabled() && mPrefs.isAfrDelayEnabled();
    }

    public void setDelayEnabled(boolean enabled) {
        if (getEnabled()) {
            mPrefs.setAfrDelayEnabled(enabled);
        } else {
            MessageHelpers.showMessage(mContext, R.string.autoframerate_not_supported);
        }
    }

    public long getDelayTime() {
        if (getEnabled()) {
            return mPrefs.getAfrDelayTime();
        }

        return 0;
    }

    public void setDelayTime(long pauseMS) {
        if (getEnabled()) {
            mPrefs.setAfrDelayTime(pauseMS);
        } else {
            MessageHelpers.showMessage(mContext, R.string.autoframerate_not_supported);
        }
    }

    public void saveOriginalState() {
        if (!getEnabled()) {
            return;
        }

        mSyncHelper.saveOriginalState();
    }

    public void applyModeChangeFix() {
        if (!getEnabled()) {
            return;
        }

        mSyncHelper.applyModeChangeFix(mContext.getWindow());
    }

    public void restoreOriginalState() {
        if (!getEnabled()) {
            Log.d(TAG, "restoreOriginalState: autoframerate not enabled... exiting...");
            return;
        }

        Log.d(TAG, "Restoring original mode...");

        mSyncHelper.restoreOriginalState(mContext.getWindow());
    }

    public void saveCurrentState() {
        if (!getEnabled()) {
            return;
        }

        mSyncHelper.saveCurrentState();
    }

    public void restoreCurrentState() {
        if (!getEnabled()) {
            Log.d(TAG, "restoreCurrentState: autoframerate not enabled... exiting...");
            return;
        }

        Log.d(TAG, "Restoring current mode...");

        mSyncHelper.restoreCurrentState(mContext.getWindow());
    }

    public void setPlayer(SimpleExoPlayer player) {
        mPlayer = player;
    }

    public void setListener(AutoFrameRateListener listener) {
        mSyncHelper.setListener(listener);
    }

    public void resetStats() {
        mSyncHelper.resetStats();
    }
}
