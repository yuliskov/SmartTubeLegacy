package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KAltFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.fragments.PlayerFragment;

public class SmartYouTubeTV4KAlt extends TwoFragmentsManagerActivity {
    private final BrowserFragment mBrowser;
    private final PlayerFragment mPlayer;

    public SmartYouTubeTV4KAlt() {
        // create fragments in constructor so there will be more time for initialization
        mBrowser = new SmartYouTubeTV4KAltFragment();
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