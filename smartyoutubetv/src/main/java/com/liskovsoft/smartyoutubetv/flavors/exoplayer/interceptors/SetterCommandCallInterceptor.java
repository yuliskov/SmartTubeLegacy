package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.os.Handler;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class SetterCommandCallInterceptor extends RequestInterceptor {
    private GenericCommand mCommand;
    private long mPrevTime;
    private final int mDelayMillis = 5000;
    private boolean mInterceptReceived;

    public SetterCommandCallInterceptor() {
    }

    public SetterCommandCallInterceptor(GenericCommand command) {
        mCommand = command;
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
     * Forces command to run is case when 'intercept' hasn't been called <br/>
     * Force call command without adding to the history (in case WebView).
     */
    public void forceRun() {
        startTimeBomb();
    }

    private void startTimeBomb() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterceptReceived) {
                    mInterceptReceived = false;
                    return;
                }
                throttledCommandCall();
            }
        }, mDelayMillis);
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
