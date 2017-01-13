package com.liskovsoft.browser.util;

import android.os.Bundle;
import com.liskovsoft.browser.BrowserActivity;

public class WebViewBrowserActivity extends BrowserActivity {
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        initController(icicle);
    }
}
