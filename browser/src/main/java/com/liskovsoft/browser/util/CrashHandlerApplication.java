package com.liskovsoft.browser.util;

import android.content.Context;
import com.liskovsoft.browser.Browser;

import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandlerApplication extends Browser {
    private static Context mContext;
    private UncaughtExceptionHandler mHandler;

    @Override
    public void onCreate ()
    {
        super.onCreate();
        mContext = getApplicationContext();

        mHandler = new SimpleUncaughtExceptionHandler(mContext);

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler(mHandler);
    }

    public static Context getContext() {
        return mContext;
    }
}
