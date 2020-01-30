package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import edu.mit.mobile.android.appupdater.AppUpdateChecker;
import edu.mit.mobile.android.appupdater.OnUpdateDialog;

public class MainApkUpdater {
    private static final String TAG = MainApkUpdater.class.getSimpleName();
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
        Log.d(TAG, "Running update checker");
        checkForUpdatesAfterDelay();
    }

    private void checkForUpdatesAfterDelay() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            checkForStableUpdates();
            checkForBetaUpdates();
        }, UPDATE_CHECK_DELAY_MS); // don't show dialog instantly after load
    }

    private void checkForStableUpdates() {
        if (mIsStableChecked) {
            CommonParams params = CommonParams.instance(mContext);

            runUpdateChecker(params.getStableUpdateUrls());
        }
    }

    private void checkForBetaUpdates() {
        if (mIsBetaChecked) {
            CommonParams params = CommonParams.instance(mContext);

            runUpdateChecker(params.getBetaUpdateUrls());
        }
    }

    private void runUpdateChecker(String[] updateUrls) {
        if (updateUrls == null) {
            Log.d(TAG, "Oops... can't start update. Url list seems empty.");
            return;
        }

        OnUpdateDialog dialog = new OnUpdateDialog(mContext, mContext.getString(R.string.app_name));
        AppUpdateChecker updateChecker = new AppUpdateChecker(mContext, updateUrls, dialog);
        // to minimize server payload use forceCheckForUpdatesIfStalled()
        updateChecker.forceCheckForUpdates();
    }
}
