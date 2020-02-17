package com.liskovsoft.smartyoutubetv.misc.appstatewatcher;

import android.app.Activity;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.CacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.LoadingCheckHandler;

public class AppStateWatcher extends AppStateWatcherBase {
    public AppStateWatcher(Activity context) {
        super(context);

        if (context instanceof FragmentManagerActivity) {
            addHandler(new LoadingCheckHandler((FragmentManagerActivity) context));
        }

        addHandler(new CacheCleanHandler(context));

        // save settings across app reinstall
        //addHandler(new BackupAndRestoreHandler(context));
    }
}
