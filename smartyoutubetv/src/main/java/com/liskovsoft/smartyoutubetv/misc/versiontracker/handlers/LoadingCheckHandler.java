package com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers;

import android.os.Handler;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.AppVersionTrackerBase.StateHandler;

public class LoadingCheckHandler extends StateHandler {
    private final FragmentManagerActivity mFragmentManagerActivity;

    public LoadingCheckHandler(FragmentManagerActivity fragmentManagerActivity) {
        mFragmentManagerActivity = fragmentManagerActivity;
    }

    @Override
    public void onBoot() {
        new Handler().postDelayed(this::checkAppLoaded, 60_000);
    }

    private void checkAppLoaded() {
        if (!mFragmentManagerActivity.isAppLoaded()) {
            CommonApplication.getPreferences().setBootSucceeded(false);
            mFragmentManagerActivity.finish();
        }
    }
}
