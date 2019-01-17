package com.liskovsoft.smartyoutubetv;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class ApplicationCommon extends Application {
    /**
     * Use MultiDexApplication: crashlytics fix on Android 4.4<br/>
     * Don't extent this class, use initializer instead<br/>
     * More info: https://developer.android.com/studio/build/multidex#mdex-gradle
     * @param base context
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
