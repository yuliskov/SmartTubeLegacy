package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers;
import com.liskovsoft.smartyoutubetv.exoplayer.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.helpers.MyUrlEncodedQueryString;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.ITag;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.SimpleYouTubeInfoParser;
import okhttp3.Response;

public class OpenExternalPlayerInterceptor2 extends RequestInterceptor {
    private final Context mContext;
    private final RequestInterceptor mExoInterceptor;
    private final RequestInterceptor mCipherInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public OpenExternalPlayerInterceptor2(Context context) {
        mContext = context;
        mExoInterceptor = new ExoInterceptor(context);
        mCipherInterceptor = new CipherInterceptor(context);
    }

    @Override
    public boolean test(String url) {
        if (url.contains("get_video_info")) {
            mCurrentInterceptor = mExoInterceptor;
            return true;
        }

        if (url.contains("tv-player.js")) {
            mCurrentInterceptor = mCipherInterceptor;
            return true;
        }

        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        mCurrentInterceptor.intercept(url);

        return null;
    }
}
