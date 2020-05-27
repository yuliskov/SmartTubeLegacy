package edu.mit.mobile.android.appupdater.addons;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import edu.mit.mobile.android.appupdater.R;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManager;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManager.MyRequest;

import java.io.File;

/**
 * Usage:
 * <pre>
 *   atualizaApp = new UpdateApp(ctx);
 *   atualizaApp.execute("http://serverurl/appfile.apk");
 * </pre>
 */
public class UpdateApp extends AsyncTask<Uri[],Void,Void> {
    private static final String TAG = UpdateApp.class.getSimpleName();
    private final Context mContext;
    private boolean mInProgress;
    private boolean mCancelInstall;
    private String mDownPath;

    public UpdateApp(Context context) {
        mContext = context;
    }

    public void downloadAndInstall(Uri[] downloadUris) {
        if (!mInProgress) {
            execute(downloadUris);
        }
    }

    @Override
    protected Void doInBackground(Uri[]... args) {
        mDownPath = null;
        mInProgress = true;

        Uri[] uris = args[0];

        String path = null;
        for (Uri uri : uris) {
            if (URLUtil.isValidUrl(uri.toString())) {
                path = downloadPackage(uri.toString());
                if (path != null)
                    break;
            }
        }

        if (path != null) {
            if (!mCancelInstall) {
                Helpers.installPackage(mContext, path);
            } else {
                mDownPath = path;
            }
        } else {
            Log.e(TAG, "Error while download. Install path is null");
            showMessage(mContext.getResources().getString(R.string.cant_download_msg));
        }

        mInProgress = false;

        return null;
    }

    private void showMessage(final String msg) {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show());
    }

    private String downloadPackage(String uri) {
        File cacheDir = FileHelpers.getCacheDir(mContext);
        if (cacheDir == null) {
            return null;
        }
        File outputFile = new File(cacheDir, "update.apk");
        String path = null;
        try {
            MyDownloadManager manager = new MyDownloadManager(mContext);
            MyRequest request = new MyRequest(Uri.parse(uri));
            request.setDestinationUri(Uri.fromFile(outputFile));
            try {
                long id = manager.enqueue(request);
                int size = manager.getSizeForDownloadedFile(id);
                Uri destination = manager.getUriForDownloadedFile(id);
                path = size > 1_000_000 ? destination.getPath() : null; // it could be a web page instead of apk
            } catch (IllegalStateException ex) { // 403 or something else
                Log.d(TAG, ex.toString());
            }
        } catch (IllegalStateException ex) { // CANNOT OBTAIN WRITE PERMISSIONS
            Log.e(TAG, ex.getMessage(), ex);
        }
        return path;
    }

    // Smart update logic

    public boolean cancelPendingUpdate() {
        mCancelInstall = true;
        return mInProgress;
    }

    public boolean tryInstallPendingUpdate() {
        mCancelInstall = false;

        if (mDownPath != null) {
            Helpers.installPackage(mContext, mDownPath);
            mDownPath = null;
            return true;
        }

        return false;
    }
}
