package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * <a href="Zoom to fit video: https://stackoverflow.com/questions/33608746/in-android-using-exoplayer-how-to-fill-surfaceview-with-a-video-that-does-not">More info</a>
 */
public class VideoZoomManager {
    public static final int MODE_DEFAULT = AspectRatioFrameLayout.RESIZE_MODE_FIT;
    public static final int MODE_FIT_WIDTH = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH;
    public static final int MODE_FIT_HEIGHT = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT;
    public static final int MODE_FIT_BOTH = AspectRatioFrameLayout.RESIZE_MODE_ZOOM;
    public static final int MODE_STRETCH = AspectRatioFrameLayout.RESIZE_MODE_FILL;
    private final Context mContext;
    private final PlayerView mPlayerView;
    private final ExoPreferences mPrefs;
    private int mMode = MODE_DEFAULT;

    public VideoZoomManager(Context context, PlayerView playerView) {
        mContext = context;
        mPlayerView = playerView;
        mPrefs = ExoPreferences.instance(mContext);

        initZoom();
    }

    private void initZoom() {
        setZoomMode(mPrefs.getVideoZoomMode());
    }

    public int getZoomMode() {
        return mMode;
    }

    public void setZoomMode(int mode) {
        mMode = mode;
        mPrefs.setVideoZoomMode(mode);
        mPlayerView.setResizeMode(mode);
    }
}
