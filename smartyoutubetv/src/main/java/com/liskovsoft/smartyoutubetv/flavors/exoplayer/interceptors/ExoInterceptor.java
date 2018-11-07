package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.TwoFragmentsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.misc.MyUrlEncodedQueryString;
import com.squareup.otto.Subscribe;
import com.liskovsoft.smartyoutubetv.common.okhttp.OkHttpHelpers;
import okhttp3.Response;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor implements PlayerListener {
    private final Context mContext;
    private static final String TAG = ExoInterceptor.class.getSimpleName();
    private final DelayedCommandCallInterceptor mInterceptor;
    private final ActionsSender mActionSender;
    private final BackgroundActionManager mManager;
    private final TwoFragmentsManager mFragmentsManager;
    private InputStream mResponseStreamSimple;
    private static final String CLOSE_SUGGESTIONS = "action_close_suggestions";
    private final SuggestionsWatcher mReceiver; // don't delete, its system bus receiver
    private Intent mCachedIntent;
    private String mCurrentUrl;

    private class SuggestionsWatcher {
        SuggestionsWatcher() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEvent event) {
            String action = event.getResult();
            if (action.equals(CLOSE_SUGGESTIONS)) {
                returnToPlayer();
            }
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

        if (mManager.cancelAction(url)) {
            Log.d(TAG, "Video canceled: " + url);
            return null;
        }

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

    private void parseAndOpenExoPlayer() {
        final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mResponseStreamSimple);
        Log.d(TAG, "Video manifest received");
        dataParser.parse(new OnMediaFoundCallback() {
            private GenericInfo mInfo;
            private Sample mSample;
            @Override
            public void onDashMPDFound(final InputStream mpdContent) {
                mSample = SampleHelpers.buildFromMPDPlaylist(mpdContent);
            }
            @Override
            public void onLiveUrlFound(final Uri hlsUrl) {
                mSample = SampleHelpers.buildFromUri(hlsUrl);
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
        return query.get("video_id");
    }

    private void prepareAndOpenExoPlayer(final Intent playerIntent) {
        if (playerIntent == null) {
            Log.d(TAG, "Switching to the running player");
            mFragmentsManager.openExoPlayer(null); // player is already running
            return;
        }

        ActionsReceiver.Listener listener = new ActionsReceiver.Listener() {
            @Override
            public void onDone() {
                Log.d(TAG, "About to start ExoPlayer fragment");
                mFragmentsManager.openExoPlayer(playerIntent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Cancel start of ExoPlayer fragment");
                mManager.onCancel();
            }
        };

        Runnable processor = new ActionsReceiver(mContext, playerIntent, listener);
        processor.run();
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
