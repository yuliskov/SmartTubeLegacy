package com.liskovsoft.smartyoutubetv.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.SmartYouTubeTVActivity;
import com.liskovsoft.smartyoutubetv.SmartYouTubeTVActivity.OnActivityResultListener;
import com.liskovsoft.smartyoutubetv.exoplayer.PlayerActivity;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.PausePlayerCommand;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.PressBackCommand2;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.PressNextCommand;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.PressPrevCommand;
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
import java.util.concurrent.Callable;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final Logger sLogger = LoggerFactory.getLogger(ExoInterceptor.class);
    private InputStream mResponseStream;
    private MediaType mResponseType;
    private GenericCommand mLastCommand;

    public ExoInterceptor(Context context) {
        mContext = context;
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
            private String mTitle;
            @Override
            public void onVideoFound(final InputStream mpdContent) {
                Sample sample = SampleHelpers.buildFromMPDPlaylist(mpdContent, mTitle);
                openExoPlayer(sample);
            }
            @Override
            public void onLiveFound(final Uri hlsUrl) {
                Sample sample = SampleHelpers.buildFromUri(hlsUrl, mTitle);
                openExoPlayer(sample);
            }

            @Override
            public void onInfoFound(YouTubeGenericInfo info) {
                mTitle = String.format("%s: %s", info.getAuthor(), info.getTitle());
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
            }
        });
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
