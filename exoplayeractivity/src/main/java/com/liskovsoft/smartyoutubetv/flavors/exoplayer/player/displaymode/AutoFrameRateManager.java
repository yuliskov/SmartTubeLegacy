package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode;

import android.app.Activity;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class AutoFrameRateManager {
    private final Activity mContext;
    private final SimpleExoPlayer mPlayer;
    private final DisplaySyncHelper mSyncHelper;

    public AutoFrameRateManager(Activity context, SimpleExoPlayer player) {
        mContext = context;
        mPlayer = player;
        mSyncHelper = new DisplaySyncHelper(mContext);
    }

    public void apply() {
        if (!getEnabled()) {
            return;
        }

        if (mPlayer == null || mPlayer.getVideoFormat() == null) {
            return;
        }

        Format videoFormat = mPlayer.getVideoFormat();
        float frameRate = videoFormat.frameRate;
        int width = videoFormat.width;
        mSyncHelper.syncDisplayMode(mContext.getWindow(), width, frameRate);
    }

    public boolean getEnabled() {
        return mSyncHelper.getNeedDisplaySync();
    }

    public void setEnabled(boolean enabled) {
        mSyncHelper.setNeedDisplaySync(enabled);
        apply();
    }

    public int getCurrentModeId() {
        return mSyncHelper.getCurrentModeId();
    }
}
