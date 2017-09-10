package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.interceptors.MuteVideoInterceptor;
import com.liskovsoft.smartyoutubetv.exoplayer.interceptors.PlayEndInterceptor;
import com.liskovsoft.smartyoutubetv.exoplayer.interceptors.CipherInterceptor;
import com.liskovsoft.smartyoutubetv.exoplayer.interceptors.ExoInterceptor;

public class OpenExternalPlayerInterceptor2 extends RequestInterceptor {
    private final Context mContext;
    private final ExoInterceptor mExoInterceptor;
    private final CipherInterceptor mCipherInterceptor;
    private final PlayEndInterceptor mPlayEndInterceptor;
    private final MuteVideoInterceptor mMuteVideoInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public OpenExternalPlayerInterceptor2(Context context) {
        mContext = context;
        mExoInterceptor = new ExoInterceptor(context);
        mCipherInterceptor = new CipherInterceptor(context);
        mPlayEndInterceptor = new PlayEndInterceptor(context);
        mMuteVideoInterceptor = new MuteVideoInterceptor(context);
    }

    @Override
    public boolean test(String url) {
        if (url.contains("get_video_info")) {
            mCurrentInterceptor = mExoInterceptor;
            mPlayEndInterceptor.reset();
            return true;
        }

        if (url.contains("tv-player.js")) {
            mCurrentInterceptor = mCipherInterceptor;
            return true;
        }

        if (url.contains("videoplayback")) {
            mCurrentInterceptor = mMuteVideoInterceptor;
            return true;
        }

        // useful places: ptracking
        // at this moment video should be added to history
        // attention: not working when activity restored
        if (url.contains("ptracking")) {
            mCurrentInterceptor = mPlayEndInterceptor;
            mPlayEndInterceptor.setCommand(mExoInterceptor.getLastCommand());
            return true;
        }

        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        return mCurrentInterceptor.intercept(url);
    }
}
