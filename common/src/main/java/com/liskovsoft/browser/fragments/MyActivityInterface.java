package com.liskovsoft.browser.fragments;

import android.view.KeyEvent;
import android.widget.FrameLayout;

public interface MyActivityInterface {
    FrameLayout getRootLayout();
    void setDispatchEvent(KeyEvent event);
}
