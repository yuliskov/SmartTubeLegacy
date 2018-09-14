package com.liskovsoft.smartyoutubetv.flavors.webview;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.core.SmartYouTubeTVActivityBase;

public class SmartYouTubeTVActivity extends SmartYouTubeTVActivityBase {
    public SmartYouTubeTVActivity() {
        // we must set engine type as early as possible
        Browser.setEngineType(EngineType.WebView);
    }
}