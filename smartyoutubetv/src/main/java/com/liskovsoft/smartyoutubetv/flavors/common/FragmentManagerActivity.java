package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.liskovsoft.browser.fragments.FragmentManager;
import com.liskovsoft.browser.fragments.GenericFragment;

public abstract class FragmentManagerActivity extends FragmentActivity implements FragmentManager {
    private KeyEvent mEvent;
    private GenericFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideTitleBar();
    }

    private void hideTitleBar() {
        setTheme(com.liskovsoft.browser.R.style.SimpleUITheme);
    }

    protected void setActiveFragment(GenericFragment fragment, boolean pausePrevious) {
        //if (pausePrevious)
        //    stopFragment();

        mFragment = fragment;

        //if (pausePrevious)
        //    startFragment();
    }

    /**
     * imitate of resuming of new activity
     */
    private void startFragment() {
        if (mFragment == null || mFragment.getState() == GenericFragment.STATE_STARTED) {
            return;
        }

        // one event instead of onStart and then onResume
        mFragment.onStartFragment();
    }

    /**
     * imitate pausing of old unused activity
     */
    private void stopFragment() {
        if (mFragment == null || mFragment.getState() == GenericFragment.STATE_STOPPED) {
            return;
        }

        // one event instead of onPause and then onStop
        mFragment.onStopFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mFragment.onNewIntent(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mFragment.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mFragment.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mFragment.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        mFragment.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragment.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mFragment.dispatchKeyEvent(event) || super.dispatchKeyEvent(modifyEvent(event));
    }

    private KeyEvent modifyEvent(KeyEvent event) {
        return mEvent != null ? mEvent : event;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        return mFragment.dispatchGenericMotionEvent(ev) || super.dispatchGenericMotionEvent(ev);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mFragment.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public FrameLayout getRootLayout() {
        return (FrameLayout) ((Fragment) mFragment).getView();
    }

    @Override
    public void setDispatchEvent(KeyEvent event) {
        mEvent = event;
    }
}
