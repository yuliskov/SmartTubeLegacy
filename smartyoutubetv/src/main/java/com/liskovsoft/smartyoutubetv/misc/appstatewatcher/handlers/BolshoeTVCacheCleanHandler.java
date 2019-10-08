package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import android.content.Intent;
import com.liskovsoft.smartyoutubetv.misc.CacheManager;
import com.liskovsoft.smartyoutubetv.misc.Constants;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class BolshoeTVCacheCleanHandler extends StateHandler {
    private final Activity mContext;

    public BolshoeTVCacheCleanHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onInit() {
        checkIntent(mContext.getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        checkIntent(intent);
    }

    private void checkIntent(Intent intent) {
        if (intent != null &&
            Constants.ACTION_CLEAR_CACHE.equals(intent.getAction())) {

            new CacheManager(mContext).clearCache();

            mContext.finish();
        }
    }
}
