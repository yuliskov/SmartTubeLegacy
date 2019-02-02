package com.liskovsoft.smartyoutubetv;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ApplicationCommon extends Application {
    private static final String TAG = ApplicationCommon.class.getSimpleName();

    /**
     * Use MultiDexApplication: crashlytics fix on Android 4.4<br/>
     * Don't extent this class, use initializer instead<br/>
     * More info: https://developer.android.com/studio/build/multidex#mdex-gradle
     *
     * @param base context
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setupGlobalExceptionHandler();
    }

    private void setupGlobalExceptionHandler() {
        final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                StringWriter out = new StringWriter();
                PrintWriter writer = new PrintWriter(out);
                throwable.printStackTrace(writer);
                Log.e(TAG, out);
                Log.flush();

                if (oldHandler != null) {
                    oldHandler.uncaughtException(thread, throwable); // Delegates to Android's error handling}
                } else {
                    System.exit(2); // Prevents the service/app from freezing}
                }
            }
        });
    }
}
