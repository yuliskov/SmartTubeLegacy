package com.liskovsoft.smartyoutubetv.flavors.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class SmartYouTubeTVActivity extends AppCompatActivity {
    private SmartYouTubeTVFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragments();
    }

    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment = new SmartYouTubeTVFragment();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        fragment.onNewIntent(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return fragment.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return fragment.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return fragment.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        fragment.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return fragment.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        return fragment.dispatchGenericMotionEvent(ev) || super.dispatchGenericMotionEvent(ev);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragment.onRestoreInstanceState(savedInstanceState);
    }
}
