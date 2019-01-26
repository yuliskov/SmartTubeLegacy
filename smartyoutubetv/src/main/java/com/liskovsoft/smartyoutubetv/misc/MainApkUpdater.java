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
    private final boolean mIsStableChecked;
    private final boolean mIsBetaChecked;

    public MainApkUpdater(Context context) {
        mContext = context;

        SmartPreferences prefs = SmartPreferences.instance(mContext);
        mIsStableChecked = prefs.getBootstrapUpdateCheck();
        mIsBetaChecked = prefs.getBootstrapBetaUpdateCheck();
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
        if (mIsStableChecked && !mIsBetaChecked) {
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
