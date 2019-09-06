package com.liskovsoft.smartyoutubetv.misc.versiontracker;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.CacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.BackupAndRestoreHandler;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers.LoadingCheckHandler;

public class AppStateWatcher extends AppStateWatcherBase {
    private final Context mContext;

    public AppStateWatcher(Context context) {
        mContext = context;

        if (context instanceof FragmentManagerActivity) {
            addHandler(new LoadingCheckHandler((FragmentManagerActivity) context));
        }

        addHandler(new CacheCleanHandler(context));
        addHandler(new BackupAndRestoreHandler(context));
    }

}
