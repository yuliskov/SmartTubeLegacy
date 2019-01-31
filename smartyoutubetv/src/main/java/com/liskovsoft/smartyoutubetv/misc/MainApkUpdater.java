package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.os.Handler;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import edu.mit.mobile.android.appupdater.AppUpdateChecker;
import edu.mit.mobile.android.appupdater.OnUpdateDialog;

public class MainApkUpdater {
    private final Context mContext;
    private static final long UPDATE_CHECK_DELAY_MS = 3000;
    private boolean mIsStableChecked;
    private boolean mIsBetaChecked;

    public MainApkUpdater(Context context) {
        mContext = context;

        SmartPreferences prefs = SmartPreferences.instance(mContext);

        switch (prefs.getBootstrapUpdateCheck()) {
            case SmartPreferences.UPDATE_CHECK_STABLE:
                mIsStableChecked = true;
                break;
            case SmartPreferences.UPDATE_CHECK_BETA:
                mIsBetaChecked = true;
                break;
        }
    }

    public void start() {
        checkForUpdatesAfterDelay();
    }

    private void checkForUpdatesAfterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkForStableUpdates();
                checkForBetaUpdates();
            }
        }, UPDATE_CHECK_DELAY_MS); // don't show dialog instantly after load
    }

    private void checkForStableUpdates() {
        if (mIsStableChecked) {
            String updateUrlGDrive = mContext.getString(R.string.update_url_gdrive);
            String updateUrlGitHub = mContext.getString(R.string.update_url_github);

            runUpdateChecker(new String[] {updateUrlGitHub, updateUrlGDrive});
        }
    }

    private void checkForBetaUpdates() {
        if (mIsBetaChecked) {
            String betaUpdateUrlGitHub = mContext.getString(R.string.beta_update_url_github);

            runUpdateChecker(new String[] {betaUpdateUrlGitHub});
        }
    }

    private void runUpdateChecker(String[] updateUrls) {
        OnUpdateDialog dialog = new OnUpdateDialog(mContext, mContext.getString(R.string.app_name));
        AppUpdateChecker updateChecker = new AppUpdateChecker(mContext, updateUrls, dialog);
        // to minimize server payload use forceCheckForUpdatesIfStalled()
        updateChecker.forceCheckForUpdates();
    }
}
