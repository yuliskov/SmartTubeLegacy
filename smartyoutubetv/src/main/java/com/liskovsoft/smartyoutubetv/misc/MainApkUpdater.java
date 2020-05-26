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
    private final OnUpdateDialog mDialog;
    private final AppUpdateChecker mUpdateChecker;

    public MainApkUpdater(Context context) {
        mContext = context;

        SmartPreferences prefs = SmartPreferences.instance(mContext);

        mDialog = new OnUpdateDialog(mContext, mContext.getString(R.string.app_name));
        mUpdateChecker = new AppUpdateChecker(mContext, mDialog);

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
        new Handler(Looper.getMainLooper())
                .postDelayed(this::checkForUpdates, UPDATE_CHECK_DELAY_MS); // don't show dialog instantly after load
    }

    private void checkForUpdates() {
        if (mIsStableChecked) {
            CommonParams params = CommonParams.instance(mContext);

            runUpdateChecker(params.getStableUpdateUrls());
        }
    }

    public boolean cancelPendingUpdate() {
        return mUpdateChecker.cancelPendingUpdate();
    }

    private void runUpdateChecker(String[] updateUrls) {
        if (updateUrls == null) {
            Log.d(TAG, "Oops... can't start update. Url list seems empty.");
            return;
        }

        // To minimize server payload use forceCheckForUpdatesIfStalled()
        mUpdateChecker.forceCheckForUpdates(updateUrls);
    }
}
