package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

public abstract class TwoFragmentsManagerActivity extends FragmentManagerActivity implements TwoFragmentsManager {
    private BrowserFragment mBrowserFragment;
    private PlayerFragment mPlayerFragment;
    private PlayerListener mPlayerListener;
    private boolean mTransparencyDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo);

        // all fragments should be initialized on start
        // or you will get different kinds of errors
        // because this process takes some time
        initBrowserFragment();
        initPlayerFragment();
        setActiveFragment(mBrowserFragment, true);
    }

    protected abstract BrowserFragment getBrowserFragment();
    protected abstract PlayerFragment getPlayerFragment();

    private void initBrowserFragment() {
        mBrowserFragment = getBrowserFragment();
        mBrowserFragment.setWrapper(findViewById(R.id.browser_wrapper));
        initFragment(mBrowserFragment);
    }

    private void initPlayerFragment() {
        mPlayerFragment = getPlayerFragment();
        mPlayerFragment.setWrapper(findViewById(R.id.player_wrapper));
        initFragment(mPlayerFragment);
    }

    private void initFragment(GenericFragment fragment) {
        if (fragment.getWrapper() == null) {
            throw new IllegalStateException("Fragment not initialized");
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment.getWrapper().getId(), (Fragment) fragment);
        transaction.commit();
    }

    @Override
    protected void setActiveFragment(GenericFragment fragment, boolean pausePrevious) {
        if (mBrowserFragment == null || mPlayerFragment == null) {
            return;
        }

        // if other fragment not initialized - skip remove
        // remove other fragment if exists
        // if container is empty add this

        GenericFragment removeCandidate = mBrowserFragment.equals(fragment) ? mPlayerFragment : mBrowserFragment;

        if (pausePrevious)
            removeFromContainer(removeCandidate);
        addToContainer(fragment);

        super.setActiveFragment(fragment, pausePrevious);
    }

    private void removeFromContainer(GenericFragment fragment) {
        if (!isInitialized(fragment)) {
            return;
        }

        ViewGroup container = findViewById(R.id.exo_container);
        View child = fragment.getWrapper();

        if (containsChild(container, child)) {
            container.removeView(child);
        }
    }

    private void addToContainer(GenericFragment fragment) {
        // can't do this here because initialization takes time
        //if (!isInitialized(fragment)) {
        //    initFragment(fragment);
        //}

        ViewGroup container = findViewById(R.id.exo_container);
        View child = fragment.getWrapper();

        if (!containsChild(container, child)) {
            container.addView(child);
        }
    }

    private static boolean containsChild(ViewGroup container, View view) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child.equals(view)) {
                return true;
            }
        }

        return false;
    }

    private boolean isInitialized(GenericFragment fragment) {
        return ((Fragment) fragment).getView() != null;
    }

    @Override
    public void openExoPlayer(final Intent intent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setActiveFragment(mPlayerFragment, true);
                mPlayerFragment.openVideo(intent);
            }
        });
    }

    @Override
    public void setPlayerListener(PlayerListener listener) {
        mPlayerListener = listener;
    }

    @Override
    public void onPlayerClosed(Intent intent) {
        boolean suggestionsClicked = intent.getBooleanExtra(ExoPlayerFragment.BUTTON_SUGGESTIONS, false);
        if (suggestionsClicked) {
            setActiveFragment(mBrowserFragment, false);
        } else {
            setActiveFragment(mBrowserFragment, true);
        }
        mPlayerListener.onPlayerClosed(intent);
    }

    private void initBrowserTransparency() {
        if (mTransparencyDone) {
            return;
        }

        findViewById(R.id.exo_container).setBackgroundColor(Color.BLACK);
        ViewGroup wrapper = (ViewGroup) mBrowserFragment.getWrapper();
        setBrowserTransparencyRecursive(wrapper);

        mTransparencyDone = true;
    }

    private void setBrowserTransparencyRecursive(ViewGroup container) {
        if (container == null) {
            return;
        }

        container.setBackgroundColor(Color.TRANSPARENT);
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            child.setBackgroundColor(Color.TRANSPARENT);
            // set hw acceleration off: API 11+ fix
            //child.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            if (child instanceof ViewGroup) {
                setBrowserTransparencyRecursive((ViewGroup) child);
            }
        }
    }

    private void sendViewToBack(View child) {
        ViewGroup parent = (ViewGroup) child.getParent();
        if (parent != null && parent.indexOfChild(child) != 0) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    @Override
    public void onLoadSuccess() {
        initBrowserTransparency();
    }
}
