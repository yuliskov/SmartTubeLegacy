package com.liskovsoft.smartyoutubetv.misc.versiontracker;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.CacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.DataBackupHandler;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.LoadingCheckHandler;

public class AppVersionTracker extends AppVersionTrackerBase {
    private final Context mContext;

    public AppVersionTracker(Context context) {
        mContext = context;

        //if (context instanceof FragmentManagerActivity) {
        //    addHandler(new LoadingCheckHandler((FragmentManagerActivity) context));
        //}

        addHandler(new CacheCleanHandler(context));
        addHandler(new DataBackupHandler(context));
    }

}
