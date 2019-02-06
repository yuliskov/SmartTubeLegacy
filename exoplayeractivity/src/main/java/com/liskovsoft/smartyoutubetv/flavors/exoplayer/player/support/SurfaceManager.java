package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.view.SurfaceHolder;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

public final class SurfaceManager implements SurfaceHolder.Callback {
    private static final int VIDEO_RENDERER_INDEX = 0; // video track
    private final SimpleExoPlayer player;
    private final DefaultTrackSelector trackSelector;

    public SurfaceManager(SimpleExoPlayer player, DefaultTrackSelector trackSelector) {
        this.player = player;
        this.trackSelector = trackSelector;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setVideoSurface(holder.getSurface());
        trackSelector.setParameters(trackSelector.buildUponParameters().setRendererDisabled(getVideoRendererIndex(), false));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        player.setVideoSurface(null);
        trackSelector.setParameters(trackSelector.buildUponParameters().setRendererDisabled(getVideoRendererIndex(), true));
    }

    private int getVideoRendererIndex() {
        for (int i = 0; i < player.getRendererCount(); i++) {
            if (player.getRendererType(i) == C.TRACK_TYPE_VIDEO) {
                return i;
            }
        }

        return VIDEO_RENDERER_INDEX;
    }
}
