package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import com.liskovsoft.browser.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KAltFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

public class SmartYouTubeTV4KAlt extends TwoFragmentsManagerActivity {
    @Override
    protected BrowserFragment getBrowserFragment() {
        return new SmartYouTubeTV4KAltFragment();
    }

    @Override
    protected PlayerFragment getPlayerFragment() {
        return new ExoPlayerFragment();
    }
}