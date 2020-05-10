package com.liskovsoft.smartyoutubetv.misc.appstatewatcher;

import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.ATVChannelsHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.ATVYouTubeBridgeHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.AdBlockPermissionsHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.AmazonYouTubeBridgeHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.ApkUpdaterHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.BackupAndRestoreHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.CacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.LoadingCheckHandler;

public class AppStateWatcher extends AppStateWatcherBase {
    public AppStateWatcher(FragmentManagerActivity context) {
        super(context);

        // Don't enable! Old ui may popup sometimes
        //addHandler(new ForceNewUIHandler(context));

        addHandler(new AdBlockPermissionsHandler(context, this));

        addHandler(new ApkUpdaterHandler(context, this));

        addHandler(new ATVYouTubeBridgeHandler(context, this));
        addHandler(new AmazonYouTubeBridgeHandler(context, this));

        addHandler(new LoadingCheckHandler(context));

        addHandler(new CacheCleanHandler(context));

        addHandler(new BackupAndRestoreHandler(context, this));

        // update recommendations
        addHandler(new ATVChannelsHandler(context));
    }

}
