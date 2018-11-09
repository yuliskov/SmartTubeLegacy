package com.liskovsoft.smartyoutubetv.flavors.webview;

import android.support.v4.app.Fragment;
import com.liskovsoft.smartyoutubetv.flavors.common.SingleFragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.webview.fragments.SmartYouTubeTV1080Fragment;

public class SmartYouTubeTV1080Activity extends SingleFragmentManagerActivity {
    private final Fragment mFragment;

    public SmartYouTubeTV1080Activity() {
        // create fragments in constructor so there will be more time for initialization
        mFragment = new SmartYouTubeTV1080Fragment();
    }

    @Override
    protected Fragment getFragment() {
        return mFragment;
    }
}
