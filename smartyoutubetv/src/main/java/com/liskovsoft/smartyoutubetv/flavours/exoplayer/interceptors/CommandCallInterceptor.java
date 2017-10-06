package com.liskovsoft.smartyoutubetv.flavours.exoplayer.interceptors;

import android.os.Handler;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavours.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class CommandCallInterceptor extends RequestInterceptor {
    private GenericCommand mCommand;
    private long mPrevTime;

    public CommandCallInterceptor() {
    }

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

    public void doDelayedCall() {
        new Handler().postDelayed(obtainCommand(), 3000);
    }

    private Runnable obtainCommand() {
        return new Runnable() {
            @Override
            public void run() {
                throttledDelayedCommandCall();
            }
        };
    }


    public void setCommand(GenericCommand command) {
        mCommand = command;
    }

    private void throttledDelayedCommandCall() {
        long timeDelta = System.currentTimeMillis() - mPrevTime;
        if (timeDelta > 3000) {
            throttledCommandCall();
        }
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
        if (mCommand != null)
            mCommand.call();
    }
}
