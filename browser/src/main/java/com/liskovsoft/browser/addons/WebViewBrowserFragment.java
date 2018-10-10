package com.liskovsoft.browser.addons;

import android.os.Bundle;
import com.liskovsoft.browser.BaseBrowserFragment;

public abstract class WebViewBrowserFragment extends BaseBrowserFragment {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //initController(icicle);
    }
}
