package com.liskovsoft.smartyoutubetv;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.facebook.stetho.Stetho;
import com.liskovsoft.smartyoutubetv.common.BuildConfig;

public class ApplicationCommon extends Application {
    private static final String TAG = ApplicationCommon.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // A WebView debug bridge, enables access to Chrome Dev Tools
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

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
}
