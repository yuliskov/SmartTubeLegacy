package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVActivity.OnActivityResultListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressBackCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressNextCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressPrevCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeInfoParser;
import okhttp3.MediaType;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final Logger sLogger = LoggerFactory.getLogger(ExoInterceptor.class);
    private final SetterCommandCallInterceptor mInterceptor;
    private InputStream mResponseStream;
    private MediaType mResponseType;
    private GenericCommand mLastCommand = new PressBackCommand();

    public ExoInterceptor(Context context, SetterCommandCallInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
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
            public void onVideoFound(final InputStream mpdContent) {
                Sample sample = SampleHelpers.buildFromMPDPlaylist(mpdContent, mTitle, mTitle2);
                openExoPlayer(sample);
            }
            @Override
            public void onLiveFound(final Uri hlsUrl) {
                Sample sample = SampleHelpers.buildFromUri(hlsUrl, mTitle, mTitle2);
                openExoPlayer(sample);
            }

            @Override
            public void onInfoFound(YouTubeGenericInfo info) {
                mTitle = String.format("%s", info.getTitle());
                mTitle2 = String.format("%s      %s: %s      %s %s",
                        info.getAuthor(),
                        mContext.getString(R.string.published),
                        info.getPublishedDate(),
                        info.getViewCount(),
                        mContext.getString(R.string.view_count));
            }
        });
    }

    private void openExoPlayer(Sample sample) {
        sLogger.info("About to start ExoPlayer activity for Regular item");
        SmartYouTubeTVActivity activity = (SmartYouTubeTVActivity) mContext;
        activity.startActivityForResult(sample.buildIntent(mContext), 1);
        setupResultListener(activity);
    }

    private void setupResultListener(SmartYouTubeTVActivity activity) {
        activity.setOnActivityResultListener(new OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                bindActions(extractAction(data));
                updateLastCommand();
            }
        });
    }

    private void updateLastCommand() {
        mInterceptor.setCommand(getLastCommand());
        // force call command without adding to the history (in case WebView)
        mInterceptor.forceRun();
    }

    private void bindActions(final String action) {
        switch (action) {
            case PlayerActivity.ACTION_NEXT:
                mLastCommand = new PressNextCommand(new PressBackCommand());
                break;
            case PlayerActivity.ACTION_PREV:
                mLastCommand = new PressPrevCommand(new PressBackCommand());
                break;
            case PlayerActivity.ACTION_BACK:
                mLastCommand = new PressBackCommand();
                break;
        }
    }

    private String extractAction(Intent data) {
        return data.getStringExtra("action");
    }

    public GenericCommand getLastCommand() {
        return mLastCommand;
    }
}
