package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

public class AutoFrameRateManagerAlt extends AutoFrameRateManager {
    public AutoFrameRateManagerAlt(ExoPlayerFragment playerFragment) {
        super(playerFragment);
    }

    @Override
    public void onAppInit() {
        if (mAutoFrameRateHelper == null) {
            mAutoFrameRateHelper = new AutoFrameRateHelper(mPlayerFragment.getActivity(), new DisplaySyncHelperAlt(mPlayerFragment.getActivity()));
        }

        super.onAppInit();
    }

    @Override
    public void onNextTrack() {
        super.onNextTrack();
    }

    @Override
    public void onPlayerClosed() {
        super.onPlayerClosed();

        if (mAutoFrameRateHelper != null) {
            mAutoFrameRateHelper.restoreOriginalState();
        }
    }
}
