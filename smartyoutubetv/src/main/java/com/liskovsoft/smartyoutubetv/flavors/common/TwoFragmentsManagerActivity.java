package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.fragments.PlayerFragment;
import com.liskovsoft.smartyoutubetv.fragments.TwoFragmentManager;
import com.liskovsoft.smartyoutubetv.fragments.PlayerListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

public abstract class TwoFragmentsManagerActivity extends FragmentManagerActivity implements TwoFragmentManager {
    private static final String TAG = TwoFragmentsManagerActivity.class.getSimpleName();
    private BrowserFragment mBrowserFragment;
    private PlayerFragment mPlayerFragment;
    private PlayerListener mPlayerListener;
    private boolean mTransparencyDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo);
        getLoadingManager().show();

        // all fragments should be initialized on start
        // or you will get different kinds of errors
        // because this process takes some time
        initBrowserFragment();
        initPlayerFragment();
        Log.d(TAG, "creating fragments...");
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

        if (pausePrevious) {
            Log.d(TAG, "Remove fragment " + removeCandidate.getClass().getSimpleName());
            removeFromContainer(removeCandidate);
        }

        Log.d(TAG, "Add fragment " + fragment.getClass().getSimpleName());
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
        if (!isInitialized(fragment)) {
            return;
        }

        ViewGroup container = findViewById(R.id.exo_container);
        View child = fragment.getWrapper();

        if (!containsChild(container, child)) {
            container.addView(child);
            return;
        }

        // ensure that view is placed above others

        int idx = container.indexOfChild(child);
        int count = container.getChildCount();
        if ((idx + 1) != count) { // should be the top view
            container.removeView(child);
            container.addView(child);
        }
    }

    private static boolean containsChild(ViewGroup container, View view) {
        return container.indexOfChild(view) != -1;
    }

    private boolean isInitialized(GenericFragment fragment) {
        return ((Fragment) fragment).getView() != null;
    }

    @Override
    public void openExoPlayer(final Intent intent, final boolean pausePrevious) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "opening player for intent=" + intent);
                setActiveFragment(mPlayerFragment, pausePrevious);
                mPlayerFragment.openVideo(intent);
            }
        });
    }

    @Override
    public void pausePrevious() {
        super.pausePrevious();
    }

    @Override
    public void setPlayerListener(PlayerListener listener) {
        mPlayerListener = listener;
    }

    @Override
    public void onPlayerAction(Intent action) {
        Log.d(TAG, "on receive player action: " + action);

        boolean doNotPause =
                action.getBooleanExtra(ExoPlayerFragment.BUTTON_USER_PAGE, false) ||
                action.getBooleanExtra(ExoPlayerFragment.BUTTON_SUGGESTIONS, false) ||
                action.getBooleanExtra(ExoPlayerFragment.BUTTON_FAVORITES, false);
        setActiveFragment(mBrowserFragment, !doNotPause);

        mPlayerListener.onPlayerAction(action);
    }

    private void initBrowserTransparency() {
        if (mTransparencyDone) {
            return;
        }

        findViewById(R.id.exo_container).setBackgroundColor(Color.BLACK);

        ViewGroup browser = (ViewGroup) mBrowserFragment.getWrapper();
        setTransparencyRecursive(browser);

        mTransparencyDone = true;
    }

    private void setTransparencyRecursive(ViewGroup container) {
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
                setTransparencyRecursive((ViewGroup) child);
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
    public void onBrowserLoaded() {
        Log.d(TAG, "Browser content started to load");
        initBrowserTransparency();
    }
}
