package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import android.content.Intent;
import com.liskovsoft.leanbackassistant.channels.RunOnInstallReceiver;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class LeanbackHandler extends StateHandler {
    private final Activity mContext;

    public LeanbackHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onLoad() {
        if (Helpers.isAndroidTVLauncher(mContext)) {
            Intent intent = new Intent(mContext, RunOnInstallReceiver.class);
            mContext.sendBroadcast(intent);
        }
    }
}
