package com.liskovsoft.smartyoutubetv.flavors.xwalk;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.core.SmartYouTubeTVFragmentBase;

public class SmartYouTubeTVFragment extends SmartYouTubeTVFragmentBase {
    public SmartYouTubeTVFragment() {
        // don't forget set preferred engine
        Browser.setEngineType(EngineType.XWalk);
    }
}