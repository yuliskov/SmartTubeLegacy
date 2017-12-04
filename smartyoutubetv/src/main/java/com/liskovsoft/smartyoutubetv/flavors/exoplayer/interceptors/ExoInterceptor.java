package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase.OnActivityResultListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.liskovsoft.smartyoutubetv.misc.MyUrlEncodedQueryString;
import okhttp3.MediaType;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final Logger sLogger = LoggerFactory.getLogger(ExoInterceptor.class);
    private final DelayedCommandCallInterceptor mInterceptor;
    private final ActionBinder mActionBinder;
    private InputStream mResponseStream;
    private MediaType mResponseType;

    public ExoInterceptor(Context context, DelayedCommandCallInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        mActionBinder = new ActionBinder(context, this);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        makeResponseStream(url);
        parseAndOpenExoPlayer();
        return null;
    }

    private void makeResponseStream(String url) {
        Response response = doOkHttpRequest(unlockAllFormats(url));
        //Response response = doOkHttpRequest(url);
        mResponseStream = response.body().byteStream();
        mResponseType = response.body().contentType();
    }

    private void parseAndOpenExoPlayer() {
        //String fmt = ITag.WEBM;
        //final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mResponseStream, fmt);
        final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mResponseStream);
        dataParser.setOnMediaFoundCallback(new OnMediaFoundCallback() {
            private String mTitle = "No title";
            private String mTitle2 = "No title";
            @Override
            public void onDashMPDFound(final InputStream mpdContent) {
                Sample sample = SampleHelpers.buildFromMPDPlaylist(mpdContent, mTitle, mTitle2);
                openExoPlayer(sample);
            }
            @Override
            public void onLiveUrlFound(final Uri hlsUrl) {
                Sample sample = SampleHelpers.buildFromUri(hlsUrl, mTitle, mTitle2);
                openExoPlayer(sample);
            }

            @Override
            public void onInfoFound(YouTubeGenericInfo info) {
                mTitle = String.format("%s", info.getTitle());
                mTitle2 = String.format("%s      %s: %s      %s %s",
                        info.getAuthor(),
                        mContext.getString(R.string.published),
                        Helpers.unixToLocalDate(mContext, info.getTimestamp()),
                        info.getViewCount(),
                        mContext.getString(R.string.view_count));
            }
        });
    }

    private void openExoPlayer(Sample sample) {
        sLogger.info("About to start ExoPlayer activity for Regular item");
        final SmartYouTubeTVExoBase activity = (SmartYouTubeTVExoBase) mContext;
        final Intent playerIntent = sample.buildIntent(mContext);
        fetchButtonStates(playerIntent, new Runnable(){
            @Override
            public void run() {
                activity.startActivityForResult(playerIntent, 1);
                setupResultListener(activity);
            }
        });
    }

    private void fetchButtonStates(Intent intent, Runnable onDone) {
        Runnable processor = new ButtonStatesProcessor(mContext, intent, onDone);
        processor.run();
    }

    private void setupResultListener(SmartYouTubeTVExoBase activity) {
        activity.setOnActivityResultListener(new OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                mActionBinder.bindActions(data);
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
    protected String unlockAllFormats(String url) {
        // youtube-dl > dashmpd:
        // {'video_id': 'q89s_1o6LLw', 'el': 'info', 'gl': 'US', 'eurl': '', 'disable_polymer': 'true', 'ps': 'default', 'sts': 17492, 'hl': 'en'}

        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);

        // query.set("c", "HTML5"); // unlock adaptive fmts
        query.set("el", "info"); // unlock dashmpd url
        // query.set("ps", "default"); // unlock 60fps formats
        // query.set("disable_polymer", "true");

        return query.toString();
    }
}
