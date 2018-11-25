package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;

public abstract class FragmentManagerActivity extends FragmentActivity implements FragmentManager {
    private static final String TAG = FragmentManagerActivity.class.getSimpleName();
    private KeyEvent mEvent;
    private GenericFragment mActiveFragment;
    private GenericFragment mPrevFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // fixing bug where player's menu shown on boot and browser forgot user agent
            Log.d(TAG, "State not null... clearing");
            savedInstanceState.clear();
        }

        super.onCreate(savedInstanceState);

        hideTitleBar();
    }

    private void hideTitleBar() {
        setTheme(com.liskovsoft.browser.R.style.SimpleUITheme);
    }

    protected void setActiveFragment(GenericFragment fragment, boolean pausePrevious) {
        if (fragment == null)
            throw new IllegalStateException("Active fragment can't be null");

        if (mActiveFragment == fragment)
            return;

        if (pausePrevious) {
            pauseFragment(mActiveFragment);
            mPrevFragment = null;
        } else {
            // same fragment to be paused later
            mPrevFragment = mActiveFragment;
        }

        mActiveFragment = fragment;

        resumeFragment(mActiveFragment);
    }

    protected void pausePrevious() {
        if (mPrevFragment == null)
            return;

        pauseFragment(mPrevFragment);
        mPrevFragment = null;
    }

    private void resumeFragment(GenericFragment fragment) {
        if (fragment == null) {
            return;
        }

        if (fragment.getState() == GenericFragment.STATE_RESUMED) {
            Log.d(TAG, "Fragment already resumed: " + fragment.getClass().getSimpleName());
            return;
        }

        Log.d(TAG, "Resuming fragment: " + fragment.getClass().getSimpleName());

        // one event instead of onStart and then onResume
        fragment.onResumeFragment();
    }

    private void pauseFragment(GenericFragment fragment) {
        if (fragment == null) {
            return;
        }

        if (fragment.getState() == GenericFragment.STATE_PAUSED) {
            Log.d(TAG, "Fragment already paused: " + fragment.getClass().getSimpleName());
            return;
        }

        Log.d(TAG, "Pausing fragment: " + fragment.getClass().getSimpleName());

        // one event instead of onPause and then onStop
        fragment.onPauseFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mActiveFragment.onNewIntent(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mActiveFragment.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mActiveFragment.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mActiveFragment.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        mActiveFragment.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActiveFragment.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mActiveFragment.dispatchKeyEvent(event) || super.dispatchKeyEvent(modifyEvent(event));
    }

    private KeyEvent modifyEvent(KeyEvent event) {
        return mEvent != null ? mEvent : event;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        return mActiveFragment.dispatchGenericMotionEvent(ev) || super.dispatchGenericMotionEvent(ev);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mActiveFragment.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void setDispatchEvent(KeyEvent event) {
        mEvent = event;
    }
}
