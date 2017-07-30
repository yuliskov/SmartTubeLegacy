package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser.YouTubeInfoParser;
import okhttp3.Response;

import java.util.List;

public class OpenExternalPlayerInterceptor2 extends RequestInterceptor {
    private final Context mContext;

    public OpenExternalPlayerInterceptor2(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        if (url.contains("get_video_info")) {
            return true;
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        pressBackButton();
        parseAndOpenExoPlayer(url);

        return null;
    }

    private void pressBackButton() {
        if (!(mContext instanceof AppCompatActivity))
            return;
        AppCompatActivity activity = (AppCompatActivity) mContext;
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

    private void parseAndOpenExoPlayer(String url) {
        Response response = doOkHttpRequest(url);
        SimpleYouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(response.body().byteStream());
        Uri video = dataParser.getUrlByTag(YouTubeInfoParser.VIDEO_1080P_AVC);
        Uri audio = dataParser.getUrlByTag(YouTubeInfoParser.AUDIO_128K_AAC);
        Sample sample = SampleHelpers.buildFromVideoAndAudio(video, audio);
        mContext.startActivity(sample.buildIntent(mContext));
    }
}
