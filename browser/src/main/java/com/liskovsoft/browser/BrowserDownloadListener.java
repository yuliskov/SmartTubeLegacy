package com.liskovsoft.browser;

import android.webkit.DownloadListener;

/**
 * An abstract download listener that allows passing extra information as
 * part of onDownloadStart callback.
 */
public abstract class BrowserDownloadListener implements DownloadListener {

    /**
     * Notify the host application that a file should be downloaded
     * @param url The full url to the content that should be downloaded
     * @param userAgent the user agent to be used for the download.
     * @param contentDisposition Content-disposition http header, if
     *                           present.
     * @param mimetype The mimetype of the content reported by the server
     * @param referer The referer associated with this url
     * @param contentLength The file size reported by the server
     */
    public abstract void onDownloadStart(String url, String userAgent,
            String contentDisposition, String mimetype, String referer,
            long contentLength);


    /**
     * Notify the host application that a file should be downloaded
     * @param url The full url to the content that should be downloaded
     * @param userAgent the user agent to be used for the download.
     * @param contentDisposition Content-disposition http header, if
     *                           present.
     * @param mimetype The mimetype of the content reported by the server
     * @param contentLength The file size reported by the server
     */
    @Override
    public void onDownloadStart(String url, String userAgent,
            String contentDisposition, String mimetype, long contentLength) {

        onDownloadStart(url, userAgent, contentDisposition, mimetype, null,
                      contentLength);
    }
}
