package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.app.Activity;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

public class AutoFrameRateManager {
    private static final String TAG = AutoFrameRateManager.class.getSimpleName();
    private final Activity mContext;
    private final SimpleExoPlayer mPlayer;
    private final DisplaySyncHelper mSyncHelper;
    private final ExoPreferences mPrefs;

    public AutoFrameRateManager(Activity context, SimpleExoPlayer player) {
        mContext = context;
        mPlayer = player;
        mSyncHelper = new DisplaySyncHelper(mContext);
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

    public int getCurrentModeId() {
        return mSyncHelper.getCurrentModeId();
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
        mSyncHelper.restoreOriginalState();
    }
}
