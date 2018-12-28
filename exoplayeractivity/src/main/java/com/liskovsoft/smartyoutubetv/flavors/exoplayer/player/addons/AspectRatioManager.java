package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.content.Context;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;

public class AspectRatioManager {
    private final Context mContext;
    private final SimpleExoPlayerView mPlayerView;

    public AspectRatioManager(Context context, SimpleExoPlayerView playerView) {
        mContext = context;
        mPlayerView = playerView;
    }

    public boolean getZoomEnabled() {
        ExoPreferences prefs = ExoPreferences.instance(mContext);
        return prefs.getVideoZoomEnabled();
    }

    public void setZoomEnabled(boolean zoomEnabled) {
        // Zoom to fit video: https://stackoverflow.com/questions/33608746/in-android-using-exoplayer-how-to-fill-surfaceview-with-a-video-that-does-not

        enableZoom(zoomEnabled);

        ExoPreferences prefs = ExoPreferences.instance(mContext);
        prefs.setVideoZoomEnabled(zoomEnabled);
    }

    private void enableZoom(boolean zoomEnabled) {
        int resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT;

        if (zoomEnabled) {
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH;
        }

        mPlayerView.setResizeMode(resizeMode);
    }
}
