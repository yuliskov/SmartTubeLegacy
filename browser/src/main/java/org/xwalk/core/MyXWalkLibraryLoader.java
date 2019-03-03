// Copyright (c) 2015 Intel Corporation. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.xwalk.core;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * MyXWalkLibraryLoader is a low level inteface to schedule decompressing, downloading, activating
 * the Crosswalk runtime. Normal user is recommended to use XWalkActivity or XWalkInitializer which
 * is simpler and more user-friendly.
 *
 * The appropriate invocation order is:
 * prepareToInit() -
 * [ if the Crosswalk runtime is supposed to be compressed - startDecompress() ] -
 * startActivate() -
 * [ if the Crosswalk runtime doesn't match - download suitable version - startActivate() ] - over
 */

class MyXWalkLibraryLoader {
    /**
     * Interface used to decompress the Crosswalk runtime
     */
    public interface DecompressListener {
        /**
         * Run on the UI thread to notify decompression is started.
         *
         * <p> This method will not be invoked if the Crosswalk runtime is not compressed or has
         * already been decompressed.
         */
        public void onDecompressStarted();

        /**
         * Run on the UI thread to notify decompression is cancelled.
         */
        public void onDecompressCancelled();

        /**
         * Run on the UI thread to notify decompression is completed successfully.
         */
        public void onDecompressCompleted();
    }

    /**
     * Interface used to activate the Crosswalk runtime
     */
    public interface ActivateListener {
        /**
         * Run on the UI thread to notify activation is started
         */
        public void onActivateStarted();

        /**
         * Run on the UI thread to notify activation failed
         */
        public void onActivateFailed();

        /**
         * Run on the UI thread to notify activation is completed successfully
         */
        public void onActivateCompleted();
    }

    /**
     * Interface used to download the Crosswalk runtime
     */
    public interface DownloadListener {
        /**
         * Run on the UI thread to notify download is started
         */
        public void onDownloadStarted();

        /**
         * Run on the UI thread to notify the download progress
         * @param percentage Shows the download progress in percentage
         */
        public void onDownloadUpdated(int percentage);

        /**
         * Run on the UI thread to notify download is cancelled
         */
        public void onDownloadCancelled();

        /**
         * Run on the UI thread to notify download is completed successfully
         * @param uri The Uri where the downloaded file is stored
         */
        public void onDownloadCompleted(Uri uri);

        /**
         * Run on the UI thread to notify download failed
         *
         * @param status The download status defined in android.app.DownloadManager.
         *               The value maybe STATUS_FAILED or STATUS_PAUSED
         * @param error The download error defined in android.app.DownloadManager.
         *              This parameter only makes sense when the status is STATUS_FAILED
         */
        public void onDownloadFailed(int status, int error);
    }

    private static final String DEFAULT_DOWNLOAD_FILE_NAME = "xwalk_update.apk";
    private static final String DOWNLOAD_WITHOUT_NOTIFICATION =
            "android.permission.DOWNLOAD_WITHOUT_NOTIFICATION";
    private static final String TAG = "XWalkLib";

    private static AsyncTask<Void, Integer, Integer> sActiveTask;

    public static boolean isInitializing() {
        return sActiveTask != null &&
                (sActiveTask instanceof DecompressTask
                        || sActiveTask instanceof ActivateTask);
    }

    public static boolean isDownloading() {
        return sActiveTask != null &&
                (sActiveTask instanceof DownloadManagerTask
                        || sActiveTask instanceof HttpDownloadTask);
    }

    /**
     * Return true if running in shared mode, false if in embedded mode.
     *
     * <p>This method must be invoked after the Crosswalk runtime has already been initialized
     * successfully.
     */
    public static boolean isSharedLibrary() {
        return XWalkCoreWrapper.getInstance().isSharedMode();
    }

    /**
     * Return true if the Crosswalk runtime has already been initialized successfully either in
     * embedded mode or shared mode, false otherwise.
     */
    public static boolean isLibraryReady() {
        return XWalkCoreWrapper.getInstance() != null;
    }

    /**
     * Return the library status defined in {@link XWalkLibraryInterface}.
     */
    public static int getLibraryStatus() {
        return XWalkCoreWrapper.getCoreStatus();
    }

    /**
     * Prepare to start initializing before all other procedures.
     *
     * <p>This method must be invoked on the UI thread.
     */
    public static void prepareToInit(Context context) {
        XWalkEnvironment.init(context);
        XWalkCoreWrapper.handlePreInit(context.getClass().getName());
    }

    public static void finishInit(Context context) {
        XWalkCoreWrapper.handlePostInit(context.getClass().getName());
    }

    /**
     * Start decompressing the Crosswalk runtime in background
     *
     * <p>This method must be invoked on the UI thread.
     *
     * @param listener The {@link DecompressListener} to use
     */
    public static void startDecompress(DecompressListener listener) {
        new DecompressTask(listener).execute();
    }

    /**
     * Attempt to cancel decompression
     *
     * @return false if decompression is not running or could not be cancelled, true otherwise
     */
    public static boolean cancelDecompress() {
        return sActiveTask != null && sActiveTask instanceof DecompressTask
                && sActiveTask.cancel(true);
    }

    /**
     * Start activating the Crosswalk runtime in background. The activation is not cancelable.
     *
     * <p>This method must be invoked on the UI thread.
     *
     * @param listener The {@link ActivateListener} to use
     */
    public static void startActivate(ActivateListener listener) {
        new ActivateTask(listener).execute();
    }

    /**
     * Start downloading the Crosswalk runtime in background via Android DownlomadManager
     *
     * <p>This method must be invoked on the UI thread.
     *
     * @param listener The {@link DownloadListener} to use
     * @param context The context to get DownloadManager
     * @param url The URL of the Crosswalk runtime
     */
    public static void startDownloadManager(DownloadListener listener, Context context,
            String url) {
        new DownloadManagerTask(listener, context, url).execute();
    }

    /**
     * Attempt to cancel download manager
     *
     * @return false if download is not running or could not be cancelled, true otherwise
     */
    public static boolean cancelDownloadManager() {
        return sActiveTask != null && sActiveTask instanceof DownloadManagerTask
                && sActiveTask.cancel(true);
    }

    /**
     * Start downloading the Crosswalk runtime in background via HTTP connection
     *
     * <p>This method must be invoked on the UI thread.
     *
     * @param listener The {@link DownloadListener} to use
     * @param context The context to get DownloadManager
     * @param url The URL of the Crosswalk runtime
     */
    public static void startHttpDownload(DownloadListener listener, Context context, String url) {
        new HttpDownloadTask(listener, context, url).execute();
    }

    /**
     * Attempt to cancel http download
     *
     * @return False if download is not running or could not be cancelled, true otherwise
     */
    public static boolean cancelHttpDownload() {
        return sActiveTask != null && sActiveTask instanceof HttpDownloadTask
                && sActiveTask.cancel(true);
    }

    private static class DecompressTask extends AsyncTask<Void, Integer, Integer> {
        DecompressListener mListener;
        boolean mIsCompressed;
        boolean mIsDecompressed;

        DecompressTask(DecompressListener listener) {
            super();
            mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "DecompressTask started");
            sActiveTask = this;

            mIsCompressed = XWalkDecompressor.isLibraryCompressed();
            if (mIsCompressed) {
                SharedPreferences sp = XWalkEnvironment.getSharedPreferences();
                int version = sp.getInt("version", 0);
                mIsDecompressed = version > 0 && version == XWalkAppVersion.API_VERSION;
            }
            if (mIsCompressed && !mIsDecompressed) mListener.onDecompressStarted();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            if (!mIsCompressed || mIsDecompressed) return 0;

            if (!XWalkDecompressor.decompressLibrary()) return 1;

            SharedPreferences sp = XWalkEnvironment.getSharedPreferences();
            sp.edit().putInt("version", XWalkAppVersion.API_VERSION).apply();
            return 0;
        }

        @Override
        protected void onCancelled(Integer result) {
            Log.d(TAG, "DecompressTask cancelled");
            sActiveTask = null;
            mListener.onDecompressCancelled();
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.d(TAG, "DecompressTask finished, " + result);
            if (result.intValue() != 0) {
                throw new RuntimeException("Decompression Failed");
            }
            sActiveTask = null;
            mListener.onDecompressCompleted();
        }
    }

    private static class ActivateTask extends AsyncTask<Void, Integer, Integer> {
        ActivateListener mListener;

        ActivateTask(ActivateListener listener) {
            super();
            mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "ActivateTask started");
            sActiveTask = this;
            mListener.onActivateStarted();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            if (XWalkCoreWrapper.getInstance() != null) return -1;
            return XWalkCoreWrapper.attachXWalkCore();
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == XWalkLibraryInterface.STATUS_MATCH) {
                XWalkCoreWrapper.dockXWalkCore();
            }

            Log.d(TAG, "ActivateTask finished, " + result);
            sActiveTask = null;
            if (result > XWalkLibraryInterface.STATUS_MATCH) {
                mListener.onActivateFailed();
            } else {
                mListener.onActivateCompleted();
            }
        }
    }

    private static class DownloadManagerTask extends AsyncTask<Void, Integer, Integer> {
        private DownloadListener mListener;
        private Context mContext;
        private String mDownloadUrl;
        private MyDownloadManager mDownloadManager;
        private long mDownloadId;
        private boolean isDone;

        DownloadManagerTask(DownloadListener listener, Context context, String url) {
            super();
            mListener = listener;
            mContext = context;
            mDownloadUrl = url;
            mDownloadManager = new MyDownloadManager(mContext);
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "DownloadManagerTask started, " + mDownloadUrl);
            sActiveTask = this;
            mListener.onDownloadStarted();
        }

        private void showMessage(final String msg) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected Integer doInBackground(Void... params) {
            if (mDownloadUrl == null) return DownloadManager.STATUS_FAILED;


            String savedFile = DEFAULT_DOWNLOAD_FILE_NAME;

            File downloadDir = FileHelpers.getCacheDir(mContext);

            File downloadFile = new File(downloadDir, savedFile);
            if (downloadFile.isFile()) downloadFile.delete();

            MyDownloadManager.MyRequest request = new MyDownloadManager.MyRequest(Uri.parse(mDownloadUrl));
            request.setDestinationUri(Uri.fromFile(downloadFile));
            request.setProgressListener(new MyDownloadManager.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    publishProgress((int)bytesRead, (int)contentLength);
                    isDone = done;
                }
            });

            try {
                mDownloadId = mDownloadManager.enqueue(request);
            } catch (IllegalStateException ex) {
                Log.e(TAG, ex.getMessage(), ex);
                MessageHelpers.showMessage(mContext, TAG, ex);
            }

            return isDone ? DownloadManager.STATUS_SUCCESSFUL : DownloadManager.STATUS_FAILED;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "DownloadManagerTask updated: " + progress[0] + "/" + progress[1]);
            int percentage = 0;
            if (progress[1] > 0) {
                percentage = (int) (progress[0] * 100.0 / progress[1]);
                mListener.onDownloadUpdated(percentage);
            }
        }

        @Override
        protected void onCancelled(Integer result) {
            mDownloadManager.remove(mDownloadId);

            Log.d(TAG, "DownloadManagerTask cancelled");
            sActiveTask = null;
            mListener.onDownloadCancelled();
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.d(TAG, "DownloadManagerTask finished, " + result);
            sActiveTask = null;

            if (result == DownloadManager.STATUS_SUCCESSFUL) {
                try {
                    Uri uri = mDownloadManager.getUriForDownloadedFile(mDownloadId);

                    Log.d(TAG, "Uri for downloaded file: " + uri);

                    if (uri != null) {
                        mListener.onDownloadCompleted(uri);
                    }
                } catch (IllegalStateException ex) {
                    Log.e(TAG, ex.getMessage(), ex);
                    MessageHelpers.showMessage(mContext, TAG, ex);
                    mListener.onDownloadFailed(result, DownloadManager.ERROR_UNKNOWN);
                }
            } else {
                int error = DownloadManager.ERROR_UNKNOWN;
                mListener.onDownloadFailed(result, error);
            }
        }
    }

    // This is used only in download mode where we want to save the downloaded file to application
    // private storage and it's also intended to solve the exception found in XWALK-5951
    private static class HttpDownloadTask extends AsyncTask<Void, Integer, Integer> {
        private static final String XWALK_DOWNLOAD_DIR = "xwalk_download";
        private static final int UPDATE_INTERVAL_MS = 500;
        private static final int DOWNLOAD_SUCCESS = 0;
        private static final int DOWNLOAD_FAILED = -1;

        private DownloadListener mListener;
        private Context mContext;
        private String mDownloadUrl;
        private File mDownloadedFile;
        private long mProgressUpdateTime;

        HttpDownloadTask(DownloadListener listener, Context context, String url) {
            super();
            mListener = listener;
            mContext = context;
            mDownloadUrl = url;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "HttpDownloadTask started, " + mDownloadUrl);
            sActiveTask = this;

            String savedFile = DEFAULT_DOWNLOAD_FILE_NAME;
            try {
                String name = new File(new URL(mDownloadUrl).getPath()).getName();
                if (!name.isEmpty()) savedFile = name;
            } catch (MalformedURLException | NullPointerException e) {
                Log.e(TAG, "Invalid download URL " + mDownloadUrl);
                mDownloadUrl = null;
                return;
            }
            mDownloadedFile = new File(mContext.getDir(XWALK_DOWNLOAD_DIR, Context.MODE_PRIVATE),
                    savedFile);

            mListener.onDownloadStarted();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            if (mDownloadUrl == null) return DOWNLOAD_FAILED;
            if (mDownloadedFile.exists()) mDownloadedFile.delete();

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(mDownloadUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                    return DOWNLOAD_FAILED;
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();
                output = new FileOutputStream(mDownloadedFile);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) return DOWNLOAD_FAILED;
                    total += count;
                    output.write(data, 0, count);

                    long time = SystemClock.uptimeMillis();
                    if (time - mProgressUpdateTime > UPDATE_INTERVAL_MS) {
                        mProgressUpdateTime = time;
                        publishProgress((int)total, fileLength);
                    }
                }
                output.flush();
            } catch (Exception e) {
                // TODO: modified
                // return DOWNLOAD_FAILED;
                throw new IllegalStateException(e);
            } finally {
                try {
                    if (output != null) output.close();
                    if (input != null) input.close();
                } catch (IOException ignored) {
                }

                if (connection != null) connection.disconnect();
            }
            return DOWNLOAD_SUCCESS;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "HttpDownloadTask updated: " + progress[0] + "/" + progress[1]);
            int percentage = 0;
            if (progress[1] > 0) percentage = (int) (progress[0] * 100.0 / progress[1]);
            mListener.onDownloadUpdated(percentage);
        }

        @Override
        protected void onCancelled(Integer result) {
            Log.d(TAG, "HttpDownloadTask cancelled");
            sActiveTask = null;
            mListener.onDownloadCancelled();
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.d(TAG, "HttpDownloadTask finished, " + result);
            sActiveTask = null;

            if (result == DOWNLOAD_SUCCESS) {
                mListener.onDownloadCompleted(Uri.fromFile(mDownloadedFile));
            } else {
                // Error codes is not used in download mode.
                mListener.onDownloadFailed(DOWNLOAD_FAILED, 0);
            }
        }
    }
}
