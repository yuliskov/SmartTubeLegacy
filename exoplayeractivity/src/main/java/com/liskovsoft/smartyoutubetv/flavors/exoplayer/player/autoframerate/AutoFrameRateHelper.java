package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.app.Activity;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.DisplaySyncHelper.AutoFrameRateListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

import java.util.HashMap;

class AutoFrameRateHelper {
    private static final String TAG = AutoFrameRateHelper.class.getSimpleName();
    private final Activity mContext;
    private DisplaySyncHelper mSyncHelper;
    private final ExoPreferences mPrefs;
    private SimpleExoPlayer mPlayer;
    private static final long THROTTLE_INTERVAL_MS = 3_000;
    private long mPrevCall;
    private HashMap<Float, Float> mFrameRateMap;

    public AutoFrameRateHelper(Activity context, DisplaySyncHelper syncHelper) {
        mContext = context;
        mSyncHelper = syncHelper;
        mPrefs = ExoPreferences.instance(mContext);

        mSyncHelper.setResolutionSwitchEnabled(mPrefs.isAfrResolutionSwitchEnabled());

        initFrameRateMap();
    }

    private void initFrameRateMap() {
        mFrameRateMap = new HashMap<>();
        mFrameRateMap.put(30f, 29.97f);
        mFrameRateMap.put(60f, 59.94f);
    }

    public void apply() {
        if (!getEnabled()) {
            Log.d(TAG, "Autoframerate not enabled... exiting...");
            return;
        }

        if (mPlayer == null) {
            Log.e(TAG, "Can't apply mode change: player is null");
            return;
        }

        if (mPlayer.getVideoFormat() == null) {
            Log.e(TAG, "Can't apply mode change: format is null");
            return;
        }

        if (System.currentTimeMillis() - mPrevCall < THROTTLE_INTERVAL_MS) {
            Log.e(TAG, "Throttling afr calls...");
            return;
        } else {
            mPrevCall = System.currentTimeMillis();
        }

        Format videoFormat = mPlayer.getVideoFormat();
        float frameRate = videoFormat.frameRate;
        frameRate = transformFrameRate(frameRate);

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

    public boolean isDelayEnabled() {
        return getEnabled() && mPrefs.isAfrDelayEnabled();
    }

    public void setDelayEnabled(boolean enabled) {
        if (getEnabled()) {
            mPrefs.setAfrDelayEnabled(enabled);
        } else {
            MessageHelpers.showMessage(mContext, R.string.autoframerate_not_supported);
        }
    }

    public boolean isResolutionSwitchEnabled() {
        return getEnabled() && mPrefs.isAfrResolutionSwitchEnabled();
    }

    public void setResolutionSwitchEnabled(boolean enabled) {
        if (getEnabled()) {
            mPrefs.setAfrResolutionSwitchEnabled(enabled);
            mSyncHelper.setResolutionSwitchEnabled(enabled);
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

    public void applyModeChangeFix() {
        if (!getEnabled()) {
            return;
        }

        mSyncHelper.applyModeChangeFix(mContext.getWindow());
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

        mSyncHelper.restoreOriginalState(mContext.getWindow());
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

    /**
     * ExoPlayer reports wrong fps for some formats
     */
    private float transformFrameRate(float frameRate) {
        if (mFrameRateMap.containsKey(frameRate)) {
            frameRate = mFrameRateMap.get(frameRate);
        }

        return frameRate;
    }
}
