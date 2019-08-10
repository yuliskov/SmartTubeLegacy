package com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import com.liskovsoft.sharedutils.dialogs.YesNoDialog;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.AppVersionTrackerBase.Handler;

import java.io.File;
import java.util.ArrayList;

public class DataBackupHandler extends Handler implements DialogInterface.OnClickListener {
    private static final String TAG = DataBackupHandler.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<File> mDataDirs;
    private static final String WEBVIEW_SUBDIR = "app_webview";
    private static final String XWALK_SUBDIR = "app_xwalkcore";
    private static final String SHARED_PREFS_SUBDIR = "shared_prefs";
    private final File mBackupDir;

    public DataBackupHandler(Context context) {
        mContext = context;
        mDataDirs = new ArrayList<>();
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, WEBVIEW_SUBDIR));
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, XWALK_SUBDIR));
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, SHARED_PREFS_SUBDIR));

        mBackupDir = new File(Environment.getExternalStorageDirectory(), String.format("data/%s/Backup", mContext.getPackageName()));
    }

    @Override
    public void onUpdate() {
        if (isExternalStorageWritable()) {
            backupData();
        }
    }

    @Override
    public void onInstall() {
        if (mBackupDir.isDirectory()) {
            askUserPermission();
        }
    }

    private void backupData() {
        Log.d(TAG, "App has been updated. Doing data backup...");

        if (mBackupDir.isDirectory()) {
            // remove old backup
            FileHelpers.delete(mBackupDir);
        }

        for (File dataDir : mDataDirs) {
            if (dataDir.isDirectory()) {
                FileHelpers.copy(dataDir, new File(mBackupDir, dataDir.getName()));
            }
        }
    }

    private void restoreData() {
        Log.d(TAG, "App just updated. Restoring data...");

        if (!mBackupDir.isDirectory()) {
            // backup not exists
            return;
        }

        for (File dataDir : mDataDirs) {
            if (dataDir.isDirectory()) {
                // remove old data
                FileHelpers.delete(dataDir);
            }

            FileHelpers.copy(new File(mBackupDir, dataDir.getName()), dataDir);
        }

        // to apply settings we need to kill the app
        System.exit(0);
    }

    private void askUserPermission() {
        YesNoDialog.create(mContext, R.string.do_restore_data_msg, this, R.style.AppDialog);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
                restoreData();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
