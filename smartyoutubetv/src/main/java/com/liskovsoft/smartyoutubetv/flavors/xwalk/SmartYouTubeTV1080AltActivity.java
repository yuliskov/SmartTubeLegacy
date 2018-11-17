package com.liskovsoft.smartyoutubetv.flavors.xwalk;

import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.flavors.common.SingleFragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.xwalk.fragments.SmartYouTubeTV1080AltFragment;

public class SmartYouTubeTV1080AltActivity extends SingleFragmentManagerActivity {
    private final BrowserFragment mFragment;

    public SmartYouTubeTV1080AltActivity() {
        // create fragments in constructor so there will be more time for initialization
        mFragment = new SmartYouTubeTV1080AltFragment();
    }

    @Override
    protected BrowserFragment getBrowserFragment() {
        return mFragment;
    }
}
