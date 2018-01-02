package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.os.Handler;
import com.liskovsoft.smartyoutubetv.R;
import edu.mit.mobile.android.appupdater.AppUpdateChecker;
import edu.mit.mobile.android.appupdater.OnUpdateDialog;

public class MainApkUpdater {
    private final Context mContext;
    private static final long UPDATE_CHECK_DELAY_MS = 3000;

    public MainApkUpdater(Context context) {
        mContext = context;
    }

    public void start() {
        SmartPreferences prefs = SmartPreferences.instance(mContext);
        if (prefs.getBootstrapUpdateCheck())
            checkForUpdatesAfterDelay();
    }

    private void checkForUpdatesAfterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkForUpdates();
            }
        }, UPDATE_CHECK_DELAY_MS);
    }

    private void checkForUpdates() {
        String updateUrl = mContext.getString(R.string.update_url);
        OnUpdateDialog dialog = new OnUpdateDialog(mContext, mContext.getString(R.string.app_name));
        AppUpdateChecker updateChecker = new AppUpdateChecker(mContext, updateUrl, dialog);
        updateChecker.forceCheckForUpdates();
    }
}
