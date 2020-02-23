package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcher;

public class AmazonYouTubeBridgeHandler extends BridgeHandlerBase {
    private static final int AMAZON_YOUTUBE_PKG_HASH = 1430778939;
    private static final String AMAZON_YOUTUBE_PKG_NAME = "com.amazon.firetv.youtube";
    private static final String AMAZON_BRIDGE_PKG_URL = "https://github.com/yuliskov/SmartYouTubeTV/releases/download/stable/Amazon_SYTV_Bridge.apk";

    public AmazonYouTubeBridgeHandler(Activity context, AppStateWatcher appStateWatcher) {
        super(context, appStateWatcher);
    }

    @Override
    protected String getPackageName() {
        return AMAZON_YOUTUBE_PKG_NAME;
    }

    @Override
    protected String getPackageUrl() {
        return AMAZON_BRIDGE_PKG_URL;
    }

    @Override
    protected int getPackageSignatureHash() {
        return AMAZON_YOUTUBE_PKG_HASH;
    }

    @Override
    protected boolean checkLauncher() {
        return Helpers.isAmazonFireTVDevice();
    }
}
