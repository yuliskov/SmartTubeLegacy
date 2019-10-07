package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.os.Handler;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

public class AutoFrameRateManagerAlt extends AutoFrameRateManager implements Runnable {
    private static final long DELAY_MS = 1_000;
    private Handler mHandler;

    public AutoFrameRateManagerAlt(ExoPlayerFragment playerFragment) {
        super(playerFragment);
    }

    @Override
    public void onAppInit() {
        if (mAutoFrameRateManager == null) {
            mAutoFrameRateManager = new AutoFrameRateHelperAlt(mPlayerFragment.getActivity());
        }

        super.onAppInit();
    }

    @Override
    public void onPlayerCreated() {
        super.onPlayerCreated();

        if (mHandler == null) {
            mHandler = new Handler(mPlayerFragment.getActivity().getMainLooper());
        }

        // minimize afr switching while in the playlist
        mHandler.removeCallbacks(this);
    }

    @Override
    public void onPlayerDestroyed() {
        super.onPlayerDestroyed();

        if (mHandler == null) {
            mHandler = new Handler(mPlayerFragment.getActivity().getMainLooper());
        }

        if (mAutoFrameRateManager != null) {
            mAutoFrameRateManager.restoreOriginalState();

            // minimize afr switching while in the playlist
            //mHandler.postDelayed(this, DELAY_MS);
        }
    }

    @Override
    public void run() {
        mAutoFrameRateManager.restoreOriginalState();
    }
}
