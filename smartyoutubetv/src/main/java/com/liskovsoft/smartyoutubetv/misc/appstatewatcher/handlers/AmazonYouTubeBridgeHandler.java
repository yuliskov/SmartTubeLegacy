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
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManagerTask;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManagerTask.DownloadListener;

public class AmazonYouTubeBridgeHandler extends StateHandler implements OnClickListener {
    private static final String TAG = AmazonYouTubeBridgeHandler.class.getSimpleName();
    private final Activity mContext;
    private static final String AMAZON_YOUTUBE_PKG_NAME = "com.amazon.firetv.youtube";
    private static final int AMAZON_YOUTUBE_PKG_HASH = 1430778939;
    private static final String AMAZON_BRIDGE_PKG_URL = "https://github.com/yuliskov/SmartYouTubeTV/releases/download/stable/Amazon_SYTV_Bridge.apk";
    private boolean mRemoveOldApkFirst;
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onDownloadCompleted(Uri uri) {
            Helpers.installPackage(mContext, uri.getPath());
        }
    };

    public AmazonYouTubeBridgeHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onInit() {
        if (CommonApplication.getPreferences().getDisableAmazonBridge()) {
            return;
        }

        PackageInfo info = getPackageInfo(AMAZON_YOUTUBE_PKG_NAME);

        if (info != null) {
            if (info.signatures[0].hashCode() != AMAZON_YOUTUBE_PKG_HASH) {
                // official YouTube installed
                mRemoveOldApkFirst = true;
                askUserPermissionToReinstallBridgeApk();
            }
        } else {
            askUserPermissionToInstallBridgeApk();
        }
    }

    private void askUserPermissionToInstallBridgeApk() {
        YesNoDialog.create(mContext, R.string.do_install_bridge_apk, this, R.style.AppDialog);
    }

    private void askUserPermissionToReinstallBridgeApk() {
        YesNoDialog.create(mContext, R.string.do_reinstall_bridge_apk, this, R.style.AppDialog);
    }

    private PackageInfo getPackageInfo(String pkgName) {
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
            installPackageFromUrl(mContext, AMAZON_BRIDGE_PKG_URL);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
                if (mRemoveOldApkFirst) {
                    Helpers.removePackageAndGetResult(mContext, AMAZON_YOUTUBE_PKG_NAME);
                } else {
                    installPackageFromUrl(mContext, AMAZON_BRIDGE_PKG_URL);
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                CommonApplication.getPreferences().setDisableAmazonBridge(true);
                break;
        }
    }
}
