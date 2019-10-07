package com.liskovsoft.smartyoutubetv.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public interface GenericFragment {
    int STATE_RESUMED = 0;
    int STATE_PAUSED = 1;
    void onBackPressed();
    void showSoftKeyboard();
    void finish();
    boolean onKeyDown(int keyCode, KeyEvent event);
    boolean onKeyLongPress(int keyCode, KeyEvent event);
    boolean onKeyUp(int keyCode, KeyEvent event);
    void onNewIntent(Intent intent);

    boolean dispatchKeyEvent(KeyEvent event);
    boolean dispatchGenericMotionEvent(MotionEvent event);
    void onRestoreInstanceState(Bundle savedInstanceState);
    void onResumeFragment();
    void onPauseFragment();
    int getState();
    View getWrapper();
    void setWrapper(View wrapper);
    void onMemoryCritical();
    void onLoad();
}
