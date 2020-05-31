package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import android.media.MediaCodec;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.liskovsoft.sharedutils.mylogger.Log;

public class MyExoAudioManager {
    private static int sDelayUs;
    private final ExoPreferences mPrefs;

    public MyExoAudioManager(Context context) {
        mPrefs = ExoPreferences.instance(context);
    }

    public void syncState() {
        sDelayUs = mPrefs.getAudioDelayMs() * 1_000;
    }

    public void setAudioDelayMs(int delay) {
        mPrefs.setAudioDelayMs(delay);

        sDelayUs = delay * 1_000;
    }

    public int getAudioDelayMs() {
        return mPrefs.getAudioDelayMs();
    }

    public static class MyMediaCodecAudioRenderer extends MediaCodecAudioRenderer {
        private static final String TAG = MyMediaCodecAudioRenderer.class.getSimpleName();

        public MyMediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
            super(context, mediaCodecSelector);
        }

        public MyMediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys);
        }

        public MyMediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler eventHandler,
                                         @Nullable AudioRendererEventListener eventListener) {
            super(context, mediaCodecSelector, eventHandler, eventListener);
        }

        public MyMediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector,
                                         @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                         boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler,
                                         @Nullable AudioRendererEventListener eventListener) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener);
        }

        public MyMediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector,
                                         @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                         boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler,
                                         @Nullable AudioRendererEventListener eventListener, @Nullable AudioCapabilities audioCapabilities,
                                         AudioProcessor... audioProcessors) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener, audioCapabilities,
                    audioProcessors);
        }

        public MyMediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector,
                                         @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                         boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler,
                                         @Nullable AudioRendererEventListener eventListener, AudioSink audioSink) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener, audioSink);
        }

        public MyMediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, boolean enableDecoderFallback, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, AudioSink audioSink) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, enableDecoderFallback, eventHandler, eventListener, audioSink);
        }

        @Override
        public long getPositionUs() {
            return super.getPositionUs() + sDelayUs;
        }
    }
}
