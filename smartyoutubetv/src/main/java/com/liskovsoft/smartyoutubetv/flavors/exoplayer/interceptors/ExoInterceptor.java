package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.widget.Toast;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase.OnActivityResultListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final String TAG = ExoInterceptor.class.getSimpleName();
    private static final Logger sLogger = LoggerFactory.getLogger(TAG);
    private final DelayedCommandCallInterceptor mInterceptor;
    private final ActionsSender mActionSender;
    private InputStream mResponseStreamSimple;
    private static final long NO_INTERACTION_TIMEOUT = 1_000;
    private static final String CLOSE_SUGGESTIONS = "action_close_suggestions";
    private static final String VIDEO_ID_PARAM = "video_id";
    private final GenericStringResultReceiver mReceiver; // don't delete, its system bus receiver
    private Intent mCachedIntent;
    private String mCurrentUrl;
    /**
     * fix playlist advance bug<br/>
     * create time window (1sec) where get_video_info isn't allowed<br/>
     * see {@link #intercept(String)} method
     */
    private long mExitTime;
    private long mPrevCallTime;
    private String mPrevVideoId;

    private class GenericStringResultReceiver {
        GenericStringResultReceiver() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEvent event) {
            String action = event.getResult();
            if (action.equals(CLOSE_SUGGESTIONS)) {
                new Handler(mContext.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCachedIntent != null) {
                            Toast.makeText(mContext, R.string.returning_to_the_video, Toast.LENGTH_LONG).show();
                            openExoPlayer(mCachedIntent);
                        }
                    }
                });
            }
        }
    }

    public ExoInterceptor(Context context, DelayedCommandCallInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        mActionSender = new ActionsSender(context, this);
        mReceiver = new GenericStringResultReceiver();
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video intercepted: " + url);

        // XWalk fix: same video intercepted twice (Why??)
        boolean videoClosedRecently = System.currentTimeMillis() - mExitTime < NO_INTERACTION_TIMEOUT;
        if (videoClosedRecently) {
            Log.d(TAG, "System.currentTimeMillis() - mExitTime < " + NO_INTERACTION_TIMEOUT);
            mPrevCallTime = System.currentTimeMillis();
            return null;
        }

        // throttle calls
        boolean highCallRate = System.currentTimeMillis() - mPrevCallTime < NO_INTERACTION_TIMEOUT;
        if (highCallRate) {
            Log.d(TAG, "System.currentTimeMillis() - mLastCall < " + NO_INTERACTION_TIMEOUT);
            mPrevCallTime = System.currentTimeMillis();
            return null;
        }

        // the same video could opened multiple times
        String videoId = MyUrlEncodedQueryString.parse(url).get(VIDEO_ID_PARAM);
        boolean sameVideo = videoId.equals(mPrevVideoId);
        if (sameVideo) {
            Log.d(TAG, "The same video encountered");
            mPrevCallTime = System.currentTimeMillis();
            return null;
        }

        mPrevVideoId = videoId;
        mPrevCallTime = System.currentTimeMillis();
        mCurrentUrl = url;

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
            @Override
            public void onDashMPDFound(final InputStream mpdContent) {
                Log.d(TAG, "Video manifest parsed");
                Sample sample = SampleHelpers.buildFromMPDPlaylist(mpdContent);
                Intent exoIntent = createExoIntent(sample, mInfo);
                openExoPlayer(exoIntent);
            }
            @Override
            public void onLiveUrlFound(final Uri hlsUrl) {
                Sample sample = SampleHelpers.buildFromUri(hlsUrl);
                Intent exoIntent = createExoIntent(sample, mInfo);
                openExoPlayer(exoIntent);
            }

            @Override
            public void onInfoFound(GenericInfo info) {
                mInfo = info;
            }
        });
    }

    private Intent createExoIntent(Sample sample, GenericInfo info) {
        final Intent playerIntent = sample.buildIntent(mContext);
        playerIntent.putExtra(PlayerActivity.VIDEO_TITLE, info.getTitle());
        playerIntent.putExtra(PlayerActivity.VIDEO_AUTHOR, info.getAuthor());
        playerIntent.putExtra(PlayerActivity.VIDEO_VIEW_COUNT, info.getViewCount());
        playerIntent.putExtra(PlayerActivity.VIDEO_ID, extractVideoId());
        mCachedIntent = playerIntent;
        return playerIntent;
    }

    private String extractVideoId() {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(mCurrentUrl);
        return query.get("video_id");
    }

    private void openExoPlayer(final Intent playerIntent) {
        String msg = "About to start ExoPlayer activity for Regular item";
        Log.d(TAG, msg);
        final SmartYouTubeTVExoBase activity = (SmartYouTubeTVExoBase) mContext;

        Runnable onDone = new Runnable() {
            @Override
            public void run() {
                // isOK == false means that app has been unloaded from memory while doing playback
                boolean isOK = setupResultListener(activity);
                if (isOK) {
                    activity.startActivityForResult(playerIntent, PlayerActivity.REQUEST_CODE);
                }
            }
        };

        fetchButtonStates(playerIntent, onDone);
    }

    private void fetchButtonStates(Intent intent, Runnable onDone) {
        final Runnable processor = new ActionsReceiver(mContext, intent, onDone);
        processor.run();
    }

    private boolean setupResultListener(SmartYouTubeTVExoBase activity) {
        return activity.setOnActivityResultListener(new OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                mExitTime = System.currentTimeMillis();
                mPrevVideoId = null;
                mActionSender.bindActions(data);
            }
        });
    }

    public void updateLastCommand(GenericCommand command) {
        mInterceptor.setCommand(command);
        // force call command without adding to the history (in case WebView)
        mInterceptor.forceRun(false);
    }

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     * @param url
     * @return
     */
    protected String unlockRegularFormats(String url) {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);

        query.set("c", "HTML5");

        return query.toString();
    }

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     * @param url
     * @return
     */
    protected String unlock60FpsFormats(String url) {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);

        query.set("el", "info"); // unlock dashmpd url
        query.set("ps", "default"); // unlock 60fps formats

        return query.toString();
    }

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     * @param url
     * @return
     */
    protected String unlock30FpsFormats(String url) {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);

        query.set("el", "info"); // unlock dashmpd url

        return query.toString();
    }
}
