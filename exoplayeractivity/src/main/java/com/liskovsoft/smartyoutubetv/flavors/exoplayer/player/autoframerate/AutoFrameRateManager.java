package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerEventListener;

public class AutoFrameRateManager implements PlayerEventListener {
    protected final ExoPlayerFragment mPlayerFragment;
    protected AutoFrameRateHelper mAutoFrameRateManager;

    public AutoFrameRateManager(ExoPlayerFragment playerFragment) {
        mPlayerFragment = playerFragment;
    }

    @Override
    public void onAppPause() {
        if (mAutoFrameRateManager != null) {
            mAutoFrameRateManager.saveLastState();
            mAutoFrameRateManager.restoreOriginalState();
        }
    }

    @Override
    public void onAppResume() {
        if (mAutoFrameRateManager != null) {
            mAutoFrameRateManager.restoreLastState();
        }
    }

    @Override
    public void onAppInit() {
        if (mAutoFrameRateManager == null) { // at this moment fragment has been attached to activity
            mAutoFrameRateManager = new AutoFrameRateHelper(mPlayerFragment.getActivity());
        }

        mAutoFrameRateManager.saveOriginalState();
    }

    @Override
    public void onPlayerCreated() {
        if (mAutoFrameRateManager == null) { // at this moment fragment has been attached to activity
            mAutoFrameRateManager = new AutoFrameRateHelper(mPlayerFragment.getActivity());
        }

        mAutoFrameRateManager.setPlayer(mPlayerFragment.getPlayer()); // new player is crated
    }

    @Override
    public void onPlayerDestroyed() {
        if (mAutoFrameRateManager != null) {
            mAutoFrameRateManager.setPlayer(null);
        }
    }

    @Override
    public void onPlaybackReady() {
        if (mAutoFrameRateManager != null) {
            mAutoFrameRateManager.apply();
        }
    }

    public boolean isEnabled() {
        if (mAutoFrameRateManager == null) {
            return false;
        }

        return mAutoFrameRateManager.getEnabled();
    }

    public void setEnabled(boolean enabled) {
        if (mAutoFrameRateManager != null) {
            mAutoFrameRateManager.setEnabled(enabled);
        }
    }
}
