package com.liskovsoft.smartyoutubetv.flavors.xwalk;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.core.fragments.SingleFragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.xwalk.fragments.SmartYouTubeTV1080AltFragment;

public class SmartYouTubeTV1080AltActivity extends SingleFragmentManagerActivity {
    @Override
    protected Fragment getFragment() {
        return new SmartYouTubeTV1080AltFragment();
    }
}
