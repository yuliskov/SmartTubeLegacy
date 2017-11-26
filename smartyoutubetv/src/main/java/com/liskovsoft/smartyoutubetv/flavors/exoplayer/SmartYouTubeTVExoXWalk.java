package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;

public class SmartYouTubeTVExoXWalk extends SmartYouTubeTVExoBase {
    public SmartYouTubeTVExoXWalk() {
        // don't forget set preferred engine
        Browser.setEngineType(EngineType.XWalk);
    }
}