package edu.mit.mobile.android.appupdater.downloadmanager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyDownloadListener {
    private final Context mContext;

    public MyDownloadListener(Context ctx) {
        mContext = ctx;
    }

    public void downloadFile(final String uriStr) {
        new GetFileInfo(new GetFileInfoListener() {
            @Override
            public void onTaskCompleted(String fileName) {
                enqueueFile(uriStr, fileName);
                showProgress();
            }
        }).execute(uriStr);
    }

    private void showProgress() {
        Intent intent = new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        mContext.startActivity(intent);
    }

    private void enqueueFile(String uriStr, String fileName) {
        Log.d(this.getClass().getName(), "trying to run external download manager on " + uriStr);

        Uri uri = Uri.parse(uriStr);
        DownloadManager.Request r = new DownloadManager.Request(uri);

        // This put the download in the same Download dir the browser uses
        if (fileName == null)
            fileName = getFileName(uri);
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        // When downloading music and videos they will be listed in the player
        // (Seems to be available since Honeycomb only)
        r.allowScanningByMediaScanner();

        // Notify user when download is completed
        // (Seems to be available since Honeycomb only)
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Start download
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(r);

        Toast.makeText(mContext, "Starting download: " + fileName, Toast.LENGTH_LONG).show();
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public interface GetFileInfoListener {
        void onTaskCompleted(String result);
    }

    class GetFileInfo extends AsyncTask<String, Integer, String>
    {

        private final GetFileInfoListener mListener;

        public GetFileInfo(GetFileInfoListener listener) {
            mListener = listener;
        }

        protected String doInBackground(String... urls)
        {
            URL url;
            String filename = null;
            try {
                url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                conn.setInstanceFollowRedirects(false);

                String depo = conn.getHeaderField("Content-Disposition");

                if (depo != null)
                    filename = depo.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
                else
                    filename = URLUtil.guessFileName(urls[0], null, null);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
            }
            return filename;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (mListener != null)
                mListener.onTaskCompleted(result);
        }
    }

}
