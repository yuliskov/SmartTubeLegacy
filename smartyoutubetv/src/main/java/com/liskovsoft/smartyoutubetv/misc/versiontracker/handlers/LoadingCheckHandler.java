package com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers;

import android.os.Handler;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.AppStateWatcherBase.StateHandler;

public class LoadingCheckHandler extends StateHandler {
    private final FragmentManagerActivity mFragmentManagerActivity;

    public LoadingCheckHandler(FragmentManagerActivity fragmentManagerActivity) {
        mFragmentManagerActivity = fragmentManagerActivity;
    }

    @Override
    public void onBoot() {
        new Handler(mFragmentManagerActivity.getMainLooper()).postDelayed(this::checkAppLoaded, 180_000);
    }

    private void checkAppLoaded() {
        if (!mFragmentManagerActivity.isAppLoaded()) {
            mFragmentManagerActivity.onAppLoaded();
        }
    }
}
