package com.liskovsoft.browser.addons;

import android.os.Bundle;
import com.liskovsoft.browser.BrowserFragment;

public abstract class WebViewBrowserFragment extends BrowserFragment {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //initController(icicle);
    }
}
