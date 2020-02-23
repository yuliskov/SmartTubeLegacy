package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import com.liskovsoft.sharedutils.dialogs.YesNoDialog;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcher;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManagerTask;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManagerTask.DownloadListener;

abstract class BridgeHandlerBase extends StateHandler implements OnClickListener {
    private static final String TAG = BridgeHandlerBase.class.getSimpleName();
    private final Activity mContext;
    private final AppStateWatcher mAppStateWatcher;
    private boolean mRemoveOldApkFirst;
    private boolean mIsFirstRun;
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onDownloadCompleted(Uri uri) {
            Helpers.installPackage(mContext, uri.getPath());
        }
    };

    public BridgeHandlerBase(Activity context, AppStateWatcher appStateWatcher) {
        mContext = context;
        mAppStateWatcher = appStateWatcher;
    }
    
    @Override
    public void onFirstRun() {
        mIsFirstRun = true;
    }

    @Override
    public void onLoad() {
        mAppStateWatcher.addRunAfterLock(this::runBridgeInstaller);
    }

    private void runBridgeInstaller() {
        if (!checkLauncher()) {
            return;
        }

        if (CommonApplication.getPreferences().getDisableYouTubeBridge()) {
            return;
        }

        if (mIsFirstRun) { // do not scare user when the app just installed
            return;
        }

        PackageInfo info = getPackageSignature(getPackageName());

        if (info != null) { // bridge installed
            if (Helpers.isUserApp(info) && info.signatures[0].hashCode() != getPackageSignatureHash()) {
                // official YouTube installed
                mRemoveOldApkFirst = true;
                askUserPermissionToReinstallBridgeApk();
            }
        } else { // bridge not installed
            askUserPermissionToInstallBridgeApk();
        }
    }

    private void askUserPermissionToInstallBridgeApk() {
        mAppStateWatcher.setLock(true);
        YesNoDialog.create(mContext, R.string.do_install_bridge_apk, this, R.style.AppDialog);
    }

    private void askUserPermissionToReinstallBridgeApk() {
        mAppStateWatcher.setLock(true);
        YesNoDialog.create(mContext, R.string.do_reinstall_bridge_apk, this, R.style.AppDialog);
    }

    private PackageInfo getPackageSignature(String pkgName) {
        PackageManager manager = mContext.getPackageManager();
        PackageInfo packageInfo = null;

        try {
            packageInfo = manager.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageInfo;
    }

    private void installPackageFromUrl(Context context, String pkgUrl) {
        Log.d(TAG, "Installing bridge apk");

        MyDownloadManagerTask task = new MyDownloadManagerTask(listener, context, pkgUrl);
        task.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Helpers.REMOVE_PACKAGE_CODE) {
            installPackageFromUrl(mContext, getPackageUrl());
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
                if (mRemoveOldApkFirst) {
                    Helpers.removePackageAndGetResult(mContext, getPackageName());
                } else {
                    installPackageFromUrl(mContext, getPackageUrl());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                CommonApplication.getPreferences().setDisableYouTubeBridge(true);
                break;
        }
        mAppStateWatcher.setLock(false);
    }

    protected abstract String getPackageName();
    protected abstract String getPackageUrl();
    protected abstract int getPackageSignatureHash();
    protected abstract boolean checkLauncher();
}
