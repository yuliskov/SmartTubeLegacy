package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.liskovsoft.browser.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.PlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.TwoFragmentsManager;

public abstract class TwoFragmentsManagerActivity extends EventManagerActivity implements TwoFragmentsManager {
    private BrowserFragment mBrowserFragment;
    private PlayerFragment mPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBrowserFragment();
        initPlayerFragment();
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
        swapFragments((Fragment) mPlayerFragment, (Fragment) mBrowserFragment);
        setActiveFragment(mPlayerFragment);
        mPlayerFragment.openIntent(intent);
    }

    private void swapFragments(Fragment toBeShown, Fragment toBeHidden) {
        // switch to the second activity and pass intent to it
        if (toBeShown == null || toBeHidden == null)
            return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show(toBeShown);
        transaction.hide(toBeHidden);
        transaction.commit();
    }
}
