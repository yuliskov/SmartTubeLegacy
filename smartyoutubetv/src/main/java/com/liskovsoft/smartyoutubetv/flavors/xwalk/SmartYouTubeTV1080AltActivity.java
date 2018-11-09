package com.liskovsoft.smartyoutubetv.flavors.xwalk;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.flavors.common.SingleFragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.xwalk.fragments.SmartYouTubeTV1080AltFragment;

public class SmartYouTubeTV1080AltActivity extends SingleFragmentManagerActivity {
    private final Fragment mFragment;

    public SmartYouTubeTV1080AltActivity() {
        // create fragments in constructor so there will be more time for initialization
        mFragment = new SmartYouTubeTV1080AltFragment();
    }

    @Override
    protected Fragment getFragment() {
        return mFragment;
    }
}
