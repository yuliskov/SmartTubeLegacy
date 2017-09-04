package com.liskovsoft.smartyoutubetv.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class PlayEndInterceptor extends RequestInterceptor {
    private final Context mContext;
    private GenericCommand mCommand;
    private int mCounter;

    public PlayEndInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (mCounter == 0 && mCommand != null) {
            mCommand.call();
        }

        mCounter++;

        return null;
    }

    public void reset() {
        mCounter = 0;
    }

    public void setCommand(GenericCommand command) {
        mCommand = command;
    }
}
