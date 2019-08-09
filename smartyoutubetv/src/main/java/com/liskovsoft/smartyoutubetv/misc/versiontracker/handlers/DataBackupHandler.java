package com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
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
    private static final String BACKUP_SUBDIR = "data";
    private final File mBackupDir;

    public DataBackupHandler(Context context) {
        mContext = context;
        mDataDirs = new ArrayList<>();
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, WEBVIEW_SUBDIR));
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, XWALK_SUBDIR));
        mDataDirs.add(new File(mContext.getApplicationInfo().dataDir, SHARED_PREFS_SUBDIR));
        mBackupDir = new File(Environment.getExternalStorageDirectory(), BACKUP_SUBDIR + "/" + mContext.getPackageName());
    }

    @Override
    public void onUpdate() {
        Log.d(TAG, "App has been updated. Doing data backup...");

        for (File dataDir : mDataDirs) {
            if (dataDir.isDirectory()) {
                FileHelpers.copy(dataDir, new File(mBackupDir, dataDir.getName()));
            }
        }
    }

    @Override
    public void onInstall() {
        if (mBackupDir.isDirectory()) {
            askUserPermission();
        }
    }

    private void restoreData() {
        Log.d(TAG, "App just updated. Restoring data...");

        if (!mBackupDir.isDirectory()) {
            return;
        }

        for (File dataDir : mDataDirs) {
            if (dataDir.isDirectory()) {
                FileHelpers.copy(new File(mBackupDir, dataDir.getName()), dataDir);
            }
        }
    }

    private void askUserPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppDialog);
        builder
                .setMessage(R.string.do_restore_data_msg)
                .setPositiveButton(R.string.yes_btn, this)
                .setNegativeButton(R.string.no_btn, this)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
                MessageHelpers.showMessage(mContext, R.string.restoring_data_msg);
                restoreData();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
        }
    }
}
