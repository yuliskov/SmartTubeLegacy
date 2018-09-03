package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.ExtendedDataHolder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.TextToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.UUID;

public abstract class PlayerCoreActivity extends Activity implements OnClickListener, Player.EventListener, PlaybackControlView.VisibilityListener {
    private static final String TAG = PlayerCoreActivity.class.getName();
    protected static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final CookieManager DEFAULT_COOKIE_MANAGER;
    
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES = "drm_key_request_properties";
    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";

    public static final String ACTION_VIEW = "com.google.android.exoplayer.demo.action.VIEW";
    public static final String EXTENSION_EXTRA = "extension";

    public static final String ACTION_VIEW_LIST = "com.google.android.exoplayer.demo.action.VIEW_LIST";
    public static final String URI_LIST_EXTRA = "uri_list";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";

    public static final String MPD_CONTENT_EXTRA = "mpd_content";
    public static final String DELIMITER = "------";

    protected EventLogger eventLogger;

    protected SimpleExoPlayer player;
    protected DefaultTrackSelector trackSelector;

    protected SimpleExoPlayerView simpleExoPlayerView;
    protected LinearLayout debugRootView;
    protected FrameLayout debugViewGroup;
    protected TextToggleButton retryButton;

    private DataSource.Factory mediaDataSourceFactory;

    protected boolean needRetrySource;
    protected boolean shouldAutoPlay;
    private int resumeWindow;
    private long resumePosition;
    protected LinearLayout playerTopBar;

    private Handler mainHandler;
    private TrackGroupArray lastSeenTrackGroupArray;

    protected TrackSelectionHelper trackSelectionHelper;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shouldAutoPlay = true;
        clearResumePosition();
        mediaDataSourceFactory = buildDataSourceFactory(true);
        mainHandler = new Handler();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        setContentView(R.layout.player_activity);
        View rootView = findViewById(R.id.root);
        rootView.setOnClickListener(this);
        debugRootView = (LinearLayout) findViewById(R.id.controls_root);
        debugViewGroup = (FrameLayout) findViewById(R.id.debug_view_group);
        playerTopBar = (LinearLayout) findViewById(R.id.player_top_bar);
        retryButton = (TextToggleButton) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.setControllerVisibilityListener(this);
        simpleExoPlayerView.requestFocus();
    }

    public void initializePlayer() {
        Intent intent = getIntent();
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            initializeTrackSelector();

            lastSeenTrackGroupArray = null;

            UUID drmSchemeUuid = intent.hasExtra(DRM_SCHEME_UUID_EXTRA) ? UUID.fromString(intent.getStringExtra(DRM_SCHEME_UUID_EXTRA)) : null;
            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
            if (drmSchemeUuid != null) {
                String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
                String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
                try {
                    drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl, keyRequestPropertiesArray);
                } catch (UnsupportedDrmException e) {
                    int errorStringId = Util.SDK_INT < 18 ? R.string.error_drm_not_supported : (e.reason == UnsupportedDrmException
                            .REASON_UNSUPPORTED_SCHEME ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
                    showToast(errorStringId);
                    return;
                }
            }

            boolean preferExtensionDecoders = intent.getBooleanExtra(PREFER_EXTENSION_DECODERS, false);
            @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode = ((ExoApplication) getApplication()).useExtensionRenderers()
                    ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER : DefaultRenderersFactory
                    .EXTENSION_RENDERER_MODE_ON) : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this, drmSessionManager, extensionRendererMode);


            // increase player's buffer size to 60 secs
            // usage: ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl)
            DefaultLoadControl loadControl = new DefaultLoadControl(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE),
                    DefaultLoadControl.DEFAULT_MIN_BUFFER_MS * 2,
                    DefaultLoadControl.DEFAULT_MAX_BUFFER_MS * 2,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            //player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);

            player.addListener(this);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
            player.setMetadataOutput(eventLogger);

            simpleExoPlayerView.setPlayer(player);
            player.setPlayWhenReady(shouldAutoPlay);
        }
        if (needNewPlayer || needRetrySource) {
            String action = intent.getAction();
            Uri[] uris;
            String[] extensions;
            if (ACTION_VIEW.equals(action)) {
                uris = new Uri[]{intent.getData()};

                extensions = new String[]{intent.getStringExtra(EXTENSION_EXTRA)};
            } else if (ACTION_VIEW_LIST.equals(action)) {
                String[] uriStrings = intent.getStringArrayExtra(URI_LIST_EXTRA);
                uris = new Uri[uriStrings.length];
                for (int i = 0; i < uriStrings.length; i++) {
                    uris[i] = Uri.parse(uriStrings[i]);
                }
                extensions = intent.getStringArrayExtra(EXTENSION_LIST_EXTRA);
                if (extensions == null) {
                    extensions = new String[uriStrings.length];
                }
            } else {
                showToast(getString(R.string.unexpected_intent_action, action));
                return;
            }
            if (Util.maybeRequestReadExternalStoragePermission(this, uris)) {
                // The player will be reinitialized if the permission is granted.
                return;
            }
            MediaSource[] mediaSources = new MediaSource[uris.length];
            for (int i = 0; i < uris.length; i++) {
                // NOTE: supply audio and video tracks in one field
                String[] split = uris[i].toString().split(DELIMITER);
                if (split.length == 2) {
                    mediaSources[i] = new MergingMediaSource(buildMediaSource(Uri.parse(split[0]), null), buildMediaSource(Uri.parse(split[1]),
                            null));
                    continue;
                }
                // NOTE: supply audio and video tracks in one field
                String smallExtra = intent.getStringExtra(MPD_CONTENT_EXTRA);
                String bigExtra = (String) ExtendedDataHolder.getInstance().getExtra(MPD_CONTENT_EXTRA);
                if (smallExtra != null) {
                    mediaSources[i] = buildMPDMediaSource(uris[i], smallExtra);
                    continue;
                } else if (bigExtra != null) { // stored externally?
                    mediaSources[i] = buildMPDMediaSource(uris[i], bigExtra);
                    continue;
                }
                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }

            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);

            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare(mediaSource, !haveResumePosition, false);

            needRetrySource = false;
            updateButtonVisibilities();
        }
    }

    protected abstract void initializeTrackSelector();

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri) : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource(uri, buildDataSourceFactory(false), new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler,
                        eventLogger);
            case C.TYPE_DASH:
                return new DashMediaSource(uri, buildDataSourceFactory(false), new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                        mainHandler, eventLogger);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(), mainHandler, eventLogger);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(UUID uuid, String licenseUrl, String[] keyRequestPropertiesArray) throws
            UnsupportedDrmException {
        if (Util.SDK_INT < 18) {
            return null;
        }
        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl, buildHttpDataSourceFactory(false));
        if (keyRequestPropertiesArray != null) {
            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i], keyRequestPropertiesArray[i + 1]);
            }
        }
        return new DefaultDrmSessionManager<>(uuid, FrameworkMediaDrm.newInstance(uuid), drmCallback, null, mainHandler, eventLogger);
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return ((ExoApplication) getApplication()).buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    /**
     * Returns a new HttpDataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new HttpDataSource factory.
     */
    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return ((ExoApplication) getApplication()).buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private MediaSource buildMPDMediaSource(Uri uri, InputStream mpdContent) {
        // Are you using FrameworkSampleSource or ExtractorSampleSource when you build your player?
        return new DashMediaSource(getManifest(uri, mpdContent), new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                mainHandler, eventLogger);
    }

    private MediaSource buildMPDMediaSource(Uri uri, String mpdContent) {
        // Are you using FrameworkSampleSource or ExtractorSampleSource when you build your player?
        return new DashMediaSource(getManifest(uri, mpdContent), new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                mainHandler, eventLogger);
    }

    private DashManifest getManifest(Uri uri, InputStream mpdContent) {
        DashManifestParser parser = new DashManifestParser();
        DashManifest result;
        try {
            result = parser.parse(uri, mpdContent);
        } catch (IOException e) {
            throw new IllegalStateException("Malformed mpd file:\n" + mpdContent, e);
        }
        return result;
    }

    private DashManifest getManifest(Uri uri, String mpdContent) {
        DashManifestParser parser = new DashManifestParser();
        DashManifest result;
        try {
            result = parser.parse(uri, Helpers.toStream(mpdContent));
        } catch (IOException e) {
            throw new IllegalStateException("Malformed mpd file:\n" + mpdContent, e);
        }
        return result;
    }

    protected void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    protected void updateResumePosition() {
        if (player == null) {
            return;
        }

        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition()) : C.TIME_UNSET;
    }

    protected void showToast(int messageId) {
        showToast(getString(messageId));
    }

    protected void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    // OnClickListener methods

    @Override
    public void onClick(View view) {
        if (view == retryButton) {
            initializePlayer();
        } else if (view.getParent() == debugRootView && view.getTag() != null) {
            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                trackSelectionHelper.showSelectionDialog(
                        this,
                        ((TextToggleButton) view).getText(),
                        trackSelector.getCurrentMappedTrackInfo(),
                        (int) view.getTag()
                );
            }
        }
    }

    // NOTE: dynamically create quality buttons
    private void updateButtonVisibilities() {
        debugRootView.removeAllViews();

        retryButton.setVisibility(needRetrySource ? View.VISIBLE : View.GONE);
        debugRootView.addView(retryButton);

        if (player == null) {
            return;
        }

        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }

        for (int i = 0; i < mappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                TextToggleButton button = new TextToggleButton(this);
                int label, id;
                switch (player.getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        id = R.id.exo_audio;
                        label = R.string.audio;
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        id = R.id.exo_video;
                        label = R.string.video;
                        break;
                    case C.TRACK_TYPE_TEXT:
                        id = R.id.exo_captions2;
                        label = R.string.text;
                        break;
                    default:
                        continue;
                }
                button.setId(id);
                button.setText(label);
                button.setTag(i);
                button.setOnClickListener(this);
                debugRootView.addView(button, debugRootView.getChildCount() - 1);
            }
        }

        addCustomButtonToQualitySection();
    }

    abstract void addCustomButtonToQualitySection();

    @Override
    @SuppressWarnings("ReferenceEquality")
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        updateButtonVisibilities();
        if (trackGroups != lastSeenTrackGroupArray) {
            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_VIDEO) == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                    showToast(R.string.error_unsupported_video);
                }
                if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO) == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                    showToast(R.string.error_unsupported_audio);
                }
            }
            lastSeenTrackGroupArray = trackGroups;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof DecoderInitializationException) {
                // Special case for decoder initialization failures.
                DecoderInitializationException decoderInitializationException = (DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = getString(R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                    } else {
                        errorString = getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = getString(R.string.error_instantiating_decoder, decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null && !getHidePlaybackErrors()) {
            showToast(errorString);
        }
        needRetrySource = true;
        if (isBehindLiveWindow(e)) {
            clearResumePosition();
            initializePlayer();
        } else {
            updateResumePosition();
            updateButtonVisibilities();
            showControls();
        }
    }

    public abstract boolean getHidePlaybackErrors();

    // User controls
    private void showControls() {
        // user must choose another track
    }

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private boolean errorWhileClickedOnPlayButton() {
        View pauseBtn = findViewById(R.id.exo_pause);
        return needRetrySource && pauseBtn.isFocused();
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (errorWhileClickedOnPlayButton()) {
            initializePlayer();
        }

        updateButtonVisibilities();
    }
}
