package com.liskovsoft.smartyoutubetv.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.liskovsoft.browser.fragments.MyActivityInterface;
import com.liskovsoft.browser.fragments.MyFragmentInterface;

public abstract class SmartYouTubeTVManagerActivity extends AppCompatActivity implements MyActivityInterface {
    private KeyEvent mEvent;
    private Fragment mFragment;
    private MyFragmentInterface mInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideTitleBar();
        initFragments();
    }

    private void hideTitleBar() {
        setTheme(com.liskovsoft.browser.R.style.SimpleUITheme);
    }

    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mFragment = getFragment();
        mInterface = (MyFragmentInterface) mFragment;
        transaction.add(android.R.id.content, mFragment);
        transaction.commit();
    }

    protected abstract Fragment getFragment();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mInterface.onNewIntent(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mInterface.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mInterface.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mInterface.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        mInterface.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mInterface.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mInterface.dispatchKeyEvent(event) || super.dispatchKeyEvent(modifyEvent(event));
    }

    private KeyEvent modifyEvent(KeyEvent event) {
        return mEvent != null ? mEvent : event;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        return mInterface.dispatchGenericMotionEvent(ev) || super.dispatchGenericMotionEvent(ev);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mInterface.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public FrameLayout getRootLayout() {
        return (FrameLayout) mFragment.getView();
    }

    @Override
    public void setDispatchEvent(KeyEvent event) {
        mEvent = event;
    }
}
