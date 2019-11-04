package com.liskovsoft.smartyoutubetv.misc.appstatewatcher;

import android.app.Activity;
import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.common.FragmentManagerActivity;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.ATVChannelsHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.ATVYouTubeBridgeHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.AmazonYouTubeBridgeHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.BackupAndRestoreHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.BolshoeTVCacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.CacheCleanHandler;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers.LoadingCheckHandler;

public class AppStateWatcher extends AppStateWatcherBase {
    private final Context mContext;

    public AppStateWatcher(Activity context) {
        mContext = context;

        if (Helpers.isAndroidTVLauncher(context)) {
            addHandler(new ATVYouTubeBridgeHandler(context));
        } else if (Helpers.isAmazonFireTVDevice()) {
            addHandler(new AmazonYouTubeBridgeHandler(context));
        }

        if (context instanceof FragmentManagerActivity) {
            addHandler(new LoadingCheckHandler((FragmentManagerActivity) context));
        }

        if (SmartUtils.isBolshoeTV()) {
            addHandler(new BolshoeTVCacheCleanHandler(context));
        } else {
            addHandler(new CacheCleanHandler(context));
        }

        addHandler(new BackupAndRestoreHandler(context));

        addHandler(new DisplayModeSaverHandler(context));

        // update recommendations
        addHandler(new ATVChannelsHandler(context));
    }

}
