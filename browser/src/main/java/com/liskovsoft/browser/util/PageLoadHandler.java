package com.liskovsoft.browser.util;

import com.liskovsoft.browser.Tab;

public interface PageLoadHandler {
    void onPageFinished(Tab tab);
    void onPageStarted(Tab tab);
}
