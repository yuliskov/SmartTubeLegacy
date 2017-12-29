package com.liskovsoft.browser.helpers;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class DeviceUtils {
    public static void wakeUpDevice(final Context context) {
        PowerManager power = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        String pkgName = DeviceUtils.class.getPackage().toString();
        final WakeLock wakeLock = power.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.ON_AFTER_RELEASE |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                            pkgName);
        Log.d("browser", wakeLock.toString());
        wakeLock.acquire();
        wakeLock.release();
    }
}
