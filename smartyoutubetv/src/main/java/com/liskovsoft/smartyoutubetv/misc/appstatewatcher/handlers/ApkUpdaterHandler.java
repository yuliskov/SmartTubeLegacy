package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.misc.MainApkUpdater;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcher;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class ApkUpdaterHandler extends StateHandler {
    private final MainApkUpdater mApkUpdater;
    private final AppStateWatcher mAppStateWatcher;

    public ApkUpdaterHandler(Context context, AppStateWatcher appStateWatcher) {
        mApkUpdater = new MainApkUpdater(context);
        mAppStateWatcher = appStateWatcher;
    }

    @Override
    public void onInit() {
        mAppStateWatcher.addRunAfterLock(this::checkUpdates);
    }

    @Override
    public void onWake() {
        mAppStateWatcher.addRunAfterLock(this::checkUpdates);
    }

    private void checkUpdates() {
        mApkUpdater.start();
    }
}
