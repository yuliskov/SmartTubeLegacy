package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.MuteVideoCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final ExoInterceptor mExoInterceptor;
    private final CipherInterceptor mCipherInterceptor;
    private final MyCommandCallInterceptor mDoOnPlayEndInterceptor;
    private final MyCommandCallInterceptor mMuteVideoInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public OpenExternalPlayerInterceptor(Context context) {
        mContext = context;
        mDoOnPlayEndInterceptor = new MyCommandCallInterceptor();
        mExoInterceptor = new ExoInterceptor(context, mDoOnPlayEndInterceptor);
        mCipherInterceptor = new CipherInterceptor(context);
        mMuteVideoInterceptor = new MyCommandCallInterceptor(new MuteVideoCommand());
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

        if (url.contains("videoplayback")) {
            mCurrentInterceptor = mMuteVideoInterceptor;
            return true;
        }

        // useful places: ptracking
        // at this moment video should be added to history
        // attention: not working when WebView restored
        if (url.contains("ptracking")) {
            mCurrentInterceptor = mDoOnPlayEndInterceptor;
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
