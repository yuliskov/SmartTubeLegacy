package com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;

public class SmartYouTubeTV4KFragment extends SmartYouTubeTV4KFragmentBase {
    public SmartYouTubeTV4KFragment() {
        // we must set engine type as early as possible
        Browser.setEngineType(EngineType.WebView);
    }


}
