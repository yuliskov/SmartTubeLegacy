package com.liskovsoft.smartyoutubetv.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;

public class DeviceWakeReceiver extends BroadcastReceiver {
    private static final String TAG = DeviceWakeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "On Wake!");
        CommonApplication.getPreferences().setDeviceWake(true);
    }
}
