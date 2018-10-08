package com.liskovsoft.smartyoutubetv.flavors.xwalk;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.core.SmartYouTubeTVManagerActivity;

public class SmartYouTubeTVActivity2 extends SmartYouTubeTVManagerActivity {
    @Override
    protected Fragment getFragment() {
        return new SmartYouTubeTVFragment();
    }
}
