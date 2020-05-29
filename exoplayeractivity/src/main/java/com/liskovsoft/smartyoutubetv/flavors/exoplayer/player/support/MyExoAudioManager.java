package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
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

    public static class MyMediaCodecAudioRendererAdapter extends MediaCodecAudioRenderer {
        private static final String TAG = MyMediaCodecAudioRendererAdapter.class.getSimpleName();

        public MyMediaCodecAudioRendererAdapter(Context context, MediaCodecSelector mediaCodecSelector) {
            super(context, mediaCodecSelector);
        }

        public MyMediaCodecAudioRendererAdapter(Context context, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys);
        }

        public MyMediaCodecAudioRendererAdapter(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler eventHandler,
                                                @Nullable AudioRendererEventListener eventListener) {
            super(context, mediaCodecSelector, eventHandler, eventListener);
        }

        public MyMediaCodecAudioRendererAdapter(Context context, MediaCodecSelector mediaCodecSelector,
                                                @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                                boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler,
                                                @Nullable AudioRendererEventListener eventListener) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener);
        }

        public MyMediaCodecAudioRendererAdapter(Context context, MediaCodecSelector mediaCodecSelector,
                                                @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                                boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler,
                                                @Nullable AudioRendererEventListener eventListener, @Nullable AudioCapabilities audioCapabilities,
                                                AudioProcessor... audioProcessors) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener, audioCapabilities,
                    audioProcessors);
        }

        public MyMediaCodecAudioRendererAdapter(Context context, MediaCodecSelector mediaCodecSelector,
                                                @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                                boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler,
                                                @Nullable AudioRendererEventListener eventListener, AudioSink audioSink) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener, audioSink);
        }

        public MyMediaCodecAudioRendererAdapter(Context context, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, boolean enableDecoderFallback, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, AudioSink audioSink) {
            super(context, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, enableDecoderFallback, eventHandler, eventListener, audioSink);
        }

        @Override
        public long getPositionUs() {
            return super.getPositionUs() + sDelayUs;
        }
    }
}
