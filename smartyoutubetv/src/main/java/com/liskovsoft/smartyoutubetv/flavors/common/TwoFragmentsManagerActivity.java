package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.PlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.TwoFragmentsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor.PlayerClosedEvent;

public abstract class TwoFragmentsManagerActivity extends FragmentManagerActivity implements TwoFragmentsManager {
    private BrowserFragment mBrowserFragment;
    private PlayerFragment mPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBrowserFragment();
        initPlayerFragment();
        swapFragments(mBrowserFragment, mPlayerFragment);
        setupEvents();
    }

    protected abstract BrowserFragment getBrowserFragment();
    protected abstract PlayerFragment getPlayerFragment();

    private void setupEvents() {
        setActiveFragment(mBrowserFragment);
    }

    private void initBrowserFragment() {
        mBrowserFragment = getBrowserFragment();
        initFragment((Fragment) mBrowserFragment);
    }

    private void initPlayerFragment() {
        mPlayerFragment = getPlayerFragment();
        initFragment((Fragment) mPlayerFragment);
    }

    private void initFragment(Fragment fragment) {
        if (fragment == null)
            return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    @Override
    public void openExoPlayer(Intent intent) {
        swapFragments(mPlayerFragment, mBrowserFragment);
        setActiveFragment(mPlayerFragment);
        mPlayerFragment.openVideo(intent);
    }

    private void swapFragments(Object toBeShown, Object toBeHidden) {
        // switch to the second activity and pass intent to it
        if (toBeShown == null || toBeHidden == null)
            return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show((Fragment) toBeShown);
        transaction.hide((Fragment) toBeHidden);
        transaction.commit(); // TODO: fix java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
    }

    @Override
    public void playerClosed(Intent intent) {
        Browser.getBus().post(new PlayerClosedEvent(intent));
    }
}
