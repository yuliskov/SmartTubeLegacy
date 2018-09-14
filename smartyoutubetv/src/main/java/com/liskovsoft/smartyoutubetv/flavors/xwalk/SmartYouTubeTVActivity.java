package com.liskovsoft.smartyoutubetv.flavors.xwalk;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.core.SmartYouTubeTVActivityBase;

public class SmartYouTubeTVActivity extends SmartYouTubeTVActivityBase {
    public SmartYouTubeTVActivity() {
        // don't forget set preferred engine
        Browser.setEngineType(EngineType.XWalk);
    }
}