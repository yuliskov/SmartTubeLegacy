package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.fragments.SmartYouTubeTV4KAltFragment;

public class SmartYouTubeTV4KAlt extends TwoFragmentsManagerActivity {
    @Override
    protected Fragment getBrowserFragment() {
        return new SmartYouTubeTV4KAltFragment();
    }

    @Override
    protected Fragment getPlayerFragment() {
        return null;
    }
}