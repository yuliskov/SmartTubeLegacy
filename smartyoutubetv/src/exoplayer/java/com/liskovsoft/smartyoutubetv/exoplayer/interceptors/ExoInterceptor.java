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
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.SimpleYouTubeInfoParser2;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.UrlFoundCallback;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.YouTubeInfoParser2;
import okhttp3.Response;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;

    public ExoInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        pressBackButton();
        parseAndOpenExoPlayer(url);

        return null;
    }

    private void parseAndOpenExoPlayer(String url) {
        Response response = doOkHttpRequest(unlockAllFormats(url));
        final YouTubeInfoParser2 dataParser = new SimpleYouTubeInfoParser2(response.body().byteStream());
        dataParser.getMPDPlaylist(ITag.AVC, new MPDFoundCallback() {
            @Override
            public void onFound(final InputStream mpdPlaylist) {
                Sample sample = SampleHelpers.buildFromMPDPlaylist(mpdPlaylist);
                mContext.startActivity(sample.buildIntent(mContext));
            }
        });
    }

    private void pressBackButton() {
        if (!(mContext instanceof AppCompatActivity))
            return;
        AppCompatActivity activity = (AppCompatActivity) mContext;
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

    private void parseAndOpenExoPlayer2(String url) {
        Response response = doOkHttpRequest(unlockAllFormats(url));
        final YouTubeInfoParser2 dataParser = new SimpleYouTubeInfoParser2(response.body().byteStream());
        dataParser.getUrlByTag(ITag.VIDEO_2160P_AVC_HQ, new UrlFoundCallback() {
            @Override
            public void onUrlFound(final Uri videoUri) {
                dataParser.getUrlByTag(ITag.AUDIO_128K_AAC, new UrlFoundCallback() {
                    @Override
                    public void onUrlFound(final Uri audioUri) {
                        Sample sample = SampleHelpers.buildFromVideoAndAudio(videoUri, audioUri);
                        mContext.startActivity(sample.buildIntent(mContext));
                    }
                });
            }
        });

    }
}
