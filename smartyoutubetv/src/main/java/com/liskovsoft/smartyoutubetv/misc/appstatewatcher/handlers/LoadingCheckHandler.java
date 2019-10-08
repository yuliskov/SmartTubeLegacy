package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.os.Handler;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class LoadingCheckHandler extends StateHandler {
    private final FragmentManagerActivity mFragmentManagerActivity;
    private final Handler mHandler;
    private final Runnable mCallback;

    public LoadingCheckHandler(FragmentManagerActivity fragmentManagerActivity) {
        mFragmentManagerActivity = fragmentManagerActivity;
        mHandler = new Handler(mFragmentManagerActivity.getMainLooper());
        mCallback = this::forceAppLoaded;
    }

    @Override
    public void onInit() {
        mHandler.postDelayed(mCallback, 180_000);
    }

    @Override
    public void onLoad() {
        mHandler.removeCallbacks(mCallback);
    }

    private void forceAppLoaded() {
        mFragmentManagerActivity.onAppLoaded();
    }
}
