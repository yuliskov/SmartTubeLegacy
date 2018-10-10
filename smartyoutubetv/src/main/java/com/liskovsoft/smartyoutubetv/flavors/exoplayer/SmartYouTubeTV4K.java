package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import com.liskovsoft.browser.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

public class SmartYouTubeTV4K extends TwoFragmentsManagerActivity {
    @Override
    protected BrowserFragment getBrowserFragment() {
        return new SmartYouTubeTV4KFragment();
    }

    @Override
    protected PlayerFragment getPlayerFragment() {
        return new ExoPlayerFragment();
    }
}
