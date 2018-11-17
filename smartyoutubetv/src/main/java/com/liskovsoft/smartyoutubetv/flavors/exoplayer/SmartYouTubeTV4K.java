package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.fragments.PlayerFragment;

public class SmartYouTubeTV4K extends TwoFragmentsManagerActivity {
    private final BrowserFragment mBrowser;
    private final PlayerFragment mPlayer;

    public SmartYouTubeTV4K() {
        // create fragments in constructor so there will be more time for initialization
        mBrowser = new SmartYouTubeTV4KFragment();
        mPlayer = new ExoPlayerFragment();
    }

    @Override
    protected BrowserFragment getBrowserFragment() {
        return mBrowser;
    }

    @Override
    protected PlayerFragment getPlayerFragment() {
        return mPlayer;
    }
}
