package com.liskovsoft.smartyoutubetv.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase;

public class DeviceWakeReceiver extends BroadcastReceiver {
    private static final String TAG = DeviceWakeReceiver.class.getSimpleName();
    private final AppStateWatcherBase mAppStateWatcher;

    public DeviceWakeReceiver(AppStateWatcherBase appStateWatcher) {
        mAppStateWatcher = appStateWatcher;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "On Wake!");

        if (mAppStateWatcher != null) {
            mAppStateWatcher.onWake();
        }
    }
}
