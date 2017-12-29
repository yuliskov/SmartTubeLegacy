package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer.EventListener;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener;
import com.google.android.exoplayer2.ui.TimeBar;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom.AutoFrameRateManager;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom.DebugViewGroupHelper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom.PlayerPresenter;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom.PlayerStateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.LayoutToggleButton;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.TextToggleButton;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.UUID;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class PlayerActivity extends Activity implements OnClickListener, Player.EventListener, PlaybackControlView.VisibilityListener {
    private static final String TAG = PlayerActivity.class.getName();
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES = "drm_key_request_properties";
    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";

    public static final String ACTION_VIEW = "com.google.android.exoplayer.demo.action.VIEW";
    public static final String EXTENSION_EXTRA = "extension";

    public static final String ACTION_VIEW_LIST = "com.google.android.exoplayer.demo.action.VIEW_LIST";
    public static final String URI_LIST_EXTRA = "uri_list";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final CookieManager DEFAULT_COOKIE_MANAGER;
    public static final String MPD_CONTENT_EXTRA = "mpd_content";
    public static final String BUTTON_USER_PAGE = "button_user_page";
    public static final String BUTTON_LIKE = "button_like";
    public static final String BUTTON_DISLIKE = "button_dislike";
    public static final String BUTTON_SUBSCRIBE = "button_subscribe";
    public static final String BUTTON_PREV = "button_prev";
    public static final String BUTTON_NEXT = "button_next";
    public static final String BUTTON_BACK = "button_back";
    public static final String BUTTON_SUGGESTIONS = "button_suggestions";
    public static final String DELIMITER = "------";
    public static final String VIDEO_DATE = "video_date";
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_AUTHOR = "video_author";
    public static final String VIDEO_VIEWS = "video_views";
    public static final String VIDEO_ID = "video_id";
    public static final String TRACK_ENDED = "track_ended";

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private Handler mainHandler;
    private EventLogger eventLogger;
    private SimpleExoPlayerView simpleExoPlayerView;
    private LinearLayout debugRootView;
    private FrameLayout debugViewGroup;
    private TextToggleButton retryButton;

    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    private DebugViewGroupHelper debugViewHelper;
    private boolean needRetrySource;
    private TrackGroupArray lastSeenTrackGroupArray;

    private boolean shouldAutoPlay;
    private int resumeWindow;
    private long resumePosition;
    private OnClickListener mPrevNextListener;
    private EventListener mPlaybackEndListener;
    private TextView mVideoTitle;
    private TextView mVideoTitle2;
    private LinearLayout mPlayerTopBar;
    private int mInterfaceVisibilityState;
    private PlayerPresenter mPresenter;
    private PlayerStateManager mStateManager;
    private AutoFrameRateManager mAutoFrameRateManager;

    // Activity lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // NOTE: completely disable open/close animation for activity
        overridePendingTransition(0, 0);

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
        mPlayerTopBar = (LinearLayout) findViewById(R.id.player_top_bar);
        retryButton = (TextToggleButton) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.setControllerVisibilityListener(this);
        simpleExoPlayerView.requestFocus();

        initPresenter();
        initExoPlayerButtons();
        initVideoTitle();
        makeActivityFullscreen();
        makeActivityHorizontal();
    }

    private void initPresenter() {
        mPresenter = new PlayerPresenter(this);
        // we need to call this method after mPresenter initialization
        mPresenter.syncButtonStates();
    }

    public void onCheckedChanged(@NonNull ToggleButtonBase compoundButton, boolean b) {
        if (mPresenter != null)
            mPresenter.onCheckedChanged(compoundButton, b);
    }

    private void makeActivityFullscreen() {
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        if (VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void makeActivityHorizontal() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void initVideoTitle() {
        mVideoTitle = (TextView)findViewById(R.id.video_title);
        mVideoTitle.setText(getMainTitle());
        mVideoTitle2 = (TextView)findViewById(R.id.video_title2);
        mVideoTitle2.setText(getSecondTitle());
    }

    public String getMainTitle() {
        return getIntent().getStringExtra(PlayerActivity.VIDEO_TITLE);
    }

    private String formatViews(String num) {
        if (num == null) {
            return null;
        }

        long no = Long.parseLong(num);
        String str = String.format("%,d", no);
        return str;
    }

    public String getSecondTitle() {
        Intent intent = getIntent();

        String secondTitle = String.format(
                "%s      %s      %s %s",
                intent.getStringExtra(VIDEO_AUTHOR),
                intent.getStringExtra(VIDEO_DATE),
                formatViews(intent.getStringExtra(VIDEO_VIEWS)),
                getString(R.string.view_count)
        );

        return secondTitle;
    }

    private void initExoPlayerButtons() {
        initNextButton();
        initPrevButton();
        initTimeBar();
        initStatsButton();
    }

    private void initStatsButton() {
        ToggleButtonBase statsButton = (ToggleButtonBase)findViewById(R.id.exo_stats);
        statsButton.setOnCheckedChangeListener(new ToggleButtonBase.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
                if (isChecked)
                {
                    debugViewGroup.setVisibility(View.VISIBLE);
                } else {
                    debugViewGroup.setVisibility(View.GONE);
                }

            }
        });

    }

    // NOTE: example of visibility change listener
    private void initNextButton() {
        final View nextButton = simpleExoPlayerView.findViewById(R.id.exo_next);
        nextButton.getViewTreeObserver().addOnGlobalLayoutListener(obtainSetButtonEnabledListener(nextButton));
        // nextButton.setOnClickListener(obtainNextListener(nextButton));
    }

    private void initPrevButton() {
        final View prevButton = simpleExoPlayerView.findViewById(R.id.exo_prev);
        // prevButton.setOnClickListener(obtainPrevListener(prevButton));
    }

    private void initTimeBar() {
        final int timeIncrementMS = 15000;

        // time bar: rewind and fast forward to 15 secs
        TimeBar timeBar = (TimeBar) simpleExoPlayerView.findViewById(R.id.exo_progress);
        timeBar.setKeyTimeIncrement(timeIncrementMS);

        // Playback control view.
        simpleExoPlayerView.setRewindIncrementMs(timeIncrementMS);
        simpleExoPlayerView.setFastForwardIncrementMs(timeIncrementMS);
    }

    // TODO: improve this
    private OnGlobalLayoutListener obtainSetButtonEnabledListener(final View nextButton) {
        return new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setButtonEnabled(true, nextButton);
            }
        };
    }

    private OnClickListener obtainNextListener(final View nextButton) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                    doGracefulExit(PlayerActivity.BUTTON_NEXT);
            }
        };
    }

    private OnClickListener obtainPrevListener(final View prevButton) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                    doGracefulExit(PlayerActivity.BUTTON_PREV);
            }
        };
    }

    private void setButtonEnabled(boolean enabled, View view) {
        if (view == null) {
            return;
        }
        view.setEnabled(enabled);
        if (Util.SDK_INT >= 11) {
            setViewAlphaV11(view, enabled ? 1f : 0.3f);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @TargetApi(11)
    private void setViewAlphaV11(View view, float alpha) {
        view.setAlpha(alpha);
    }

    public void doGracefulExit() {
        Intent intent = mPresenter.createResultIntent();
        doGracefulExit(intent);
    }

    public void doGracefulExit(String action) {
        Intent intent = mPresenter.createResultIntent();
        intent.putExtra(action, true);
        doGracefulExit(intent);
    }

    private void doGracefulExit(Intent intent) {
        setResult(Activity.RESULT_OK, intent);

        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        doGracefulExit(PlayerActivity.BUTTON_BACK);

        // moveTaskToBack(true); // don't exit at this point
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        shouldAutoPlay = true;
        clearResumePosition();
        setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        if (mStateManager != null) {
            mStateManager.persistState();
            mStateManager = null; // force restore state
        }

        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    // Activity input

    // NOTE: entry point to handle keys
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isVolumeEvent(event))
            return false;
        
        if (isBackKey(event)) {
            return handleBack(event);
        }

        // Show the controls on any key event.
        simpleExoPlayerView.showController();

        // If the event was not handled then see if the player view can handle it as a media key event.
        return super.dispatchKeyEvent(event) || simpleExoPlayerView.dispatchMediaKeyEvent(event);
    }

    private boolean isBackKey(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_BACK;
    }

    private boolean handleBack(KeyEvent event) {
        boolean isUp = event.getAction() == KeyEvent.ACTION_UP;
        boolean isVisible = mInterfaceVisibilityState == View.VISIBLE;

        if (isVisible) {
            if (isUp) {
                simpleExoPlayerView.hideController();
            }
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    private boolean isVolumeEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP ||
            event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return true;
        }
        return false;
    }

    // OnClickListener methods

    @Override
    public void onClick(View view) {
        if (view == retryButton) {
            initializePlayer();
        } else if (view.getParent() == debugRootView) {
            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                trackSelectionHelper.showSelectionDialog(this, ((TextToggleButton) view).getText(), trackSelector.getCurrentMappedTrackInfo(), (int) view
                        .getTag());
            }
        }
    }

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void onVisibilityChange(int visibility) {
        mInterfaceVisibilityState = visibility;

        mPlayerTopBar.setVisibility(visibility);

        // NOTE: don't set to GONE or you will get fathom events
        if (visibility == View.VISIBLE)
            resetStateOfLayoutToggleButtons();
    }

    private void resetStateOfLayoutToggleButtons() {
        LayoutToggleButton toggleButton1 = (LayoutToggleButton) findViewById(R.id.player_options_btn);
        LayoutToggleButton toggleButton2 = (LayoutToggleButton) findViewById(R.id.player_quality_btn);
        toggleButton1.resetState();
        toggleButton2.resetState();
    }

    // Internal methods

    private void initializePlayer() {
        Intent intent = getIntent();
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            // TODO: modified: force all format support
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory) {
                @Override
                protected TrackSelection[] selectTracks(RendererCapabilities[] rendererCapabilities, TrackGroupArray[] rendererTrackGroupArrays,
                                                        int[][][] rendererFormatSupports) throws ExoPlaybackException {


                    if (mStateManager == null) { // run once
                        mStateManager = new PlayerStateManager(PlayerActivity.this, player, trackSelector);
                        mStateManager.restoreState(rendererTrackGroupArrays);
                    }

                    forceAllFormatsSupport(rendererFormatSupports);

                    return super.selectTracks(rendererCapabilities, rendererTrackGroupArrays, rendererFormatSupports);
                }

                private void forceAllFormatsSupport(int[][][] rendererFormatSupports) {
                    if (rendererFormatSupports == null) {
                        return;
                    }

                    for (int i = 0; i < rendererFormatSupports.length; i++) {
                        if (rendererFormatSupports[i] == null) {
                            continue;
                        }
                        for (int j = 0; j < rendererFormatSupports[i].length; j++) {
                            if (rendererFormatSupports[i][j] == null) {
                                continue;
                            }
                            for (int k = 0; k < rendererFormatSupports[i][j].length; k++) {
                                int supportLevel = rendererFormatSupports[i][j][k];
                                int notSupported = 6;
                                int formatSupported = 7;
                                if (supportLevel == notSupported) {
                                    rendererFormatSupports[i][j][k] = formatSupported;
                                }
                            }
                        }
                    }
                }
            };

            trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
            lastSeenTrackGroupArray = null;
            eventLogger = new EventLogger(trackSelector);

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

            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
            player.addListener(this);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
            player.setMetadataOutput(eventLogger);

            simpleExoPlayerView.setPlayer(player);
            player.setPlayWhenReady(shouldAutoPlay);
            debugViewHelper = new DebugViewGroupHelper(player, debugViewGroup, PlayerActivity.this);
            debugViewHelper.start();

            mAutoFrameRateManager = new AutoFrameRateManager(this, player);
        }
        if (needNewPlayer || needRetrySource) {
            String action = intent.getAction();
            Uri[] uris;
            String[] extensions;
            if (ACTION_VIEW.equals(action)) {
                uris = new Uri[]{intent.getData()};

                //TODO: modified
                extensions = new String[]{intent.getStringExtra(EXTENSION_EXTRA)};
                //extensions = new String[]{intent.getStringExtra(EXTENSION_EXTRA), "m3u8"};
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
                if (intent.getStringExtra(MPD_CONTENT_EXTRA) != null) {
                    mediaSources[i] = buildMPDMediaSource(uris[i], intent.getStringExtra(MPD_CONTENT_EXTRA));
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
    
    private MediaSource buildMPDMediaSource(Uri uri, String mpdContent) {
        // Are you using FrameworkSampleSource or ExtractorSampleSource when you build your player?
        return new DashMediaSource(getManifest(uri, mpdContent), new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                mainHandler, eventLogger);
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

    private void releasePlayer() {
        if (player != null) {
            debugViewHelper.stop();
            debugViewHelper = null;
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
            trackSelectionHelper = null;
            eventLogger = null;
        }
    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition()) : C.TIME_UNSET;
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
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

    // ExoPlayer.EventListener implementation

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_ENDED) {
            doGracefulExit(PlayerActivity.TRACK_ENDED);
        }
        if (playbackState == Player.STATE_READY) {
            getAutoFrameRateManager().apply();
        }
        updateButtonVisibilities();
    }

    public AutoFrameRateManager getAutoFrameRateManager() {
        return mAutoFrameRateManager;
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        // NOTE: moving to ExoPlayer 2.5.3
    }

    @Override
    public void onPositionDiscontinuity() {
        if (needRetrySource) {
            // This will only occur if the user has performed a seek whilst in the error state. Update the
            // resume position so that if the user then retries, playback will resume from the position to
            // which they seeked.
            updateResumePosition();
        }
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        // Do nothing.
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing.
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
        if (errorString != null) {
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

    // User controls

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
                int label;
                switch (player.getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        label = R.string.audio;
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        label = R.string.video;
                        break;
                    case C.TRACK_TYPE_TEXT:
                        label = R.string.text;
                        break;
                    default:
                        continue;
                }
                button.setText(label);
                button.setTag(i);
                button.setOnClickListener(this);
                debugRootView.addView(button, debugRootView.getChildCount() - 1);
            }
        }
    }

    private void showControls() {
        // user must choose another track
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

    void retryIfNeeded() {
        if (needRetrySource) {
            initializePlayer();
        }
    }
}
