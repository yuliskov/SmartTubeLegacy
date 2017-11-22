package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.os.Handler;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class DelayedCommandCallInterceptor extends RequestInterceptor {
    private GenericCommand mCommand;
    private long mPrevTime;
    private boolean mInterceptReceived;

    public DelayedCommandCallInterceptor() {
        
    }

    public DelayedCommandCallInterceptor(GenericCommand command) {
        mCommand = command;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        mInterceptReceived = true;
        return null;
    }

    /**
     * Forces command to run is case when 'intercept' hasn't been called <br/>
     * Force call command without adding to the history (in case WebView).
     */
    public void forceRun(boolean doNotDelay) {
        if (mInterceptReceived || doNotDelay) {
            mInterceptReceived = false;
            throttledCommandCall();
        } else {
            postDelayed();
        }
    }

    private void postDelayed() {
        int delayMillis = 3000;
        new Handler().postDelayed(new Runnable() {
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
