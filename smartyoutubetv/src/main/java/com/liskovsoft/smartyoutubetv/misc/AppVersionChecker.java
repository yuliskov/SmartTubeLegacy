package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class AppVersionChecker {
    private static final String TAG = AppVersionChecker.class.getSimpleName();
    private final Context mContext;

    public AppVersionChecker(Context context) {
        mContext = context;
    }

    public void run() {
        SmartPreferences prefs = CommonApplication.getPreferences();
        int code = prefs.getPreviousAppVersionCode();

        if (code != BuildConfig.VERSION_CODE) {
            prefs.setPreviousAppVersionCode(BuildConfig.VERSION_CODE);

            if (code != 0) {
                // put your logic here
                FileHelpers.deleteCache(mContext);
                Log.d(TAG, "App has been updated... Clearing cache...");
            }
        }
    }
}
