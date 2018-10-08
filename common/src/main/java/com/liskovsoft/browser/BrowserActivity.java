package com.liskovsoft.browser;

import android.view.KeyEvent;
import android.widget.FrameLayout;

public interface BrowserActivity {
    FrameLayout getRootLayout();
    void setDispatchEvent(KeyEvent event);
}
