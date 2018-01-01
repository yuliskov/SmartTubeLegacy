package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.app.Activity;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode.DisplaySyncHelper;

public class AutoFrameRateManager {
    private final Activity mCtx;
    private final SimpleExoPlayer mPlayer;
    private final DisplaySyncHelper mSyncHelper;

    public AutoFrameRateManager(Activity ctx, SimpleExoPlayer player) {
        mCtx = ctx;
        mPlayer = player;
        mSyncHelper = new DisplaySyncHelper(mCtx);
    }

    public void apply() {
        if (mPlayer == null || mPlayer.getVideoFormat() == null) {
            return;
        }
        Format videoFormat = mPlayer.getVideoFormat();
        float frameRate = videoFormat.frameRate;
        int width = videoFormat.width;
        mSyncHelper.syncDisplayMode(mCtx.getWindow(), width, frameRate);
    }

    public boolean getEnabled() {
        return mSyncHelper.getNeedDisplaySync();
    }

    public void setEnabled(boolean enabled) {
        mSyncHelper.setNeedDisplaySync(enabled);
        apply();
    }
}
