package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;

public class SmartYouTubeTVExoWebView extends SmartYouTubeTVExoBase {
    public SmartYouTubeTVExoWebView() {
        // we must set engine type as early as possible
        Browser.setEngineType(EngineType.WebView);
    }


}
