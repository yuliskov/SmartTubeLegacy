package com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.AppVersionTrackerBase.Handler;

import java.io.File;
import java.util.ArrayList;

public class DataBackupHandler extends Handler {
    private static final String TAG = DataBackupHandler.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<File> mDataDirs;
    private static final String WEBVIEW_SUBDIR = "app_webview";
    private static final String XWALK_SUBDIR = "app_xwalkcore";
    private static final String SHARED_PREFS_SUBDIR = "shared_prefs";

    public DataBackupHandler(Context context) {
        mContext = context;
        mDataDirs = new ArrayList<>();
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, WEBVIEW_SUBDIR));
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, XWALK_SUBDIR));
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, SHARED_PREFS_SUBDIR));
    }

    @Override
    public void onUpdate() {
        Log.d(TAG, "App has been updated. Doing data backup...");
        File backup = new File(mContext.getExternalCacheDir(), "Backup");
        for (File dataDir : mDataDirs) {
            if (dataDir.isDirectory()) {
                FileHelpers.copy(dataDir, backup);
            }
        }
    }

    @Override
    public void onInstall() {
        Log.d(TAG, "App just updated. Restoring data...");
    }
}
