package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerEventListener;

public class AutoFrameRateManager implements PlayerEventListener {
    protected final ExoPlayerFragment mPlayerFragment;
    protected AutoFrameRateHelper mAutoFrameRateHelper;

    public AutoFrameRateManager(ExoPlayerFragment playerFragment) {
        mPlayerFragment = playerFragment;
    }

    @Override
    public void onAppPause() {
        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.saveLastState();
            mAutoFrameRateHelper.restoreOriginalState();
        }
    }

    @Override
    public void onAppResume() {
        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.restoreLastState();
        }
    }

    @Override
    public void onAppInit() {
        if (mAutoFrameRateHelper == null) { // at this moment fragment has been attached to activity
            mAutoFrameRateHelper = new AutoFrameRateHelper(mPlayerFragment.getActivity(), new DisplaySyncHelper(mPlayerFragment.getActivity()));
        }

        mAutoFrameRateHelper.saveOriginalState();
    }

    @Override
    public void onPlayerCreated() {
        mAutoFrameRateHelper.setPlayer(mPlayerFragment.getPlayer()); // new player is crated
    }

    @Override
    public void onPlayerDestroyed() {
        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.setPlayer(null);
        }
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
}
