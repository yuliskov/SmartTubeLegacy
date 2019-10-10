package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.os.Bundle;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;

public class SmartYouTubeTV4KAmazon extends SmartYouTubeTV4K {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntent().putExtra(LoadingManager.HIDE_TIPS, true);
    }
}
