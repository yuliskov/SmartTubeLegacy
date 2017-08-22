package com.liskovsoft.smartyoutubetv.exoplayer.interceptors;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.ITag;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.MPDFoundCallback;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.MediaFoundCallback;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.SimpleYouTubeInfoParser2;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.SimpleYouTubeInfoParser3;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.UrlFoundCallback;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.YouTubeInfoParser2;
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
        parseAndOpenExoPlayer3();
        return null;
    }

    private void makeResponseStream(String url) {
        Response response = doOkHttpRequest(unlockAllFormats(url));
        mResponseStream = response.body().byteStream();
        mResponseType = response.body().contentType();
    }

    private void pressBackButton() {
        if (!(mContext instanceof AppCompatActivity))
            return;
        AppCompatActivity activity = (AppCompatActivity) mContext;
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

    private void parseAndOpenExoPlayer3() {
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

    private void parseAndOpenExoPlayer() {
        final YouTubeInfoParser2 dataParser = new SimpleYouTubeInfoParser2(mResponseStream);
        dataParser.getMPDByCodec(ITag.AVC, new MPDFoundCallback() {
            @Override
            public void onFound(final InputStream mpdPlaylist) {
                Sample sample = SampleHelpers.buildFromMPDPlaylist(mpdPlaylist);
                sLogger.info("About to start ExoPlayer activity");
                mContext.startActivity(sample.buildIntent(mContext));
            }
        });
    }

    private void parseAndOpenExoPlayer2() {
        final YouTubeInfoParser2 dataParser = new SimpleYouTubeInfoParser2(mResponseStream);
        dataParser.getUrlByTag(ITag.VIDEO_2160P_AVC_HQ, new UrlFoundCallback() {
            @Override
            public void onUrlFound(final Uri videoUri) {
                sLogger.info("About to parse audio");
                dataParser.getUrlByTag(ITag.AUDIO_128K_AAC, new UrlFoundCallback() {
                    @Override
                    public void onUrlFound(final Uri audioUri) {
                        Sample sample = SampleHelpers.buildFromVideoAndAudio(videoUri, audioUri);
                        sLogger.info("About to start ExoPlayer activity");
                        mContext.startActivity(sample.buildIntent(mContext));
                    }
                });
            }
        });

    }
}
