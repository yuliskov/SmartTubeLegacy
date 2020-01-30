package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.misc.MainApkUpdater;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class ApkUpdaterHandler extends StateHandler {
    private final MainApkUpdater mApkUpdater;

    public ApkUpdaterHandler(Context context) {
        mApkUpdater = new MainApkUpdater(context);
    }

    @Override
    public void onInit() {
        mApkUpdater.start();
    }

    @Override
    public void onWake() {
        mApkUpdater.start();
    }
}
