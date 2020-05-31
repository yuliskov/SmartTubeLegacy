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
    private static final long THROTTLE_INTERVAL_MS = 5_000;
    private long mPrevCall;
    private HashMap<Float, Float> mFrameRateMapping;

    public AutoFrameRateHelper(Activity context, DisplaySyncHelper syncHelper) {
        mContext = context;
        mSyncHelper = syncHelper;
        mPrefs = ExoPreferences.instance(mContext);

        mSyncHelper.setResolutionSwitchEnabled(mPrefs.isAfrResolutionSwitchEnabled());

        initFrameRateMapping();
    }

    private void initFrameRateMapping() {
        mFrameRateMapping = new HashMap<>();
        mFrameRateMapping.put(30f, 29.97f);
        mFrameRateMapping.put(60f, 59.94f);
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
        float frameRate = correctFps(videoFormat.frameRate);

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
        return mPrefs.isAfrDelayEnabled();
    }

    public void setDelayEnabled(boolean enabled) {
        mPrefs.setAfrDelayEnabled(enabled);
    }

    public boolean isResolutionSwitchEnabled() {
        return mPrefs.isAfrResolutionSwitchEnabled();
    }

    public void setResolutionSwitchEnabled(boolean enabled) {
        mPrefs.setAfrResolutionSwitchEnabled(enabled);
        mSyncHelper.setResolutionSwitchEnabled(enabled);
    }

    public long getDelayTime() {
        return mPrefs.getAfrDelayTime();
    }

    public void setDelayTime(long pauseMS) {
        mPrefs.setAfrDelayTime(pauseMS);
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

    private float correctFps(float frameRate) {
        if (mPrefs.isAfr60fpsCorrectionEnabled() && mFrameRateMapping.containsKey(frameRate)) {
            return mFrameRateMapping.get(frameRate);
        }

        return frameRate;
    }

    public boolean is60fpsCorrectionEnabled() {
        return mPrefs.isAfr60fpsCorrectionEnabled();
    }

    public void set60fpsCorrectionEnabled(boolean checked) {
        mPrefs.setAfr60fpsCorrectionEnabled(checked);
    }
}
