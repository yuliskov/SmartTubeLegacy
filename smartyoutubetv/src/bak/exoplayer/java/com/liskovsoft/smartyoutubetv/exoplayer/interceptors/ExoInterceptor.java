package com.liskovsoft.smartyoutubetv.flavours.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.SmartYouTubeTVActivity;
import com.liskovsoft.smartyoutubetv.SmartYouTubeTVActivity.OnActivityResultListener;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.SampleHelpers;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.commands.PressBackCommand2;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.commands.PressNextCommand;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.commands.PressPrevCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.ITag;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.SimpleYouTubeInfoParser3;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.YouTubeInfoParser3;
import okhttp3.MediaType;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final Logger sLogger = LoggerFactory.getLogger(ExoInterceptor.class);
    private final CommandCallInterceptor mInterceptor;
    private InputStream mResponseStream;
    private MediaType mResponseType;
    private GenericCommand mLastCommand = new PressBackCommand2();

    public ExoInterceptor(Context context, CommandCallInterceptor interceptor) {
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
        mResponseStream = response.body().byteStream();
        mResponseType = response.body().contentType();
    }

    private void parseAndOpenExoPlayer() {
        final YouTubeInfoParser3 dataParser = new SimpleYouTubeInfoParser3(mResponseStream, ITag.AVC);
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
                mTitle = String.format("%s: %s", info.getAuthor(), info.getTitle());
                mTitle2 = String.format("View count: %s, Published: %s", info.getViewCount(), info.getPublishedDate());
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
        mInterceptor.doDelayedCall();
    }

    private void bindActions(final String action) {
        switch (action) {
            case PlayerActivity.ACTION_NEXT:
                mLastCommand = new PressNextCommand(new PressBackCommand2());
                break;
            case PlayerActivity.ACTION_PREV:
                mLastCommand = new PressPrevCommand(new PressBackCommand2());
                break;
            case PlayerActivity.ACTION_BACK:
                mLastCommand = new PressBackCommand2();
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
