package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ActionsReceiver.Listener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser;
import com.liskovsoft.smartyoutubetv.fragments.PlayerListener;
import com.liskovsoft.smartyoutubetv.fragments.TwoFragmentsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.squareup.otto.Subscribe;
import com.liskovsoft.smartyoutubetv.common.okhttp.OkHttpHelpers;
import okhttp3.Response;

import java.io.InputStream;
import java.util.List;

public class ExoInterceptor extends RequestInterceptor implements PlayerListener {
    private final Context mContext;
    private static final String TAG = ExoInterceptor.class.getSimpleName();
    private final DelayedCommandCallInterceptor mInterceptor;
    private final ActionsSender mActionSender;
    private final BackgroundActionManager mManager;
    private final TwoFragmentsManager mFragmentsManager;
    private InputStream mResponseStreamSimple;
    private static final String CLOSE_SUGGESTIONS = "action_close_suggestions";
    private static final String PLAYBACK_STARTED = "playback_started";
    private final SuggestionsWatcher mReceiver; // don't delete, its system bus receiver
    private Intent mCachedIntent;
    private String mCurrentUrl;
    public static final String VIDEO_DATA_URL = "get_video_info";
    private static final String VIDEO_ID_PARAM = "video_id";
    private boolean mPlaybackStarted;
    private Listener mPlayerListener;
    private static final long FORCE_PLAYBACK_TIMEOUT_MS = 10_000;

    private class SuggestionsWatcher {
        SuggestionsWatcher() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEvent event) {
            String action = event.getResult();
            if (action.equals(CLOSE_SUGGESTIONS)) {
                returnToPlayer();
            } else if (action.equals(PLAYBACK_STARTED)) {
                // playbackStarted();
            }
        }

        private void playbackStarted() {
            if (mPlaybackStarted || !mManager.isDone()) {
                return;
            }

            Log.d(TAG, "Playback is started");
            mPlaybackStarted = true;

            new Handler(mContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mFragmentsManager.pausePrevious();
                }
            });
        }

        private void returnToPlayer() {
            new Handler(mContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (mCachedIntent != null) {
                        prepareAndOpenExoPlayer(null); // player should already be running so pass null
                    }
                }
            });
        }
    }

    public ExoInterceptor(Context context, DelayedCommandCallInterceptor interceptor) {
        mContext = context;
        mFragmentsManager = (TwoFragmentsManager) context;
        mInterceptor = interceptor;
        mActionSender = new ActionsSender(context, this);
        mReceiver = new SuggestionsWatcher();
        mManager = new BackgroundActionManager();

        mFragmentsManager.setPlayerListener(this);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video intercepted: " + url);

        mCurrentUrl = url;

        if (mManager.cancelPlayback(url)) {
            Log.d(TAG, "Video canceled: " + url);
            //if (mManager.isDone()) // player is doing playback
            //    mReceiver.returnToPlayer();
            return null;
        }

        mPlaybackStarted = false;

        prepareResponseStream(url);
        parseAndOpenExoPlayer();
        return null;
    }

    // We also try looking in get_video_info since it may contain different dashmpd
    // URL that points to a DASH manifest with possibly different itag set (some itags
    // are missing from DASH manifest pointed by webpage's dashmpd, some - from DASH
    // manifest pointed by get_video_info's dashmpd).
    // The general idea is to take a union of itags of both DASH manifests (for example
    // video with such 'manifest behavior' see https://github.com/rg3/youtube-dl/issues/6093)
    private void prepareResponseStream(String url) {
        Response responseSimple = OkHttpHelpers.doOkHttpRequest(url);
        mResponseStreamSimple = responseSimple == null ? null : responseSimple.body().byteStream();
    }

    /**
     * For parsing details see {@link YouTubeMediaParser}
     */
    private void parseAndOpenExoPlayer() {
        final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mResponseStreamSimple);
        Log.d(TAG, "Video manifest received");
        dataParser.parse(new OnMediaFoundCallback() {
            private GenericInfo mInfo;
            private Sample mSample;

            @Override
            public void onDashUrlFound(Uri dashUrl) {
                mSample = SampleHelpers.buildFromMpdUri(dashUrl);
            }

            @Override
            public void onHLSFound(final Uri hlsUrl) {
                mSample = SampleHelpers.buildFromHlsUri(hlsUrl);
            }

            @Override
            public void onDashMPDFound(final InputStream mpdContent) {
                mSample = SampleHelpers.buildFromMPDPlaylist(mpdContent);
            }

            @Override
            public void onUrlListFound(final List<String> urlList) {
                mSample = SampleHelpers.buildFromList(urlList);
            }

            @Override
            public void onInfoFound(GenericInfo info) {
                mInfo = info;
            }

            @Override
            public void onDone() {
                if (mSample == null || mInfo == null) {
                    mManager.onCancel();
                    return;
                }

                Log.d(TAG, "Video info has been parsed... opening exoplayer...");
                Intent exoIntent = createExoIntent(mSample, mInfo);
                prepareAndOpenExoPlayer(exoIntent);
            }
        });
    }

    private Intent createExoIntent(Sample sample, GenericInfo info) {
        final Intent playerIntent = sample.buildIntent(mContext);
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_TITLE, info.getTitle());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_AUTHOR, info.getAuthor());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_VIEW_COUNT, info.getViewCount());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_ID, extractVideoId());
        mCachedIntent = playerIntent;
        return playerIntent;
    }

    private String extractVideoId() {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(mCurrentUrl);
        return query.get(VIDEO_ID_PARAM);
    }

    private void prepareAndOpenExoPlayer(final Intent playerIntent) {
        final boolean pauseBrowser = !mManager.isMirroring(mCurrentUrl);

        if (playerIntent == null) {
            Log.d(TAG, "Switching to the running player from suggestions");
            mFragmentsManager.openExoPlayer(null, pauseBrowser); // player is opened from suggestions
            return;
        }

        mPlayerListener = new ActionsReceiver.Listener() { // player is opened from from get_video_info url
            @Override
            public void onDone() {
                Log.d(TAG, "About to start ExoPlayer fragment: " + playerIntent.getExtras());
                mManager.onDone();
                // forcePlaybackCheck();
                // mFragmentsManager.openExoPlayer(playerIntent, false); // don't pause until playback is started
                mFragmentsManager.openExoPlayer(playerIntent, pauseBrowser); // don't pause until playback is started
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Cancel start of ExoPlayer fragment");
                mManager.onCancel();
            }
        };

        Runnable processor = new ActionsReceiver(mContext, playerIntent, mPlayerListener);
        processor.run();
    }

    /**
     * Force to open the video if playback not started within specified timeout
     */
    private void forcePlaybackCheck() {
        new Handler(mContext.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mReceiver.playbackStarted();
            }
        }, FORCE_PLAYBACK_TIMEOUT_MS);
    }

    public void onPlayerClosed(Intent intent) {
        boolean suggestionsClicked = intent.getBooleanExtra(ExoPlayerFragment.BUTTON_SUGGESTIONS, false);
        if (!suggestionsClicked)
            mManager.onClose();

        mActionSender.bindActions(intent);
    }

    public void updateLastCommand(GenericCommand command) {
        mInterceptor.setCommand(command);
        // force call command without adding to the history (in case WebView)
        mInterceptor.forceRun(true);
    }
}
