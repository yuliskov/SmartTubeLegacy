package com.liskovsoft.smartyoutubetv.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.PausePlayerCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class MuteAudioInterceptor extends RequestInterceptor {
    private final Context mContext;
    private long mPrevTime;

    public MuteAudioInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        throttledTryToPauseVideo();

        return null;
    }

    private void throttledTryToPauseVideo() {
        long currentTime = System.currentTimeMillis();
        long timeDelta = currentTime - mPrevTime;
        mPrevTime = currentTime;
        if (timeDelta > 1000) {
            tryToPauseVideo();
        }
    }

    private void tryToPauseVideo() {
        new PausePlayerCommand().call();
    }
}
