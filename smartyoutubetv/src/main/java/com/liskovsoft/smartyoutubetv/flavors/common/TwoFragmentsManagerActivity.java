package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;
import com.liskovsoft.browser.fragments.BrowserFragment;
import com.liskovsoft.browser.fragments.GenericFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.PlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.TwoFragmentsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.PlayerListener;

public abstract class TwoFragmentsManagerActivity extends FragmentManagerActivity implements TwoFragmentsManager {
    private BrowserFragment mBrowserFragment;
    private PlayerFragment mPlayerFragment;
    private PlayerListener mPlayerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBrowserFragment();
        initPlayerFragment();
        swapFragments(mBrowserFragment, mPlayerFragment);
    }

    protected abstract BrowserFragment getBrowserFragment();
    protected abstract PlayerFragment getPlayerFragment();

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
    public void openExoPlayer(final Intent intent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swapFragments(mPlayerFragment, mBrowserFragment);
                mPlayerFragment.openVideo(intent);
            }
        });
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

        setActiveFragment((GenericFragment) toBeShown, true);
    }

    @Override
    public void setPlayerListener(PlayerListener listener) {
        mPlayerListener = listener;
    }

    @Override
    public void onPlayerClosed(Intent intent) {
        swapFragments(mBrowserFragment, mPlayerFragment);
        mPlayerListener.onPlayerClosed(intent);
    }

    private void showFragment(GenericFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show((Fragment) fragment);
        transaction.commit();
    }

    @Override
    public void bringBrowserToFront() {
        Toast.makeText(this, "Displaying suggestions above player window", Toast.LENGTH_LONG).show();
        //showFragment(mPlayerFragment);
        //View rootView = ((Fragment) mBrowserFragment).getView();
        //if (rootView == null) {
        //    return;
        //}
        //rootView.bringToFront();
        //rootView.setAlpha(0.5f);
    }
}
