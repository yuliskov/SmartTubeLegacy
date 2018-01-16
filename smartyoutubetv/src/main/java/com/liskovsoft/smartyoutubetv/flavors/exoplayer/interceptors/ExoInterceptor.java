package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
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
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.liskovsoft.smartyoutubetv.misc.MyUrlEncodedQueryString;
import com.squareup.otto.Subscribe;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final Logger sLogger = LoggerFactory.getLogger(ExoInterceptor.class);
    private final DelayedCommandCallInterceptor mInterceptor;
    private final ActionsSender mActionSender;
    private InputStream mResponseStream30Fps;
    private InputStream mResponseStream60Fps;

    private final String CLOSE_SUGGESTIONS = "action_close_suggestions";
    private final GenericStringResultReceiver mReceiver;
    private Intent mCachedIntent;
    private String mCurrentUrl;

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
        Response response30Fps = Helpers.doOkHttpRequest(unlockRegularFormats(url));
        Response response60Fps = Helpers.doOkHttpRequest(unlock60FpsFormats(url));
        mResponseStream30Fps = response30Fps.body().byteStream();
        mResponseStream60Fps = response60Fps.body().byteStream();
    }

    private void parseAndOpenExoPlayer() {
        final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mResponseStream60Fps, mResponseStream30Fps);
        dataParser.parse(new OnMediaFoundCallback() {
            private GenericInfo mInfo;
            @Override
            public void onDashMPDFound(final InputStream mpdContent) {
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
        sLogger.info("About to start ExoPlayer activity for Regular item");
        final SmartYouTubeTVExoBase activity = (SmartYouTubeTVExoBase) mContext;
        fetchButtonStates(playerIntent, new Runnable(){
            @Override
            public void run() {
                activity.startActivityForResult(playerIntent, 1);
                setupResultListener(activity);
            }
        });
    }

    private void fetchButtonStates(Intent intent, Runnable onDone) {
        Runnable processor = new ActionsReceiver(mContext, intent, onDone);
        processor.run();
    }

    private void setupResultListener(SmartYouTubeTVExoBase activity) {
        activity.setOnActivityResultListener(new OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
