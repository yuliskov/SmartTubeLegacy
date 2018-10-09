package com.liskovsoft.smartyoutubetv.flavors.webview;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.core.fragments.SingleFragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.webview.fragments.SmartYouTubeTV1080Fragment;

public class SmartYouTubeTV1080Activity extends SingleFragmentManagerActivity {
    @Override
    protected Fragment getFragment() {
        return new SmartYouTubeTV1080Fragment();
    }
}
