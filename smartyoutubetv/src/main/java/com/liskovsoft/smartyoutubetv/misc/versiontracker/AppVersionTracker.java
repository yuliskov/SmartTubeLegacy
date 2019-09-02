package com.liskovsoft.smartyoutubetv.misc.versiontracker;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.CacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.DataBackupHandler;

public class AppVersionTracker extends AppVersionTrackerBase {
    private final Context mContext;

    public AppVersionTracker(Context context) {
        mContext = context;
        //addHandler(new CacheCleanHandler(context));
        //addHandler(new DataBackupHandler(context));
    }

}
