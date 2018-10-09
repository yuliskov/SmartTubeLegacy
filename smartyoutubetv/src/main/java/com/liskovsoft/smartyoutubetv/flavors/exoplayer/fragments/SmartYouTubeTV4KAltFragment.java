package com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;

public class SmartYouTubeTV4KAltFragment extends SmartYouTubeTV4KFragmentBase {
    public SmartYouTubeTV4KAltFragment() {
        // don't forget set preferred engine
        Browser.setEngineType(EngineType.XWalk);
    }
}