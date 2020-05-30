package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.subalignment;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.MyExoAudioManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.MyExoAudioManager.MyMediaCodecAudioRenderer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * My wrapper<br/>
 * Main intent: override audio delay
 */
public class MyAudioDelayOverrideRenderersFactory extends DefaultRenderersFactory {
    private static final String TAG = MyAudioDelayOverrideRenderersFactory.class.getSimpleName();

    public MyAudioDelayOverrideRenderersFactory(FragmentActivity activity) {
        super(activity);
    }

    /**
     * Builds audio renderers for use by the player.
     *
     * @param context The {@link Context} associated with the player.
     * @param extensionRendererMode The extension renderer mode.
     * @param mediaCodecSelector A decoder selector.
     * @param drmSessionManager An optional {@link DrmSessionManager}. May be null if the player will
     *     not be used for DRM protected playbacks.
     * @param playClearSamplesWithoutKeys Whether renderers are permitted to play clear regions of
     *     encrypted media prior to having obtained the keys necessary to decrypt encrypted regions of
     *     the media.
     * @param enableDecoderFallback Whether to enable fallback to lower-priority decoders if decoder
     *     initialization fails. This may result in using a decoder that is slower/less efficient than
     *     the primary decoder.
     * @param audioProcessors An array of {@link AudioProcessor}s that will process PCM audio buffers
     *     before output. May be empty.
     * @param eventHandler A handler to use when invoking event listeners and outputs.
     * @param eventListener An event listener.
     * @param out An array to which the built renderers should be appended.
     */
    @Override
    protected void buildAudioRenderers(
            Context context,
            @ExtensionRendererMode int extensionRendererMode,
            MediaCodecSelector mediaCodecSelector,
            @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
            boolean playClearSamplesWithoutKeys,
            boolean enableDecoderFallback,
            AudioProcessor[] audioProcessors,
            Handler eventHandler,
            AudioRendererEventListener eventListener,
            ArrayList<Renderer> out) {
        new MyExoAudioManager(context).syncState(); // apply audio shift from prefs

        out.add(
                new MyMediaCodecAudioRenderer(
                        context,
                        mediaCodecSelector,
                        drmSessionManager,
                        playClearSamplesWithoutKeys,
                        enableDecoderFallback,
                        eventHandler,
                        eventListener,
                        new DefaultAudioSink(AudioCapabilities.getCapabilities(context), audioProcessors)));

        if (extensionRendererMode == EXTENSION_RENDERER_MODE_OFF) {
            return;
        }
        int extensionRendererIndex = out.size();
        if (extensionRendererMode == EXTENSION_RENDERER_MODE_PREFER) {
            extensionRendererIndex--;
        }

        try {
            // Full class names used for constructor args so the LINT rule triggers if any of them move.
            // LINT.IfChange
            Class<?> clazz = Class.forName("com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer");
            Constructor<?> constructor =
                    clazz.getConstructor(
                            Handler.class,
                            AudioRendererEventListener.class,
                            AudioProcessor[].class);
            // LINT.ThenChange(../../../../../../../proguard-rules.txt)
            Renderer renderer =
                    (Renderer) constructor.newInstance(eventHandler, eventListener, audioProcessors);
            out.add(extensionRendererIndex++, renderer);
            Log.i(TAG, "Loaded LibopusAudioRenderer.");
        } catch (ClassNotFoundException e) {
            // Expected if the app was built without the extension.
        } catch (Exception e) {
            // The extension is present, but instantiation failed.
            throw new RuntimeException("Error instantiating Opus extension", e);
        }

        try {
            // Full class names used for constructor args so the LINT rule triggers if any of them move.
            // LINT.IfChange
            Class<?> clazz = Class.forName("com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer");
            Constructor<?> constructor =
                    clazz.getConstructor(
                            Handler.class,
                            AudioRendererEventListener.class,
                            AudioProcessor[].class);
            // LINT.ThenChange(../../../../../../../proguard-rules.txt)
            Renderer renderer =
                    (Renderer) constructor.newInstance(eventHandler, eventListener, audioProcessors);
            out.add(extensionRendererIndex++, renderer);
            Log.i(TAG, "Loaded LibflacAudioRenderer.");
        } catch (ClassNotFoundException e) {
            // Expected if the app was built without the extension.
        } catch (Exception e) {
            // The extension is present, but instantiation failed.
            throw new RuntimeException("Error instantiating FLAC extension", e);
        }

        try {
            // Full class names used for constructor args so the LINT rule triggers if any of them move.
            // LINT.IfChange
            Class<?> clazz =
                    Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer");
            Constructor<?> constructor =
                    clazz.getConstructor(
                            Handler.class,
                            AudioRendererEventListener.class,
                            AudioProcessor[].class);
            // LINT.ThenChange(../../../../../../../proguard-rules.txt)
            Renderer renderer =
                    (Renderer) constructor.newInstance(eventHandler, eventListener, audioProcessors);
            out.add(extensionRendererIndex++, renderer);
            Log.i(TAG, "Loaded FfmpegAudioRenderer.");
        } catch (ClassNotFoundException e) {
            // Expected if the app was built without the extension.
        } catch (Exception e) {
            // The extension is present, but instantiation failed.
            throw new RuntimeException("Error instantiating FFmpeg extension", e);
        }
    }
}
