package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class DelayedCommandCallInterceptor extends RequestInterceptor {
    private GenericCommand mCommand;
    private long mPrevTime;
    private boolean mInterceptReceived;
    private boolean mForceRun;

    public DelayedCommandCallInterceptor(Context context) {
        super(context);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        mInterceptReceived = true;
        throttledCommandCall();
        return null;
    }

    /**
     * Forces command to remove is case when 'intercept' hasn't been called <br/>
     * Force call command without adding to the history (in case WebView).
     */
    public void forceRun(boolean doNotDelay) {
        mForceRun = true;
        if (mInterceptReceived || doNotDelay) {
            throttledCommandCall();
        } else {
            postDelayed();
        }
    }

    private void postDelayed() {
        int delayMillis = 3000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                throttledCommandCall();
            }
        }, delayMillis);
    }

    public void setCommand(GenericCommand command) {
        mCommand = command;
    }

    protected void throttledCommandCall() {
        if (!mForceRun)
            return;
        mInterceptReceived = false;
        mForceRun = false;

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
