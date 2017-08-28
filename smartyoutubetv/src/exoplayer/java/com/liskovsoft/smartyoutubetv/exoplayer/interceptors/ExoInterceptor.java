package com.liskovsoft.smartyoutubetv.exoplayer.interceptors;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.ITag;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.MediaFoundCallback;
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
    private InputStream mResponseStream;
    private MediaType mResponseType;

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
        dataParser.getCombinedMedia(new MediaFoundCallback() {
            @Override
            public void onFound(final InputStream mpdContent) {
                Sample sample = SampleHelpers.buildFromMPDPlaylist(mpdContent);
                openExoPlayer(sample);
            }
            @Override
            public void onLiveFound(final Uri hlsUrl) {
                Sample sample = SampleHelpers.buildFromUri(hlsUrl);
                openExoPlayer(sample);
            }
        });
    }

    private void openExoPlayer(Sample sample) {
        sLogger.info("About to start ExoPlayer activity for Regular item");
        mContext.startActivity(sample.buildIntent(mContext));
    }
}
