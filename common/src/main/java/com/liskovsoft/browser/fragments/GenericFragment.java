package com.liskovsoft.browser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public interface GenericFragment {
    int STATE_STARTED = 0;
    int STATE_STOPPED = 1;
    void onBackPressed();
    void finish();
    boolean onKeyDown(int keyCode, KeyEvent event);
    boolean onKeyLongPress(int keyCode, KeyEvent event);
    boolean onKeyUp(int keyCode, KeyEvent event);
    void onNewIntent(Intent intent);

    boolean dispatchKeyEvent(KeyEvent event);
    boolean dispatchGenericMotionEvent(MotionEvent event);
    void onRestoreInstanceState(Bundle savedInstanceState);
    void onStartFragment();
    void onStopFragment();
    int getState();
}
