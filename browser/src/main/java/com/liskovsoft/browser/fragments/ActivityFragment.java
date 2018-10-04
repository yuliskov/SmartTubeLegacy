package com.liskovsoft.browser.fragments;

import android.content.Intent;
import android.view.KeyEvent;

public interface ActivityFragment {
    void onBackPressed();
    void finish();
    boolean onKeyDown(int keyCode, KeyEvent event);
    boolean onKeyLongPress(int keyCode, KeyEvent event);
    boolean onKeyUp(int keyCode, KeyEvent event);
    void onNewIntent(Intent intent);
}
