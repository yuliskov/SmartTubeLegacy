package com.liskovsoft.smartyoutubetv;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;

public class SmartYouTubeTVActivity extends SmartYouTubeTVActivityBase {
    public SmartYouTubeTVActivity() {
        // don't forget set preferred engine
        Browser.setEngineType(EngineType.XWalk);
    }
}
