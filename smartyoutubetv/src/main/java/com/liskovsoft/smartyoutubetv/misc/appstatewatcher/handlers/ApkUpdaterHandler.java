package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.MainApkUpdater;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcher;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class ApkUpdaterHandler extends StateHandler {
    private final MainApkUpdater mApkUpdater;
    private final FragmentManagerActivity mContext;
    private final AppStateWatcher mAppStateWatcher;

    public ApkUpdaterHandler(FragmentManagerActivity context, AppStateWatcher appStateWatcher) {
        mApkUpdater = new MainApkUpdater(context);
        mContext = context;
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

    @Override
    public void onResume() {
        mAppStateWatcher.addRunAfterLock(this::checkUpdates);
    }

    private void checkUpdates() {
        if (!mContext.isSimplePlayerMode()) {
            mApkUpdater.start();
        }
    }
}
