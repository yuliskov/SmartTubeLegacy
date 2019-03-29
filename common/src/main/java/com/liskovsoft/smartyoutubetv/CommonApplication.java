package com.liskovsoft.smartyoutubetv;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.facebook.stetho.Stetho;
import com.liskovsoft.smartyoutubetv.common.BuildConfig;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class CommonApplication extends Application {
    private static final String TAG = CommonApplication.class.getSimpleName();
    private static Bus sBus;
    private static boolean sFromBootstrap;

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

    public static Bus getBus() {
        if (sBus == null) {
            sBus = new Bus(ThreadEnforcer.ANY);
        }
        return sBus;
    }

    public static boolean getFromBootstrap() {
        return sFromBootstrap;
    }

    public static void setFromBootstrap(boolean fromBootstrap) {
        sFromBootstrap = fromBootstrap;
    }
}
