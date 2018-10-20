package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.liskovsoft.browser.fragments.BrowserFragment;
import com.liskovsoft.browser.fragments.GenericFragment;
import com.liskovsoft.smartyoutubetv.R;
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
        setContentView(R.layout.activity_exo);

        initFragments();
        swapFragments(mBrowserFragment, mPlayerFragment);
    }

    protected abstract BrowserFragment getBrowserFragment();
    protected abstract PlayerFragment getPlayerFragment();

    private void initFragments() {
        mPlayerFragment = getPlayerFragment();
        mBrowserFragment = getBrowserFragment();
        mPlayerFragment.setWrapper(findViewById(R.id.player_wrapper));
        mBrowserFragment.setWrapper(findViewById(R.id.browser_wrapper));
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.player_wrapper, (Fragment) mPlayerFragment);
        transaction.add(R.id.browser_wrapper, (Fragment) mBrowserFragment);
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

    private void swapFragments(GenericFragment toBeShown, GenericFragment toBeHidden) {
        if (toBeShown == null || toBeHidden == null)
            return;

        // switch to the second activity and pass intent to it
        sendViewToBack(toBeHidden.getWrapper());

        setActiveFragment(toBeShown, true);
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

    private void sendViewToBack(View child) {
        ViewGroup parent = (ViewGroup) child.getParent();
        if (parent != null && parent.indexOfChild(child) != 0) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    @Override
    public void bringBrowserToFront() {
        Toast.makeText(this, "Making suggestions semitransparent...", Toast.LENGTH_LONG).show();
        mBrowserFragment.getWrapper().setAlpha(0.3f);
    }
}
