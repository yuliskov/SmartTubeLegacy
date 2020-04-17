package edu.mit.mobile.android.appupdater;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.widget.Button;
import edu.mit.mobile.android.appupdater.addons.UpdateApp;

import java.util.List;

/**
 * A handy pop-up dialog box which lists the changelog and asks if you want to update.
 *
 * @author steve
 */
public class OnUpdateDialog implements OnAppUpdateListener {
    private final Context mContext;
    private final CharSequence mAppName;
    private Uri[] mDownloadUris;
    private final Handler mHandler;
    private static final int MSG_SHOW_DIALOG = 1;
    private AlertDialog mDialog;
    private final UpdateApp mUpdateApp;
    private boolean mCancelDialog;

    public OnUpdateDialog(Context context, CharSequence appName) {
        mContext = context;
        mAppName = appName;
        mHandler = new MyHandler();
        mUpdateApp = new UpdateApp(mContext);
    }

    public void appUpdateStatus(boolean isLatestVersion, String latestVersionName, List<String> changelog, Uri[] downloadUris) {
        mCancelDialog = false;
        mDownloadUris = downloadUris;

        if (!isLatestVersion) {
            final Builder db = new Builder(mContext, R.style.AppDialog);
            db.setTitle(mAppName);

            final StringBuilder sb = new StringBuilder();
            sb.append(mContext.getString(R.string.app_update_new_version, latestVersionName, mAppName));
            sb.append("\n\n");
            for (final String item : changelog) {
                sb.append(" • ").append(item).append("\n");
            }

            db.setMessage(sb);

            db.setPositiveButton(R.string.upgrade, dialogOnClickListener);
            db.setNegativeButton(R.string.cancel, dialogOnClickListener);
            mDialog = db.create();
            mHandler.sendEmptyMessage(MSG_SHOW_DIALOG);
        }
    }

    private final DialogInterface.OnClickListener dialogOnClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    mUpdateApp.downloadAndInstall(mDownloadUris);
                case AlertDialog.BUTTON_NEGATIVE:
                    postpone();
            }

        }
    };

    private void postpone() {
        
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_DIALOG:
                    try {
                        // TODO fix this so it'll pop up appropriately
                        if (!mCancelDialog) {
                            mDialog.show();
                            setupFocus();
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                        // XXX ignore for the moment
                    }

                    break;
            }
        }
    }

    private void setupFocus() {
        Button okBtn = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);

        if (okBtn != null) {
            okBtn.requestFocus();
        }
    }

    @Override
    public boolean cancelPendingUpdate() {
        mCancelDialog = true;
        return mUpdateApp.cancelPendingUpdate();
    }

    @Override
    public boolean tryInstallPendingUpdate() {
        return mUpdateApp.tryInstallPendingUpdate();
    }
}