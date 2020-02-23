package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
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
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.BuildConfig;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.ExtendedDataHolder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.PlayerInterface;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.subalignment.MyDefaultRenderersFactory;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.TextToggleButton;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.UUID;

public abstract class PlayerCoreFragment extends Fragment implements OnClickListener, Player.EventListener, PlayerControlView.VisibilityListener, PlayerInterface {
    private static final String TAG = PlayerCoreFragment.class.getName();
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
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
    private static final String COMBINED_URL_DELIMITER = "------";

    public static final int RENDERER_INDEX_VIDEO = 0;
    public static final int RENDERER_INDEX_AUDIO = 1;
    public static final int RENDERER_INDEX_SUBTITLE = 2;
    private static final int UI_SHOW_TIMEOUT_LONG_MS = 5_000;
    private static final int UI_SHOW_TIMEOUT_SHORT_MS = 2_000;

    protected MyEventLogger mEventLogger;

    protected SimpleExoPlayer mPlayer;
    protected DefaultTrackSelector mTrackSelector;

    protected PlayerView mSimpleExoPlayerView;
    protected LinearLayout mDebugRootView;
    protected View mLoadingView;
    protected FrameLayout mDebugViewGroup;
    private TextToggleButton mRetryButton;

    private DataSource.Factory mMediaDataSourceFactory;

    protected int mRetryCount;
    protected boolean mNeedRetrySource;
    protected boolean mShouldAutoPlay;
    protected LinearLayout mPlayerTopBar;

    private int mResumeWindow;
    private long mResumePosition;

    private Handler mMainHandler;
    private TrackGroupArray mLastSeenTrackGroupArray;

    protected TrackSelectionHelper mTrackSelectionHelper;
    protected long mExtractStartMS;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    }

    private Intent mIntent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SmartPreferences prefs = SmartPreferences.instance(getActivity());

        int layout = R.layout.player_activity;

        if (prefs.getFixAspectRatio()) {
            layout = R.layout.player_activity_texture_view;
        }

        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // shouldAutoPlay = true;
        clearResumePosition();
        mMediaDataSourceFactory = buildDataSourceFactory(false);
        mMainHandler = new Handler();

        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        // setContentView(R.layout.player_activity);
        View root = getView();
        if (root == null)
            throw new IllegalStateException("Fragment's root view is null");
        View rootView = root.findViewById(R.id.root);
        rootView.setOnClickListener(this);
        mDebugRootView = root.findViewById(R.id.controls_root);
        mLoadingView = root.findViewById(R.id.loading_view);
        mDebugViewGroup = root.findViewById(R.id.debug_view_group);
        mPlayerTopBar = root.findViewById(R.id.player_top_bar);
        mRetryButton = root.findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(this);

        mSimpleExoPlayerView = root.findViewById(R.id.player_view);
        mSimpleExoPlayerView.setControllerVisibilityListener(this);
        // hide ui player by default
        mSimpleExoPlayerView.setControllerAutoShow(false);
        boolean decreaseTimeout = CommonApplication.getPreferences().getDecreasePlayerUITimeout();
        mSimpleExoPlayerView.setControllerShowTimeoutMs(decreaseTimeout ? UI_SHOW_TIMEOUT_SHORT_MS : UI_SHOW_TIMEOUT_LONG_MS);

        mPlayerTopBar.setVisibility(View.GONE);

        configureSubtitleView();
    }

    private void configureSubtitleView() {
        if (mSimpleExoPlayerView != null) {
            SubtitleView subtitleView = mSimpleExoPlayerView.getSubtitleView();

            if (subtitleView != null) {
                // disable default style
                subtitleView.setApplyEmbeddedStyles(false);

                int defaultSubtitleColor = Color.argb(255, 218, 218, 218);
                int outlineColor = Color.argb(255, 43, 43, 43);
                CaptionStyleCompat style =
                        new CaptionStyleCompat(defaultSubtitleColor,
                                Color.TRANSPARENT, Color.TRANSPARENT,
                                CaptionStyleCompat.EDGE_TYPE_OUTLINE,
                                outlineColor, Typeface.DEFAULT);
                subtitleView.setStyle(style);
            }
        }
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void initializePlayer() {
        Intent intent = getIntent();
        boolean needNewPlayer = mPlayer == null;

        if (needNewPlayer) {
            initializeTrackSelector();

            mLastSeenTrackGroupArray = null;

            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), getRenderersFactory(), mTrackSelector, getLoadControl(), getDrmManager(intent), BANDWIDTH_METER);

            //Log.d(TAG, "High Bit Depth supported ? " + VpxLibrary.isHighBitDepthSupported());

            mPlayer.addListener(this);
            mPlayer.addListener(mEventLogger);
            mPlayer.setAudioDebugListener(mEventLogger);
            mPlayer.setVideoDebugListener(mEventLogger);
            mPlayer.setMetadataOutput(mEventLogger);

            if (BuildConfig.DEBUG) {
                mPlayer.addAnalyticsListener(new EventLogger(mTrackSelector));
            }

            mSimpleExoPlayerView.setPlayer(mPlayer);
            mPlayer.setPlayWhenReady(false); // give a chance to switch/restore track before play
        }

        if (needNewPlayer || mNeedRetrySource) {
            MediaSource mediaSource = extractMediaSource(intent);

            if (mediaSource == null) {
                return;
            }

            boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

            if (haveResumePosition) {
                mPlayer.seekTo(mResumeWindow, mResumePosition);
            }
            
            mPlayer.prepare(mediaSource, !haveResumePosition, !haveResumePosition);

            mNeedRetrySource = false;

            updateButtonVisibilities();

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Video extract starting...");
                mExtractStartMS = System.currentTimeMillis();
            }
        }
    }

    private MediaSource extractMediaSource(Intent intent) {
        Uri[] uris = getUris(intent);
        String[] extensions = getExtensions(intent);

        if (uris == null) {
            return null;
        }

        MediaSource[] mediaSources = new MediaSource[uris.length];

        for (int i = 0; i < uris.length; i++) {
            // NOTE: supply audio and video tracks in one field
            boolean isCombinedUri = uris[i].toString().contains(COMBINED_URL_DELIMITER);
            boolean isSimpleMpdExtra = intent.hasExtra(MPD_CONTENT_EXTRA);
            boolean isExtendedMpdExtra = ExtendedDataHolder.getInstance().hasExtra(MPD_CONTENT_EXTRA);

            if (isSimpleMpdExtra) { // mpd content
                String mpdExtra = intent.getStringExtra(MPD_CONTENT_EXTRA);
                mediaSources[i] = buildMPDMediaSource(uris[i], mpdExtra);
            } else if (isExtendedMpdExtra) { // mpd content stored externally?
                String mpdExtra = (String) ExtendedDataHolder.getInstance().getExtra(MPD_CONTENT_EXTRA);
                mediaSources[i] = buildMPDMediaSource(uris[i], mpdExtra);
            } else if (isCombinedUri) { // video and audio in one url
                String[] split = uris[i].toString().split(COMBINED_URL_DELIMITER);
                mediaSources[i] = new MergingMediaSource(
                        buildMediaSource(Uri.parse(split[0]), null),
                        buildMediaSource(Uri.parse(split[1]), null)
                );
            } else { // url only
                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }
        }

        return mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources); // or playlist
    }

    private Uri[] getUris(Intent intent) {
        String action = intent.getAction();
        Uri[] uris = null;

        if (ACTION_VIEW.equals(action)) {
            uris = new Uri[]{intent.getData()};
        } else if (ACTION_VIEW_LIST.equals(action)) {
            String[] uriStrings = intent.getStringArrayExtra(URI_LIST_EXTRA);
            uris = new Uri[uriStrings.length];

            for (int i = 0; i < uriStrings.length; i++) {
                uris[i] = Uri.parse(uriStrings[i]);
            }
        } else {
            showToast(getString(R.string.unexpected_intent_action, action));
        }

        if (uris != null && Util.maybeRequestReadExternalStoragePermission(getActivity(), uris)) {
            // The player will be reinitialized if the permission is granted.
            return null;
        }

        return uris;
    }

    private String[] getExtensions(Intent intent) {
        String action = intent.getAction();
        String[] extensions = null;

        if (ACTION_VIEW.equals(action)) {
            extensions = new String[]{intent.getStringExtra(EXTENSION_EXTRA)};
        } else if (ACTION_VIEW_LIST.equals(action)) {
            String[] uriStrings = intent.getStringArrayExtra(URI_LIST_EXTRA);
            extensions = intent.getStringArrayExtra(EXTENSION_LIST_EXTRA);

            if (extensions == null) {
                extensions = new String[uriStrings.length];
            }
        } else {
            showToast(getString(R.string.unexpected_intent_action, action));
        }

        return extensions;
    }

    private RenderersFactory getRenderersFactory() {
        DefaultRenderersFactory factory = new MyDefaultRenderersFactory(getActivity());
        factory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON);
        //factory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);
        //factory.setMediaCodecSelector(new BlackListMediaCodecSelector());
        return factory;
    }

    /**
     * Increase player's min/max buffer size to 60 secs
     * @return load control
     */
    private DefaultLoadControl getLoadControl() {
        DefaultLoadControl.Builder baseBuilder = new DefaultLoadControl.Builder();

        if (CommonApplication.getPreferences().getPlayerBufferType().equals(SmartPreferences.PLAYER_BUFFER_TYPE_MEDIUM)) {
            int minBufferMs = DefaultLoadControl.DEFAULT_MIN_BUFFER_MS;
            int maxBufferMs = DefaultLoadControl.DEFAULT_MAX_BUFFER_MS * 2;
            int bufferForPlaybackMs = DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS;
            int bufferForPlaybackAfterRebufferMs = DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS;
            //baseBuilder.setAllocator(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE));
            baseBuilder.setBufferDurationsMs(minBufferMs, maxBufferMs, bufferForPlaybackMs, bufferForPlaybackAfterRebufferMs);
        }

        return baseBuilder.createDefaultLoadControl();
    }

    private DrmSessionManager<FrameworkMediaCrypto> getDrmManager(Intent intent) {
        if (intent == null) {
            return null;
        }

        UUID drmSchemeUuid = intent.hasExtra(DRM_SCHEME_UUID_EXTRA) ? UUID.fromString(intent.getStringExtra(DRM_SCHEME_UUID_EXTRA)) : null;

        DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;

        if (drmSchemeUuid != null) {
            String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
            String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
            try {
                drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl, keyRequestPropertiesArray);
            } catch (UnsupportedDrmException e) {
                int errorStringId = Util.SDK_INT < 18 ? R.string.error_drm_not_supported :
                        (e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
                showToast(errorStringId);
            }
        }

        return drmSessionManager;
    }

    protected abstract void initializeTrackSelector();

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri) : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                SsMediaSource ssSource =
                        new SsMediaSource.Factory(
                                new DefaultSsChunkSource.Factory(mMediaDataSourceFactory),
                                buildDataSourceFactory(false)
                        )
                        .createMediaSource(uri);
                ssSource.addEventListener(mMainHandler, mEventLogger);
                return ssSource;
            case C.TYPE_DASH:
                DashMediaSource dashSource =
                        new DashMediaSource.Factory(
                                new DefaultDashChunkSource.Factory(mMediaDataSourceFactory),
                                buildDataSourceFactory(false)
                        )
                        .createMediaSource(uri);
                dashSource.addEventListener(mMainHandler, mEventLogger);
                return dashSource;
            case C.TYPE_HLS:
                HlsMediaSource hlsSource = new HlsMediaSource.Factory(mMediaDataSourceFactory).createMediaSource(uri);
                hlsSource.addEventListener(mMainHandler, mEventLogger);
                return hlsSource;
            case C.TYPE_OTHER:
                ExtractorMediaSource extractorSource = new ExtractorMediaSource.Factory(mMediaDataSourceFactory)
                        .setExtractorsFactory(new DefaultExtractorsFactory())
                        .createMediaSource(uri);
                extractorSource.addEventListener(mMainHandler, mEventLogger);
                return extractorSource;
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

        DefaultDrmSessionManager<FrameworkMediaCrypto> manager =
                new DefaultDrmSessionManager<>(
                        uuid,
                        FrameworkMediaDrm.newInstance(uuid),
                        drmCallback,
                        null
                );

        manager.addListener(mMainHandler, mEventLogger);

        return manager;
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return PlayerUtil.buildDataSourceFactory(this.getContext(), useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    /**
     * Returns a new HttpDataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new HttpDataSource factory.
     */
    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return PlayerUtil.buildHttpDataSourceFactory(this.getContext(), useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private MediaSource buildMPDMediaSource(Uri uri, InputStream mpdContent) {
        // Are you using FrameworkSampleSource or ExtractorSampleSource when you build your player?
        DashMediaSource dashSource = new DashMediaSource.Factory(
                new DefaultDashChunkSource.Factory(mMediaDataSourceFactory),
                null
            )
            .createMediaSource(getManifest(uri, mpdContent));
        dashSource.addEventListener(mMainHandler, mEventLogger);
        return dashSource;
    }

    private MediaSource buildMPDMediaSource(Uri uri, String mpdContent) {
        // Are you using FrameworkSampleSource or ExtractorSampleSource when you build your player?
        DashMediaSource dashSource = new DashMediaSource.Factory(
                new DefaultDashChunkSource.Factory(mMediaDataSourceFactory),
                null
            )
            .createMediaSource(getManifest(uri, mpdContent));
        dashSource.addEventListener(mMainHandler, mEventLogger);
        return dashSource;
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
            result = parser.parse(uri, FileHelpers.toStream(mpdContent));
        } catch (IOException e) {
            throw new IllegalStateException("Malformed mpd file:\n" + mpdContent, e);
        }
        return result;
    }

    protected void clearResumePosition() {
        mResumeWindow = C.INDEX_UNSET;
        mResumePosition = C.TIME_UNSET;
    }

    protected void updateResumePosition() {
        if (mPlayer == null) {
            return;
        }

        mResumeWindow = mPlayer.getCurrentWindowIndex();
        mResumePosition = mPlayer.isCurrentWindowSeekable() ? Math.max(0, mPlayer.getCurrentPosition()) : C.TIME_UNSET;
    }

    protected void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        MessageHelpers.showMessageThrottled(getActivity(), message);
    }

    // OnClickListener methods

    @Override
    public void onClick(View view) {
        if (view == mRetryButton) {
            initializePlayer();
        } else if (view.getParent() == mDebugRootView && view.getTag() != null && mTrackSelector != null) {
            MappedTrackInfo mappedTrackInfo = mTrackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                mTrackSelectionHelper.showSelectionDialog(
                        (ExoPlayerFragment) this,
                        ((TextToggleButton) view).getText(),
                        mTrackSelector.getCurrentMappedTrackInfo(),
                        (int) view.getTag()
                );
            }
        }
    }

    // NOTE: dynamically create quality buttons
    private void updateButtonVisibilities() {
        if (mPlayer == null) {
            return;
        }

        MappedTrackInfo mappedTrackInfo = mTrackSelector.getCurrentMappedTrackInfo();

        if (mappedTrackInfo == null) {
            return;
        }

        mDebugRootView.removeAllViews();

        //mRetryButton.setVisibility(mNeedRetrySource ? View.VISIBLE : View.GONE);
        mDebugRootView.addView(mRetryButton);

        for (int i = 0; i < mappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0 && getActivity() != null) {
                TextToggleButton button = new TextToggleButton(getActivity());
                int label, id;
                switch (mPlayer.getRendererType(i)) {
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
                mDebugRootView.addView(button, mDebugRootView.getChildCount() - 1);
            }
        }

        addCustomButtonToQualitySection();
    }

    abstract void addCustomButtonToQualitySection();

    @Override
    @SuppressWarnings("ReferenceEquality")
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        Context context = getActivity();

        if (context != null) {
            new Handler(context.getMainLooper()).post(this::updateButtonVisibilities);
        }

        if (trackGroups != mLastSeenTrackGroupArray) {
            MappedTrackInfo mappedTrackInfo = mTrackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO) == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                    showToast(R.string.error_unsupported_video);
                }
                if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO) == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                    showToast(R.string.error_unsupported_audio);
                }
            }

            mLastSeenTrackGroupArray = trackGroups;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        boolean isCodecError = false;

        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof DecoderInitializationException) {
                // Special case for decoder initialization failures.
                DecoderInitializationException decoderInitializationException = (DecoderInitializationException) cause;

                errorString = decoderInitializationException.diagnosticInfo;

                // Ver. 2.9.6
                //if (decoderInitializationException.decoderName == null) {
                //    if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                //        errorString = getString(R.string.error_querying_decoders);
                //    } else if (decoderInitializationException.secureDecoderRequired) {
                //        errorString = getString(R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                //    } else {
                //        errorString = getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                //    }
                //} else {
                //    errorString = getString(R.string.error_instantiating_decoder, decoderInitializationException.decoderName);
                //}
            }

            isCodecError = true;
        } else if (e.type == ExoPlaybackException.TYPE_SOURCE) {
            // Response code: 403
            errorString = e.getSourceException().getLocalizedMessage();
        } else if (e.type == ExoPlaybackException.TYPE_UNEXPECTED) {
            errorString = e.getUnexpectedException().getLocalizedMessage();
        }

        if (errorString != null && !getHidePlaybackErrors()) {
            showToast(errorString);
        }

        mNeedRetrySource = true;

        if (isBehindLiveWindow(e)) {
            clearResumePosition();
        } else {
            updateResumePosition();
            // show retry button to the options
            //updateButtonVisibilities();
        }

        if (isCodecError) {
            restorePlayback();
        }
    }

    /**
     * Trying to restore the playback without user interaction
     */
    private void restorePlayback() {
        if (mRetryCount++ < 5) {
            new Handler(Looper.getMainLooper()).postDelayed(this::initializePlayer, 500);
        }
    }

    public abstract boolean getHidePlaybackErrors();

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

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        //updateButtonVisibilities();
    }

    @Override
    public SimpleExoPlayer getPlayer() {
        return mPlayer;
    }

    @Override
    public PlayerView getExoPlayerView() {
        return mSimpleExoPlayerView;
    }
}
