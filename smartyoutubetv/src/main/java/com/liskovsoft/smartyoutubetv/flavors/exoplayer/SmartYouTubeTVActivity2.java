package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;

public class SmartYouTubeTVActivity2 extends SmartYouTubeTVActivity {
    public SmartYouTubeTVActivity2() {
        // don't forget set preferred engine
        Browser.setEngineType(EngineType.XWalk);
    }
}