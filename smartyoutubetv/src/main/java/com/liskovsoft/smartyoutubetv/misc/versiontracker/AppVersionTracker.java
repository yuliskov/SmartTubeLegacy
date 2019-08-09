package com.liskovsoft.smartyoutubetv.misc.versiontracker;

import android.content.Context;

public class AppVersionTracker extends AppVersionTrackerBase {
    private final Context mContext;

    public AppVersionTracker(Context context) {
        mContext = context;
    }

}
