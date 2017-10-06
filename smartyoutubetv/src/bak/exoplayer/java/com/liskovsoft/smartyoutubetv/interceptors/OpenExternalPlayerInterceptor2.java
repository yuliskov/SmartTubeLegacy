package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.commands.MuteVideoCommand;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.interceptors.CommandCallInterceptor;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.interceptors.CipherInterceptor;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.interceptors.ExoInterceptor;

public class OpenExternalPlayerInterceptor2 extends RequestInterceptor {
    private final Context mContext;
    private final ExoInterceptor mExoInterceptor;
    private final CipherInterceptor mCipherInterceptor;
    private final CommandCallInterceptor mPlayEndInterceptor;
    private final CommandCallInterceptor mMuteVideoInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public OpenExternalPlayerInterceptor2(Context context) {
        mContext = context;
        mPlayEndInterceptor = new CommandCallInterceptor();
        mExoInterceptor = new ExoInterceptor(context, mPlayEndInterceptor);
        mCipherInterceptor = new CipherInterceptor(context);
        mMuteVideoInterceptor = new CommandCallInterceptor(new MuteVideoCommand());
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
        // attention: not working when activity restored
        if (url.contains("ptracking")) {
            mCurrentInterceptor = mPlayEndInterceptor;
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
