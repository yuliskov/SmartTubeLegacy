package com.liskovsoft.browser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.Controller.EventListener;

import java.util.Map;

public class PreloadController implements WebViewController {

    private static final boolean LOGD_ENABLED = false;
    private static final String LOGTAG = "PreloadController";

    private Context mContext;

    public PreloadController(Context ctx) {
        mContext = ctx.getApplicationContext();

    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public Activity getActivity() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "getActivity()");
        return null;
    }

    @Override
    public TabControl getTabControl() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "getTabControl()");
        return null;
    }

    @Override
    public WebViewFactory getWebViewFactory() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "getWebViewFactory()");
        return null;
    }

    @Override
    public void onSetWebView(Tab tab, WebView view) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onSetWebView()");
    }

    @Override
    public void createSubWindow(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "createSubWindow()");
    }

    @Override
    public void onPageStarted(Tab tab, Bitmap favicon) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onPageStarted()");
        if (tab.getWebView() != null) {
            // Clear history of all previously visited pages. When the
            // user visits a preloaded tab, the only item in the history
            // list should the currently viewed page.
            tab.getWebView().clearHistory();
        }
    }

    @Override
    public void onPageFinished(Tab tab, String url) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onPageFinished()");
        if (tab != null) {
            final WebView view = tab.getWebView();
            if (view != null) {
                // Clear history of all previously visited pages. When the
                // user visits a preloaded tab.
                view.clearHistory();
            }
        }
    }

    @Override
    public void onProgressChanged(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onProgressChanged()");
    }

    @Override
    public void onReceivedTitle(Tab tab, String title) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onReceivedTitle()");
    }

    @Override
    public void onFavicon(Tab tab, Bitmap icon) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onFavicon()");
    }

    @Override
    public boolean shouldOverrideUrlLoading(Tab tab, String url) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "shouldOverrideUrlLoading()");
        return false;
    }

    @Override
    public boolean shouldOverrideKeyEvent(KeyEvent event) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "shouldOverrideKeyEvent()");
        return false;
    }

    @Override
    public boolean onUnhandledKeyEvent(KeyEvent event) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onUnhandledKeyEvent()");
        return false;
    }

    @Override
    public void doUpdateVisitedHistory(Tab tab, boolean isReload) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "doUpdateVisitedHistory()");
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "getVisitedHistory()");
    }

    @Override
    public void onReceivedHttpAuthRequest(Tab tab, WebView view,
                                    HttpAuthHandler handler, String host,
                                    String realm) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onReceivedHttpAuthRequest()");
    }

    @Override
    public void onDownloadStart(Tab tab, String url, String useragent,
                                    String contentDisposition, String mimeType,
                                    String referer, long contentLength) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onDownloadStart()");
    }

    @Override
    public void showCustomView(Tab tab, View view, int requestedOrientation,
                                    CustomViewCallback callback) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "showCustomView()");
    }

    @Override
    public void hideCustomView() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "hideCustomView()");
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "getDefaultVideoPoster()");
        return null;
    }

    @Override
    public View getVideoLoadingProgressView() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "getVideoLoadingProgressView()");
        return null;
    }

    @Override
    public void showSslCertificateOnError(WebView view,
                                    SslErrorHandler handler, SslError error) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "showSslCertificateOnError()");
    }

    @Override
    public void onUserCanceledSsl(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onUserCanceledSsl()");
    }

    @Override
    public boolean shouldShowErrorConsole() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "shouldShowErrorConsole()");
        return false;
    }

    @Override
    public void onUpdatedSecurityState(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onUpdatedSecurityState()");
    }

    @Override
    public void showFileChooser(ValueCallback<Uri[]> callback, FileChooserParams params) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "showFileChooser()");
    }

    @Override
    public void endActionMode() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "endActionMode()");
    }

    @Override
    public void attachSubWindow(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "attachSubWindow()");
    }

    @Override
    public void dismissSubWindow(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "dismissSubWindow()");
    }

    @Override
    public Tab openTab(String url, boolean incognito, boolean setActive, boolean useCurrent) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "openTab()");
        return null;
    }

    @Override
    public Tab openTab(String url, Tab parent, boolean setActive, boolean useCurrent) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "openTab()");
        return null;
    }

    @Override
    public boolean switchToTab(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "switchToTab()");
        return false;
    }

    @Override
    public void closeTab(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "closeTab()");
    }

    @Override
    public void bookmarkedStatusHasChanged(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "bookmarkedStatusHasChanged()");
    }

    @Override
    public void showAutoLogin(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "showAutoLogin()");
    }

    @Override
    public void hideAutoLogin(Tab tab) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "hideAutoLogin()");
    }

    @Override
    public boolean shouldCaptureThumbnails() {
        return false;
    }

    // My Custom Methods

    @Override
    public void setListener(EventListener listener) {

    }

    @Override
    public void onControllerStart() {

    }

    @Override
    public void onSaveControllerState(Bundle state) {

    }

    @Override
    public void onRestoreControllerState(Bundle state) {

    }

    @Override
    public void setDefaultUrl(Uri url) {

    }

    @Override
    public void setDefaultHeaders(Map<String, String> headers) {

    }

    @Override
    public Map<String, String> getDefaultHeaders() {
        return null;
    }

    // End My Custom Methods

}
