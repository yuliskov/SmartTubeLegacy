package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.os.Handler;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class MyCommandCallInterceptor extends RequestInterceptor {
    private GenericCommand mCommand;
    private long mPrevTime;
    private boolean mRunOnce;
    private final int mTimeMillis = 10000;

    public MyCommandCallInterceptor() {
    }

    public MyCommandCallInterceptor(GenericCommand command) {
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

    /**
     * Forces command to run is case when 'intercept' hasn't been called
     */
    public void forceIntercept() {
        mRunOnce = false; // reset calls
        new Handler().postDelayed(obtainCommandWrapper(), mTimeMillis);
    }

    private Runnable obtainCommandWrapper() {
        return new Runnable() {
            @Override
            public void run() {
                if (mRunOnce)
                    return;
                throttledCommandCall();
            }
        };
    }


    public void setCommand(GenericCommand command) {
        mCommand = command;
    }

    protected void throttledCommandCall() {
        mRunOnce = true;
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
