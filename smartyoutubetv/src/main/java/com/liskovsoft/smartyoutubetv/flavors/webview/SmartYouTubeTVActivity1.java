package com.liskovsoft.smartyoutubetv.flavors.webview;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.core.SmartYouTubeTVManagerActivity;

public class SmartYouTubeTVActivity1 extends SmartYouTubeTVManagerActivity {
    @Override
    protected Fragment getFragment() {
        return new SmartYouTubeTVFragment();
    }
}
