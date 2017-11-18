package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class CommandCallInterceptor extends RequestInterceptor {
    private GenericCommand mCommand;
    private long mPrevTime;

    public CommandCallInterceptor(GenericCommand command) {
        mCommand = command;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        throttledCommandCall();
        return null;
    }

    protected void throttledCommandCall() {
        long currentTime = System.currentTimeMillis();
        long timeDelta = currentTime - mPrevTime;
        mPrevTime = currentTime;
        if (timeDelta > 1000) {
            actuallyCallCommand();
        }
    }

    protected void actuallyCallCommand() {
        if (mCommand != null) {
            mCommand.call();
        }
    }
}
