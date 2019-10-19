package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerEventListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateHelper.AutoFrameRateListener;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class AutoFrameRateManager implements PlayerEventListener, AutoFrameRateListener {
    private static final long AFR_MSG_HIDE_DELAY = 5_000;
    protected final ExoPlayerFragment mPlayerFragment;
    protected AutoFrameRateHelper mAutoFrameRateHelper;

    public AutoFrameRateManager(ExoPlayerFragment playerFragment) {
        mPlayerFragment = playerFragment;
    }

    @Override
    public void onAppInit() {
        if (mAutoFrameRateHelper == null) { // at this moment fragment has been attached to activity
            mAutoFrameRateHelper = new AutoFrameRateHelper(mPlayerFragment.getActivity(), new DisplaySyncHelper(mPlayerFragment.getActivity()));
        }

        mAutoFrameRateHelper.saveOriginalState();
        mAutoFrameRateHelper.setListener(this);
    }

    @Override
    public void onPlayerCreated() {
        mAutoFrameRateHelper.setPlayer(mPlayerFragment.getPlayer()); // new player is crated
    }

    @Override
    public void onPlayerClosed() {
        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.setPlayer(null);
        }
    }

    @Override
    public void onTrackEnded() {
        
    }

    @Override
    public void onPlaybackReady() {
        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.apply();
        }
    }

    public boolean isEnabled() {
        if (mAutoFrameRateHelper == null) {
            return false;
        }

        return mAutoFrameRateHelper.getEnabled();
    }

    public void setEnabled(boolean enabled) {
        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.setEnabled(enabled);
        }
    }

    public boolean isDelayEnabled() {
        if (mAutoFrameRateHelper == null) {
            return false;
        }

        return mAutoFrameRateHelper.getDelayEnabled();
    }

    public void setDelayEnabled(boolean enabled) {
        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.setDelayEnabled(enabled);
        }
    }

    @Override
    public void onModeStart() {
        if (isDelayEnabled()) {
            mPlayerFragment.startPlaybackDelay(AFR_MSG_HIDE_DELAY);
            mPlayerFragment.showMessage(R.string.changing_video_frame_rate, AFR_MSG_HIDE_DELAY);
        }
    }

    @Override
    public void onAppPause() {
        // save last state
    }

    @Override
    public void onAppResume() {
        // restore last state
    }
}
