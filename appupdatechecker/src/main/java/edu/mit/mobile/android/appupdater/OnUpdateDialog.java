package edu.mit.mobile.android.appupdater;

import android.support.v7.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
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
    private Dialog mDialog;

    public OnUpdateDialog(Context context, CharSequence appName) {
        mContext = context;
        mAppName = appName;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_SHOW_DIALOG:
                        try {
                            // TODO fix this so it'll pop up appropriately
                            mDialog.show();
                        } catch (final Exception e) {
                            e.printStackTrace();
                            // XXX ignore for the moment
                        }

                        break;
                }
            }
        };
    }

    public void appUpdateStatus(boolean isLatestVersion, String latestVersionName, List<String> changelog, Uri[] downloadUris) {
        this.mDownloadUris = downloadUris;

        if (!isLatestVersion) {
            final Builder db = new Builder(mContext, com.liskovsoft.smartyoutubetv.common.R.style.AppDialog);
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
                case Dialog.BUTTON_POSITIVE:
                    downloadAndInstall(mDownloadUris);
                case Dialog.BUTTON_NEGATIVE:
                    postpone();
            }

        }
    };

    private void postpone() {
        
    }

    private void downloadAndInstall(Uri[] downloadUris) {
        UpdateApp updateApp = new UpdateApp(mContext);
        updateApp.execute(downloadUris);
    }
}