package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcher;

public class ATVYouTubeBridgeHandler extends BridgeHandlerBase {
    private static final int ATV_YOUTUBE_PKG_HASH = 1430778939;
    private static final String ATV_YOUTUBE_PKG_NAME = "com.google.android.youtube.tv";
    private static final String ATV_BRIDGE_PKG_URL = "https://github.com/yuliskov/SmartYouTubeTV/releases/download/stable/ATV_SYTV_Bridge.apk";
    private final Activity mContext;

    public ATVYouTubeBridgeHandler(Activity context, AppStateWatcher appStateWatcher) {
        super(context, appStateWatcher);
        mContext = context;
    }

    @Override
    protected String getPackageName() {
        return ATV_YOUTUBE_PKG_NAME;
    }

    @Override
    protected String getPackageUrl() {
        return ATV_BRIDGE_PKG_URL;
    }

    @Override
    protected int getPackageSignatureHash() {
        return ATV_YOUTUBE_PKG_HASH;
    }

    @Override
    protected boolean checkLauncher() {
        return Helpers.isAndroidTVLauncher(mContext);
    }
}
