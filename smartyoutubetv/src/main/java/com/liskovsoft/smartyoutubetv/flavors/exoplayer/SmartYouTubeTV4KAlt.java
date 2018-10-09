package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.support.v4.app.Fragment;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.core.fragments.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KAltFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KFragmentBase;

public class SmartYouTubeTV4KAlt extends TwoFragmentsManagerActivity {
    @Override
    protected Fragment getFirstFragment() {
        return new SmartYouTubeTV4KAltFragment();
    }

    @Override
    protected Fragment getSecondFragment() {
        return null;
    }
}