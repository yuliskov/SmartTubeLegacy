package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import android.content.Intent;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class ATVChannelsHandler extends StateHandler {
    private static final String TAG = ATVChannelsHandler.class.getSimpleName();
    private static final String ATV_CHANNELS_CLASS = "com.liskovsoft.leanbackassistant.channels.RunOnInstallReceiver";
    private final Activity mContext;

    public ATVChannelsHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onLoad() {
        if (Helpers.isATVChannelsSupported(mContext)) {
            try {
                Class<?> aClass = Class.forName(ATV_CHANNELS_CLASS);
                Intent intent = new Intent(mContext, aClass);
                mContext.sendBroadcast(intent);
            } catch (ClassNotFoundException e) {
                Log.d(TAG, "ATV class not found: " + ATV_CHANNELS_CLASS);
                e.printStackTrace();
            }
        }
    }
}
