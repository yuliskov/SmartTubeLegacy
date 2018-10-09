package com.liskovsoft.smartyoutubetv.flavors.xwalk;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.core.fragments.SingleFragmentManagerActivity;

public class SmartYouTubeTV1080AltActivity extends SingleFragmentManagerActivity {
    @Override
    protected Fragment getFragment() {
        return new SmartYouTubeTVFragment();
    }
}
