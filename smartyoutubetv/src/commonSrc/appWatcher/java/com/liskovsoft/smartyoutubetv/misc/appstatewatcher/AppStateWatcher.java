package com.liskovsoft.smartyoutubetv.misc.appstatewatcher;

import android.app.Activity;
import android.content.Context;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.BackupAndRestoreHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.CacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.ForceOldUIHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.LoadingCheckHandler;

public class AppStateWatcher extends AppStateWatcherBase {
    private final Context mContext;

    public AppStateWatcher(Activity context) {
        mContext = context;

        if (context instanceof FragmentManagerActivity) {
            addHandler(new LoadingCheckHandler((FragmentManagerActivity) context));
        }

        addHandler(new CacheCleanHandler(context));

        addHandler(new BackupAndRestoreHandler(context));

        // same UI across all of the devices
        addHandler(new ForceOldUIHandler(context));
    }

}
