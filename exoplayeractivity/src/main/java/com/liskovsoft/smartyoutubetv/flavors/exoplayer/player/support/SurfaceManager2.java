package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import android.view.SurfaceHolder;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.video.DummySurface;

public final class SurfaceManager2 implements SurfaceHolder.Callback {
    private final Context ctx;
    private final SimpleExoPlayer player;
    private DummySurface dummySurface;

    public SurfaceManager2(Context ctx, SimpleExoPlayer player) {
        this.player = player;
        this.ctx = ctx;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setVideoSurface(holder.getSurface());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (dummySurface == null) {
            dummySurface = DummySurface.newInstanceV17(ctx,false);
        }
        player.setVideoSurface(dummySurface);
    }

    public void release() {
        if (dummySurface != null) {
            dummySurface.release();
            dummySurface = null;
        }
    }

}
