package com.liskovsoft.smartyoutubetv.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.liskovsoft.smartyoutubetv.misc.CacheManager;

public class ClearCacheReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new CacheManager(context).clearCache();
        System.exit(0);
    }
}
