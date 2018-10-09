package com.liskovsoft.smartyoutubetv.flavors.webview.fragments;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.core.fragments.SmartYouTubeTVFragmentBase;

public class SmartYouTubeTV1080Fragment extends SmartYouTubeTVFragmentBase {
    public SmartYouTubeTV1080Fragment() {
        // we must set engine type as early as possible
        Browser.setEngineType(EngineType.WebView);
    }
}