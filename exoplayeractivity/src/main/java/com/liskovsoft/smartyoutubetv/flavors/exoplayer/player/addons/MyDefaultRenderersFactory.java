package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.util.ArrayList;

public class MyDefaultRenderersFactory extends DefaultRenderersFactory {
    public MyDefaultRenderersFactory(FragmentActivity activity, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                     int extensionRendererModeOn) {
        super(activity, drmSessionManager, extensionRendererModeOn);
    }

    @Override
    protected void buildVideoRenderers(Context context,
                                       DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                       long allowedVideoJoiningTimeMs,
                                       Handler eventHandler,
                                       VideoRendererEventListener eventListener,
                                       int extensionRendererMode,
                                       ArrayList<Renderer> out) {
        super.buildVideoRenderers(context, drmSessionManager, allowedVideoJoiningTimeMs, eventHandler, eventListener, extensionRendererMode, out);

        out.add(0, new LibvpxVideoRenderer(true, 10_000));
    }
}
