package com.liskovsoft.smartyoutubetv.flavors.xwalk.fragments;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.flavors.common.fragments.SmartYouTubeTVBaseFragment;

public class SmartYouTubeTV1080AltFragment extends SmartYouTubeTVBaseFragment {
    public SmartYouTubeTV1080AltFragment() {
        // don't forget set preferred engine
        Browser.setEngineType(EngineType.XWalk);
    }
}