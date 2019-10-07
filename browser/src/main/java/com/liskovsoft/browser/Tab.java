/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liskovsoft.browser;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.xwalk.XWalkResourceClientAdapter;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Vector;

/**
 * Essentially this is an WebView holder.
 */
public class Tab implements PictureListener {
    private static final String TAG = Tab.class.getSimpleName();
    private static Bitmap sDefaultFavicon;
    protected WebViewController mWebViewController;
    private final Context mContext;
    private final DataController mDataController;
    private WebView mMainView;
    private View mViewContainer;
    private WebView mSubView;

    private static final int MSG_CAPTURE = 42;
    private static final int CAPTURE_DELAY = 100;
    private static final int INITIAL_PROGRESS = 5;
    private int mPageLoadProgress;
    private boolean mInPageLoad;
    private PageState mCurrentState;
    private final static String USER_AGENT_HEADER = "User-Agent";
    private boolean mInForeground;
    private Vector<Tab> mChildren;
    private Tab mParent;
    // The tab ID
    private long mId = -1;
    // Saved bundle for when we are running low on memory. It contains the
    // information needed to restore the WebView if the user goes back to the
    // tab.
    private Bundle mSavedState;
    private BrowserSettings mSettings;

    // Used for saving and restoring each Tab
    static final String ID = "ID";
    static final String CURRURL = "currentUrl";
    static final String CURRTITLE = "currentTitle";
    static final String PARENTTAB = "parentTab";
    static final String APPID = "appid";
    static final String INCOGNITO = "privateBrowsingEnabled";
    static final String USERAGENT = "useragent";
    static final String CLOSEFLAG = "closeOnBack";
    private View mSubViewContainer;
    private String mAppId;
    private boolean mCloseOnBack;
    private DeviceAccountLogin mDeviceAccountLogin;
    private Bitmap mCapture;
    private Handler mHandler;
    //private PageLoadHandler mPageLoadHandler;


    private int mCaptureWidth;
    private int mCaptureHeight;
    // The Geolocation permissions prompt
    private GeolocationPermissionsPrompt mGeolocationPermissionsPrompt;
    // The permissions prompt
    private PermissionsPrompt mPermissionsPrompt;
    private DownloadListener mDownloadListener;
    // Main WebView wrapper
    private View mContainer;
    private EventListener mListener;

    public interface EventListener {
        void onPageStarted(Tab tab, Bitmap favicon);
        void onPageFinished(Tab tab, String url);
        void onReceiveError(Tab tab, int errorCode);
        /**
         * Fired one time per session.<br/>
         * Usually this means that network connection is good.<br/>
         * Also it is a good place to hide any loading placeholder.
         * @param tab tab object
         */
        void onLoadSuccess(Tab tab);
        /**
         * Called on new API 21+
         */
        WebResourceResponse shouldInterceptRequest(Tab tab, WebResourceRequest request);
        /**
         * Called on old API 14+
         */
        WebResourceResponse shouldInterceptRequest(Tab tab, String url);
    }

    // Construct a new tab
    Tab(WebViewController wvcontroller, WebView w) {
        this(wvcontroller, w, null);
    }

    Tab(WebViewController wvcontroller, Bundle state) {
        this(wvcontroller, null, state);
    }

    Tab(WebViewController wvcontroller, WebView w, Bundle state) {
        mWebViewController = wvcontroller;
        mContext = mWebViewController.getContext();
        mSettings = BrowserSettings.getInstance();
        mDataController = DataController.getInstance(mContext);

        mCurrentState = new PageState(mContext, w != null && w.isPrivateBrowsingEnabled());


        restoreState(state);

        if (getId() != -1) {
            Log.d(TAG, "Tab is restored, old id: " + getId());
        }

        // NOTE: duplicate tab ids fix. Force unique id even when tab is restored.
        mId = TabControl.getNextId();

        // NOTE: below could lead to duplicate tab ids error!
        //if (getId() == -1) {
        //    mId = TabControl.getNextId();
        //}

        // NOTE: state restored here!!!
        setWebView(w);
    }

    public void loadUrl(String url, Map<String, String> headers) {
        if (mMainView != null) {
            mWebViewClient.mLoadSuccess = true;
            mPageLoadProgress = INITIAL_PROGRESS;
            mInPageLoad = true;
            mCurrentState = new PageState(mContext, false, url, null);
            mMainView.loadUrl(url, headers);

            //setUserAgentFromHeaders(headers);
        }
    }

    public void reload() {
        if (mMainView == null) {
            return;
        }

        String url = mMainView.getUrl();
        loadUrl(url, mWebViewController.getDefaultHeaders());
    }

    private static synchronized Bitmap getDefaultFavicon(Context context) {
        if (sDefaultFavicon == null) {
            sDefaultFavicon = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.app_web_browser_sm);
        }
        return sDefaultFavicon;
    }

    public void setWebView(WebView w) {
        setWebView(w, true);
    }

    public void setListener(EventListener listener) {
        if (mListener != null && listener != null) {
            throw new IllegalStateException("Attempt to overwrite previously established Tab Listener!");
        }
        mListener = listener;
    }

    /**
     * Sets the WebView for this tab, correctly removing the old WebView from
     * the container view.
     */
    void setWebView(WebView w, boolean restore) {
        if (mMainView == w) {
            return;
        }

        // If the WebView is changing, the page will be reloaded, so any ongoing
        // Geolocation permission requests are void.
        if (mGeolocationPermissionsPrompt != null) {
            mGeolocationPermissionsPrompt.hide();
        }

        if (mPermissionsPrompt != null) {
            mPermissionsPrompt.hide();
        }

        mWebViewController.onSetWebView(this, w);

        if (mMainView != null) {
            mMainView.setPictureListener(null);
            if (w != null) {
                syncCurrentState(w, null);
            } else {
                mCurrentState = new PageState(mContext, false);
            }
        }
        // set the new one
        mMainView = w;
        // attach the WebViewClient, WebChromeClient and DownloadListener
        if (mMainView != null) {
            restoreInitialScale();

            mMainView.setWebViewClient(mWebViewClient);
            mMainView.setWebChromeClient(mWebChromeClient);
            // Attach DownloadManager so that downloads can start in an active
            // or a non-active window. This can happen when going to a site that
            // does a redirect after a period of time. The user could have
            // switched to another tab while waiting for the download to start.
            mMainView.setDownloadListener(mDownloadListener);
            TabControl tc = mWebViewController.getTabControl();
            if (tc != null && tc.getOnThumbnailUpdatedListener() != null) {
                mMainView.setPictureListener(this);
            }
            if (restore && (mSavedState != null)) {
                restoreUserAgent();
                // NOTE: webview state restored here (sate saved as binary data)
                WebBackForwardList restoredState
                        = mMainView.restoreState(mSavedState);
                if (restoredState == null || restoredState.getSize() == 0) {
                    Log.w(TAG, "Failed to restore WebView state!");
                    loadUrl(mCurrentState.mOriginalUrl, null);
                }
                mSavedState = null;
            }
        }
    }

    /**
     * Return the main window of this tab. Note: if a tab is freed in the
     * background, this can return null. It is only guaranteed to be
     * non-null for the current tab.
     * @return The main WebView of this tab.
     */
    public WebView getWebView() {
        return mMainView;
    }


    /**
     * Return the top window of this tab; either the subwindow if it is not
     * null or the main window.
     * @return The top window of this tab.
     */
    public WebView getTopWindow() {
        if (mSubView != null) {
            return mSubView;
        }
        return mMainView;
    }

    /**
     * Return the subwindow of this tab or null if there is no subwindow.
     * @return The subwindow of this tab or null.
     */
    public WebView getSubWebView() {
        return mSubView;
    }

    public void setSubWebView(WebView subView) {
        mSubView = subView;
    }

    public View getViewContainer() {
        return mViewContainer;
    }

    public void setViewContainer(View container) {
        mViewContainer = container;
    }

    View getSubViewContainer() {
        return mSubViewContainer;
    }

    /**
     * Get the title of this tab.
     */
    String getTitle() {
        if (mCurrentState.mTitle == null && mInPageLoad) {
            return mContext.getString(R.string.title_bar_loading);
        }
        return mCurrentState.mTitle;
    }

    /**
     * Get the favicon of this tab.
     */
    Bitmap getFavicon() {
        if (mCurrentState.mFavicon != null) {
            return mCurrentState.mFavicon;
        }
        return getDefaultFavicon(mContext);
    }

    String getOriginalUrl() {
        if (mCurrentState.mOriginalUrl == null) {
            return getUrl();
        }
        return UrlUtils.filteredUrl(mCurrentState.mOriginalUrl);
    }

    /**
     * Return the tab's error console. Creates the console if createIfNEcessary
     * is true and we haven't already created the console.
     * @param createIfNecessary Flag to indicate if the console should be
     *            created if it has not been already.
     * @return The tab's error console, or null if one has not been created and
     *         createIfNecessary is false.
     */
    ErrorConsoleView getErrorConsole(boolean createIfNecessary) {
        if (createIfNecessary && mErrorConsole == null) {
            mErrorConsole = new ErrorConsoleView(mContext);
            mErrorConsole.setWebView(mMainView);
        }
        return mErrorConsole;
    }

    /**
     * Remove the tab from the parent
     */
    void removeFromTree() {
        // detach the children
        if (mChildren != null) {
            for(Tab t : mChildren) {
                t.setParent(null);
            }
        }
        // remove itself from the parent list
        if (mParent != null) {
            mParent.mChildren.remove(this);
        }
        deleteThumbnail();
    }

    /**
     * This is used to get a new ID when the tab has been preloaded, before it is displayed and
     * added to TabControl. Preloaded tabs can be created before restoreInstanceState, leading
     * to overlapping IDs between the preloaded and restored tabs.
     */
    public void refreshIdAfterPreload() {
        mId = TabControl.getNextId();
    }

    void deleteThumbnail() {
        // TODO: not implemented
    }


    public Bitmap getScreenshot() {
        synchronized (Tab.this) {
            return mCapture;
        }
    }

    public void updateBookmarkedStatus() {
        mDataController.queryBookmarkStatus(getUrl(), mIsBookmarkCallback);
    }

    @Override
    public void onNewPicture(WebView view, Picture picture) {
        postCapture();
    }

    private void postCapture() {
        if (!mHandler.hasMessages(MSG_CAPTURE)) {
            mHandler.sendEmptyMessageDelayed(MSG_CAPTURE, CAPTURE_DELAY);
        }
    }

    public void disableUrlOverridingForLoad() {
        mDisableOverrideUrlLoading = true;
    }

    public void updateShouldCaptureThumbnails() {
        if (mWebViewController.shouldCaptureThumbnails()) {
            synchronized (Tab.this) {
                if (mCapture == null) {
                    mCapture = Bitmap.createBitmap(mCaptureWidth, mCaptureHeight,
                            Bitmap.Config.RGB_565);
                    mCapture.eraseColor(Color.WHITE);
                    if (mInForeground) {
                        postCapture();
                    }
                }
            }
        } else {
            synchronized (Tab.this) {
                mCapture = null;
                deleteThumbnail();
            }
        }
    }

    private DataController.OnQueryUrlIsBookmark mIsBookmarkCallback
            = new DataController.OnQueryUrlIsBookmark() {
        @Override
        public void onQueryUrlIsBookmark(String url, boolean isBookmark) {
            if (mCurrentState.mUrl.equals(url)) {
                mCurrentState.mIsBookmarkedSite = isBookmark;
                mWebViewController.bookmarkedStatusHasChanged(Tab.this);
            }
        }
    };

    /**
     * Destroy the tab's main WebView and subWindow if any
     */
    void destroy() {
        if (mMainView != null) {
            dismissSubWindow();
            // save the WebView to call destroy() after detach it from the tab
            WebView webView = mMainView;
            setWebView(null);
            webView.destroy();
        }
    }

    public void putInBackground() {
        // TODO: not implemented
    }

    public void putInForeground() {
        // TODO: not implemented
    }

    /**
     * @return TRUE if onPageStarted is called while onPageFinished is not
     *         called yet.
     */
    boolean inPageLoad() {
        return mInPageLoad;
    }

    void pause() {
        if (mMainView != null) {
            mMainView.onPause();
            if (mSubView != null) {
                mSubView.onPause();
            }
        }
    }

    boolean inForeground() {
        return mInForeground;
    }

    public boolean isPrivateBrowsingEnabled() {
        return false;
    }

    public boolean isSnapshot() {
        return false;
    }

    public boolean canGoBack() {
        return mMainView != null ? mMainView.canGoBack() : false;
    }

    public boolean canGoForward() {
        return mMainView != null ? mMainView.canGoForward() : false;
    }

    public void goBack() {
        if (mMainView != null) {
            mMainView.goBack();
        }
    }

    public void goForward() {
        if (mMainView != null) {
            mMainView.goForward();
        }
    }



    /**
     * @return The Bundle with the tab's state if it can be saved, otherwise null
     */
    public Bundle saveState() {
        // If the WebView is null it means we ran low on memory and we already
        // stored the saved state in mSavedState.
        if (mMainView == null) {
            return mSavedState;
        }

        // NOTE: don't count on WebViewClient. State might not be synced yet.
        syncCurrentState(mMainView, null);

        if (TextUtils.isEmpty(mCurrentState.mUrl)) {
            return null;
        }

        mSavedState = new Bundle();
        WebBackForwardList savedList = mMainView.saveState(mSavedState);
        if (savedList == null || savedList.getSize() == 0) {
            Log.w(TAG, "Failed to save back/forward list for "
                    + mCurrentState.mUrl);
        }

        mSavedState.putLong(ID, mId);
        mSavedState.putString(CURRURL, mCurrentState.mUrl);
        mSavedState.putString(CURRTITLE, mCurrentState.mTitle);
        mSavedState.putBoolean(INCOGNITO, mMainView.isPrivateBrowsingEnabled());
        if (mAppId != null) {
            mSavedState.putString(APPID, mAppId);
        }
        mSavedState.putBoolean(CLOSEFLAG, mCloseOnBack);
        // Remember the parent tab so the relationship can be restored.
        if (mParent != null) {
            mSavedState.putLong(PARENTTAB, mParent.mId);
        }
        mSavedState.putBoolean(USERAGENT,
                mSettings.hasDesktopUseragent(getWebView()));
        return mSavedState;
    }

    /**
     * When a Tab is created through the content of another Tab, then we
     * associate the Tabs.
     * @param child the Tab that was created from this Tab
     */
    void addChildTab(Tab child) {
        if (mChildren == null) {
            mChildren = new Vector<>();
        }
        mChildren.add(child);
        child.setParent(this);
    }

    Vector<Tab> getChildren() {
        return mChildren;
    }

    /**
     * Create a new subwindow unless a subwindow already exists.
     * @return True if a new subwindow was created. False if one already exists.
     */
    boolean createSubWindow() {
        if (mSubView == null) {
            mWebViewController.createSubWindow(this);
            mSubView.setWebViewClient(new SubWindowClient(mWebViewClient,
                    mWebViewController));
            mSubView.setWebChromeClient(new SubWindowChromeClient(
                    mWebChromeClient));
            // Set a different DownloadListener for the mSubView, since it will
            // just need to dismiss the mSubView, rather than close the Tab
            mSubView.setDownloadListener(new BrowserDownloadListener() {
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype, String referer,
                                            long contentLength) {
                    mWebViewController.onDownloadStart(Tab.this, url, userAgent,
                            contentDisposition, mimetype, referer, contentLength);
                    if (mSubView.copyBackForwardList().getSize() == 0) {
                        // This subwindow was opened for the sole purpose of
                        // downloading a file. Remove it.
                        mWebViewController.dismissSubWindow(Tab.this);
                    }
                }
            });
            mSubView.setOnCreateContextMenuListener(mWebViewController.getActivity());
            return true;
        }
        return false;
    }

    /**
     * Dismiss the subWindow for the tab.
     */
    void dismissSubWindow() {
        if (mSubView != null) {
            mWebViewController.endActionMode();
            mSubView.destroy();
            mSubView = null;
            mSubViewContainer = null;
        }
    }

    /**
     * Set the parent tab of this tab.
     */
    void setParent(Tab parent) {
        if (parent == this) {
            throw new IllegalStateException("Cannot set parent to self!");
        }
        mParent = parent;
        // This tab may have been freed due to low memory. If that is the case,
        // the parent tab id is already saved. If we are changing that id
        // (most likely due to removing the parent tab) we must update the
        // parent tab id in the saved Bundle.
        if (mSavedState != null) {
            if (parent == null) {
                mSavedState.remove(PARENTTAB);
            } else {
                mSavedState.putLong(PARENTTAB, parent.getId());
            }
        }

        // Sync the WebView useragent with the parent
        if (parent != null && mSettings.hasDesktopUseragent(parent.getWebView())
                != mSettings.hasDesktopUseragent(getWebView())) {
            mSettings.toggleDesktopUseragent(getWebView());
        }

        if (parent != null && parent.getId() == getId()) {
            throw new IllegalStateException("Parent has same ID as child!");
        }
    }

    /**
     * If this Tab was created through another Tab, then this method returns
     * that Tab.
     * @return the Tab parent or null
     */
    public Tab getParent() {
        return mParent;
    }

    /**
     * @return The geolocation permissions prompt for this tab.
     */
    GeolocationPermissionsPrompt getGeolocationPermissionsPrompt() {
        if (mGeolocationPermissionsPrompt == null) {
            ViewStub stub = (ViewStub) mContainer
                    .findViewById(R.id.geolocation_permissions_prompt);
            mGeolocationPermissionsPrompt = (GeolocationPermissionsPrompt) stub
                    .inflate();
        }
        return mGeolocationPermissionsPrompt;
    }

    /**
     * @return The permissions prompt for this tab.
     */
    PermissionsPrompt getPermissionsPrompt() {
        if (mPermissionsPrompt == null) {
            ViewStub stub = (ViewStub) mContainer
                    .findViewById(R.id.permissions_prompt);
            mPermissionsPrompt = (PermissionsPrompt) stub.inflate();
        }
        return mPermissionsPrompt;
    }

    String getUrl() {
        return UrlUtils.filteredUrl(mCurrentState.mUrl);
    }

    public long getId() {
        return mId;
    }

    /**
     * @return The application id string
     */
    String getAppId() {
        return mAppId;
    }

    /**
     * Set the application id string
     * @param id
     */
    void setAppId(String id) {
        mAppId = id;
    }

    public void setController(WebViewController ctl) {
        mWebViewController = ctl;
        updateShouldCaptureThumbnails();
    }

    // Called by DeviceAccountLogin when the Tab needs to have the auto-login UI
    // displayed.
    void setDeviceAccountLogin(DeviceAccountLogin login) {
        mDeviceAccountLogin = login;
    }

    // Returns non-null if the title bar should display the auto-login UI.
    DeviceAccountLogin getDeviceAccountLogin() {
        return mDeviceAccountLogin;
    }

    boolean closeOnBack() {
        return mCloseOnBack;
    }

    void setCloseOnBack(boolean close) {
        mCloseOnBack = close;
    }


    void resume() {
        if (mMainView != null) {
            setupHwAcceleration(mMainView);
            mMainView.onResume();
            if (mSubView != null) {
                mSubView.onResume();
            }
        }
    }

    private void setupHwAcceleration(View web) {
        if (web == null) return;
        BrowserSettings settings = BrowserSettings.getInstance();
        if (settings.isHardwareAccelerated()) {
            web.setLayerType(View.LAYER_TYPE_NONE, null);
        } else {
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    void updateCaptureFromBlob(byte[] blob) {
        synchronized (Tab.this) {
            if (mCapture == null) {
                return;
            }
            ByteBuffer buffer = ByteBuffer.wrap(blob);
            try {
                mCapture.copyPixelsFromBuffer(buffer);
            } catch (RuntimeException rex) {
                Log.e(TAG, "Load capture has mismatched sizes; buffer: "
                        + buffer.capacity() + " blob: " + blob.length
                        + "capture: " + mCapture.getByteCount());
                throw rex;
            }
        }
    }

    public boolean isBookmarkedSite() {
        return mCurrentState.mIsBookmarkedSite;
    }

    /**
     * Restore the state of the tab.
     * @param b state object
     */
    private void restoreState(Bundle b) {
        mSavedState = b;
        if (mSavedState == null) {
            return;
        }
        // Restore the internal state even if the WebView fails to restore.
        // This will maintain the app id, original url and close-on-exit values.
        mId = b.getLong(ID);
        mAppId = b.getString(APPID);
        mCloseOnBack = b.getBoolean(CLOSEFLAG);
        restoreUserAgent();
        String url = b.getString(CURRURL);
        String title = b.getString(CURRTITLE);
        boolean incognito = b.getBoolean(INCOGNITO);
        mCurrentState = new PageState(mContext, incognito, url, null);
        mCurrentState.mTitle = title;
        synchronized (Tab.this) {
            if (mCapture != null) {
                DataController.getInstance(mContext).loadThumbnail(this);
            }
        }
    }

    private void restoreUserAgent() {
        if (mMainView == null || mSavedState == null) {
            return;
        }
        if (mSavedState.getBoolean(USERAGENT)
                != mSettings.hasDesktopUseragent(mMainView)) {
            mSettings.toggleDesktopUseragent(mMainView);
        }
    }

    private void restoreInitialScale() {
        if (mMainView == null) {
            return;
        }

        // real display size (virtual pixel == real pixel)
        // 100 - normal resolution, 50 - 2160p resolution, 30 - 4k resolution, 0 - default
        // NOTE: you need to restart the browser in order this setting to work
        mMainView.setInitialScale(mSettings.getInitialScale());
    }

    public enum SecurityState {
        // The page's main resource does not use SSL. Note that we use this
        // state irrespective of the SSL authentication state of sub-resources.
        SECURITY_STATE_NOT_SECURE,
        // The page's main resource uses SSL and the certificate is good. The
        // same is true of all sub-resources.
        SECURITY_STATE_SECURE,
        // The page's main resource uses SSL and the certificate is good, but
        // some sub-resources either do not use SSL or have problems with their
        // certificates.
        SECURITY_STATE_MIXED,
        // The page's main resource uses SSL but there is a problem with its
        // certificate.
        SECURITY_STATE_BAD_CERTIFICATE,
    }

    private void syncCurrentState(WebView view, String url) {
        // Sync state (in case of stop/timeout)
        mCurrentState.mUrl = view.getUrl();
        if (mCurrentState.mUrl == null) {
            mCurrentState.mUrl = "";
        }
        mCurrentState.mOriginalUrl = view.getOriginalUrl();
        mCurrentState.mTitle = view.getTitle();
        mCurrentState.mFavicon = view.getFavicon();
        if (!URLUtil.isHttpsUrl(mCurrentState.mUrl)) {
            // In case we stop when loading an HTTPS page from an HTTP page
            // but before a provisional load occurred
            mCurrentState.mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
            mCurrentState.mSslCertificateError = null;
        }
        mCurrentState.mIncognito = view.isPrivateBrowsingEnabled();
    }

    private boolean mDisableOverrideUrlLoading;
    private long mLoadStartTime;
    private boolean mUpdateThumbnail;
    // AsyncTask for downloading touch icons
    DownloadTouchIcon mTouchIconLoader;
    // Keep the original url around to avoid killing the old WebView if the url
    // has not changed.
    // Error console for the tab
    private ErrorConsoleView mErrorConsole;

    // All the state needed for a page
    protected static class PageState {
        String mUrl;
        String mOriginalUrl;
        String mTitle;
        SecurityState mSecurityState;
        // This is non-null only when mSecurityState is SECURITY_STATE_BAD_CERTIFICATE.
        SslError mSslCertificateError;
        Bitmap mFavicon;
        boolean mIsBookmarkedSite;
        boolean mIncognito;

        PageState(Context c, boolean incognito) {
            mIncognito = incognito;
            if (mIncognito) {
                mOriginalUrl = mUrl = "browser:incognito";
                mTitle = c.getString(R.string.new_incognito_tab);
            } else {
                mOriginalUrl = mUrl = "";
                mTitle = c.getString(R.string.new_tab);
            }
            mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
        }

        PageState(Context c, boolean incognito, String url, Bitmap favicon) {
            mIncognito = incognito;
            mOriginalUrl = mUrl = url;
            if (URLUtil.isHttpsUrl(url)) {
                mSecurityState = SecurityState.SECURITY_STATE_SECURE;
            } else {
                mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
            }
            mFavicon = favicon;
        }

    }

    // -------------------------------------------------------------------------
    // WebViewClient implementation for the main WebView
    // -------------------------------------------------------------------------
    private MainWindowClient mWebViewClient = new MainWindowClient();

    public class MainWindowClient extends WebViewClient {
        boolean mFirstStarted;
        boolean mLoadSuccess = true;
        final String TAG = Tab.class.getSimpleName() + "." + WebViewClient.class.getSimpleName();

        /**
         * More code: see {@link XWalkResourceClientAdapter#onReceivedSslError}
         */
        public void onReceivedSslError(WebView view, ValueCallback<Boolean> callback, SslError error) {
            callback.onReceiveValue(true); // Ignore SSL certificate errors
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }

        private void onReceiveError(int errorCode) {
            Log.w(TAG, "network error: " + errorCode);

            if (errorCode == WebViewClient.ERROR_UNKNOWN) {
                Log.w(TAG, "ignore fake errors on Android 7.0");
                return;
            }

            if (mListener != null) {
                mListener.onReceiveError(Tab.this, errorCode);
            }

            mLoadSuccess = false;
        }

        @TargetApi(23)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            onReceiveError(error.getErrorCode());
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            onReceiveError(errorCode);
        }

        private void onLoadSuccess() {
            if (mLoadSuccess && mListener != null) {
                mListener.onLoadSuccess(Tab.this);
            }

            mLoadSuccess = false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (mListener != null)
                mListener.onPageStarted(Tab.this, favicon);

            // usually finish called after start
            // but sometimes there is fake finish
            mFirstStarted = true;

            mInPageLoad = true;
            mUpdateThumbnail = true;
            mPageLoadProgress = INITIAL_PROGRESS;
            mCurrentState = new PageState(mContext,
                    view.isPrivateBrowsingEnabled(), url, favicon);
            mLoadStartTime = SystemClock.uptimeMillis();

            // If we start a touch icon load and then load a new page, we don't
            // want to cancel the current touch icon loader. But, we do want to
            // create a new one when the touch icon url is known.
            if (mTouchIconLoader != null) {
                mTouchIconLoader.mTab = null;
                mTouchIconLoader = null;
            }

            // reset the error console
            if (mErrorConsole != null) {
                mErrorConsole.clearErrorMessages();
                if (mWebViewController.shouldShowErrorConsole()) {
                    mErrorConsole.showConsole(ErrorConsoleView.SHOW_NONE);
                }
            }

            // Cancel the auto-login process.
            if (mDeviceAccountLogin != null) {
                mDeviceAccountLogin.cancel();
                mDeviceAccountLogin = null;
                mWebViewController.hideAutoLogin(Tab.this);
            }

            // finally update the UI in the activity if it is in the foreground
            mWebViewController.onPageStarted(Tab.this, favicon);

            updateBookmarkedStatus();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // usually finish called after start
            // but sometimes there is fake finish
            if (!mFirstStarted)
                return;

            mDisableOverrideUrlLoading = false;

            if (!isPrivateBrowsingEnabled()) {
                Log.i(TAG, "logPageFinishedLoading: " + url + " " + (SystemClock.uptimeMillis() - mLoadStartTime));
            }

            syncCurrentState(view, url);
            if (mListener != null)
                mListener.onPageFinished(Tab.this, url);
            mWebViewController.onPageFinished(Tab.this, url);

            onLoadSuccess();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        // return true if want to hijack the url to let another app to handle it (FIRED ONCE)
        // don't rely on it: shouldOverrideUrlLoading called one time fore session, use shouldInterceptLoadRequest instead
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // NOTE: don't place this block into onPageStarted because on XWalk it logic is differ
            mLoadSuccess = true;

            if (!mDisableOverrideUrlLoading && mInForeground) {
                return mWebViewController.shouldOverrideUrlLoading(Tab.this, url);
            } else {
                return false;
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.i(TAG, "should intercept1? " + url);

            if (mListener != null) {
                return mListener.shouldInterceptRequest(Tab.this, url);
            }

            return super.shouldInterceptRequest(view, url);
        }
        
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.i(TAG, "should intercept2? " + request);

            if (mListener != null) {
                return mListener.shouldInterceptRequest(Tab.this, request);
            }

            return super.shouldInterceptRequest(view, request);
        }
    };

    // -------------------------------------------------------------------------
    // WebChromeClient implementation for the main WebView
    // -------------------------------------------------------------------------

    private WebChromeClient mWebChromeClient = new MainWindowChromeClient();
    private class MainWindowChromeClient extends WebChromeClient {
        // Helper method to create a new tab or sub window.
        private void createWindow(final boolean dialog, final Message msg) {
            WebView.WebViewTransport transport =
                    (WebView.WebViewTransport) msg.obj;
            if (dialog) {
                createSubWindow();
                mWebViewController.attachSubWindow(Tab.this);
                transport.setWebView(mSubView);
            } else {
                final Tab newTab = mWebViewController.openTab(null,
                        Tab.this, true, true);
                transport.setWebView(newTab.getWebView());
            }
            msg.sendToTarget();
        }

        @Override
        public boolean onCreateWindow(WebView view, final boolean dialog,
                                      final boolean userGesture, final Message resultMsg) {
            // only allow new window or sub window for the foreground case
            if (!mInForeground) {
                return false;
            }
            // Short-circuit if we can't create any more tabs or sub windows.
            if (dialog && mSubView != null) {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.too_many_subwindows_dialog_title)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setMessage(R.string.too_many_subwindows_dialog_message)
                        .setPositiveButton(R.string.ok, null)
                        .show();
                return false;
            } else if (!mWebViewController.getTabControl().canCreateNewTab()) {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.too_many_windows_dialog_title)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setMessage(R.string.too_many_windows_dialog_message)
                        .setPositiveButton(R.string.ok, null)
                        .show();
                return false;
            }

            // Short-circuit if this was a user gesture.
            if (userGesture) {
                createWindow(dialog, resultMsg);
                return true;
            }

            // Allow the popup and create the appropriate window.
            final AlertDialog.OnClickListener allowListener =
                    new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface d,
                                            int which) {
                            createWindow(dialog, resultMsg);
                        }
                    };

            // Block the popup by returning a null WebView.
            final AlertDialog.OnClickListener blockListener =
                    new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface d, int which) {
                            resultMsg.sendToTarget();
                        }
                    };

            // Build a confirmation dialog to display to the user.
            final AlertDialog d =
                    new AlertDialog.Builder(mContext)
                            .setIconAttribute(android.R.attr.alertDialogIcon)
                            .setMessage(R.string.popup_window_attempt)
                            .setPositiveButton(R.string.allow, allowListener)
                            .setNegativeButton(R.string.block, blockListener)
                            .setCancelable(false)
                            .create();

            // Show the confirmation dialog.
            d.show();
            return true;
        }

        @Override
        public void onRequestFocus(WebView view) {
            if (!mInForeground) {
                mWebViewController.switchToTab(Tab.this);
            }
        }

        @Override
        public void onCloseWindow(WebView window) {
            if (mParent != null) {
                // JavaScript can only close popup window.
                if (mInForeground) {
                    mWebViewController.switchToTab(mParent);
                }
                mWebViewController.closeTab(Tab.this);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            mWebViewController.getTabControl().setActiveTab(Tab.this);
            return false;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            mWebViewController.getTabControl().setActiveTab(Tab.this);
            return false;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            mWebViewController.getTabControl().setActiveTab(Tab.this);
            return false;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mPageLoadProgress = newProgress;
            if (newProgress == 100) {
                mInPageLoad = false;
            }
            mWebViewController.onProgressChanged(Tab.this);
            if (mUpdateThumbnail && newProgress == 100) {
                mUpdateThumbnail = false;
            }
        }

        @Override
        public void onReceivedTitle(WebView view, final String title) {
            mCurrentState.mTitle = title;
            mWebViewController.onReceivedTitle(Tab.this, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            mCurrentState.mFavicon = icon;
            mWebViewController.onFavicon(Tab.this, icon);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            final ContentResolver cr = mContext.getContentResolver();
            // Let precomposed icons take precedence over non-composed
            // icons.
            if (precomposed && mTouchIconLoader != null) {
                mTouchIconLoader.cancel(false);
                mTouchIconLoader = null;
            }
            // Have only one async task at a time.
            if (mTouchIconLoader == null) {
                mTouchIconLoader = new DownloadTouchIcon(Tab.this, cr, view);
                mTouchIconLoader.execute(url);
            }
        }

        @Override
        public void onShowCustomView(View view,
                                     WebChromeClient.CustomViewCallback callback) {
            Activity activity = mWebViewController.getActivity();
            if (activity != null) {
                onShowCustomView(view, activity.getRequestedOrientation(), callback);
            }
        }

        @Override
        public void onShowCustomView(View view, int requestedOrientation,
                                     WebChromeClient.CustomViewCallback callback) {
            if (mInForeground) mWebViewController.showCustomView(Tab.this, view,
                    requestedOrientation, callback);
        }

        @Override
        public void onHideCustomView() {
            if (mInForeground) mWebViewController.hideCustomView();
        }

        /**
         * The origin has exceeded its database quota.
         * @param url the URL that exceeded the quota
         * @param databaseIdentifier the identifier of the database on which the
         *            transaction that caused the quota overflow was run
         * @param currentQuota the current quota for the origin.
         * @param estimatedSize the estimated size of the database.
         * @param totalUsedQuota is the sum of all origins' quota.
         * @param quotaUpdater The callback to run when a decision to allow or
         *            deny quota has been made. Don't forget to call this!
         */
        @Override
        public void onExceededDatabaseQuota(String url,
                                            String databaseIdentifier, long currentQuota, long estimatedSize,
                                            long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
            mSettings.getWebStorageSizeManager()
                    .onExceededDatabaseQuota(url, databaseIdentifier,
                            currentQuota, estimatedSize, totalUsedQuota,
                            quotaUpdater);
        }

        /**
         * The Application Cache has exceeded its max size.
         * @param spaceNeeded is the amount of disk space that would be needed
         *            in order for the last appcache operation to succeed.
         * @param totalUsedQuota is the sum of all origins' quota.
         * @param quotaUpdater A callback to inform the WebCore thread that a
         *            new app cache size is available. This callback must always
         *            be executed at some point to ensure that the sleeping
         *            WebCore thread is woken up.
         */
        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded,
                                             long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
            mSettings.getWebStorageSizeManager()
                    .onReachedMaxAppCacheSize(spaceNeeded, totalUsedQuota,
                            quotaUpdater);
        }

        /**
         * Instructs the browser to show a prompt to ask the user to set the
         * Geolocation permission state for the specified origin.
         * @param origin The origin for which Geolocation permissions are
         *     requested.
         * @param callback The callback to call once the user has set the
         *     Geolocation permission state.
         */
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            if (mInForeground) {
                getGeolocationPermissionsPrompt().show(origin, callback);
            }
        }

        /**
         * Instructs the browser to hide the Geolocation permissions prompt.
         */
        @Override
        public void onGeolocationPermissionsHidePrompt() {
            if (mInForeground && mGeolocationPermissionsPrompt != null) {
                mGeolocationPermissionsPrompt.hide();
            }
        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {
            if (!mInForeground) return;
            getPermissionsPrompt().show(request);
        }

        @Override
        public void onPermissionRequestCanceled(PermissionRequest request) {
            if (mInForeground && mPermissionsPrompt != null) {
                mPermissionsPrompt.hide();
            }
        }

        /* Adds a JavaScript error message to the system log and if the JS
         * console is enabled in the about:debug options, to that console
         * also.
         * @param consoleMessage the message object.
         */
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (mInForeground) {
                // call getErrorConsole(true) so it will create one if needed
                ErrorConsoleView errorConsole = getErrorConsole(true);
                errorConsole.addErrorMessage(consoleMessage);
                if (mWebViewController.shouldShowErrorConsole()
                        && errorConsole.getShowState() !=
                        ErrorConsoleView.SHOW_MAXIMIZED) {
                    errorConsole.showConsole(ErrorConsoleView.SHOW_MINIMIZED);
                }
            }

            // Don't log console messages in private browsing mode
            if (isPrivateBrowsingEnabled()) return true;

            String message = "Console: " + consoleMessage.message() + " "
                    + consoleMessage.sourceId() +  ":"
                    + consoleMessage.lineNumber();

            switch (consoleMessage.messageLevel()) {
                case TIP:
                    Log.d(TAG, message);
                    break;
                case LOG:
                    Log.i(TAG, message);
                    break;
                case WARNING:
                    Log.w(TAG, message);
                    break;
                case ERROR:
                    Log.e(TAG, message);
                    break;
                case DEBUG:
                    Log.d(TAG, message);
                    break;
            }

            return true;
        }

        /**
         * Ask the browser for an icon to represent a <video> element.
         * This icon will be used if the Web page did not specify a poster attribute.
         * @return Bitmap The icon or null if no such icon is available.
         */
        @Override
        public Bitmap getDefaultVideoPoster() {
            if (mInForeground) {
                return mWebViewController.getDefaultVideoPoster();
            }
            return null;
        }

        /**
         * Ask the host application for a custom progress view to show while
         * a <video> is loading.
         * @return View The progress view.
         */
        @Override
        public View getVideoLoadingProgressView() {
            if (mInForeground) {
                return mWebViewController.getVideoLoadingProgressView();
            }
            return null;
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> callback,
                                         FileChooserParams params) {
            if (mInForeground) {
                mWebViewController.showFileChooser(callback, params);
                return true;
            } else {
                return false;
            }
        }

        /**
         * Deliver a list of already-visited URLs
         */
        @Override
        public void getVisitedHistory(final ValueCallback<String[]> callback) {
            mWebViewController.getVisitedHistory(callback);
        }

    };

    // -------------------------------------------------------------------------
    // WebViewClient implementation for the sub window
    // -------------------------------------------------------------------------

    // Subclass of WebViewClient used in subwindows to notify the main
    // WebViewClient of certain WebView activities.
    private static class SubWindowClient extends WebViewClient {
        // The main WebViewClient.
        private final WebViewClient mClient;
        private final WebViewController mController;

        SubWindowClient(WebViewClient client, WebViewController controller) {
            mClient = client;
            mController = controller;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // Unlike the others, do not call mClient's version, which would
            // change the progress bar.  However, we do want to remove the
            // find or select dialog.
            mController.endActionMode();
        }
        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                                           boolean isReload) {
            mClient.doUpdateVisitedHistory(view, url, isReload);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return mClient.shouldOverrideUrlLoading(view, url);
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            mClient.onReceivedSslError(view, handler, error);
        }
        @TargetApi(21)
        @Override
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            mClient.onReceivedClientCertRequest(view, request);
        }
        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                                              HttpAuthHandler handler, String host, String realm) {
            mClient.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        @Override
        public void onFormResubmission(WebView view, Message dontResend,
                                       Message resend) {
            mClient.onFormResubmission(view, dontResend, resend);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            mClient.onReceivedError(view, errorCode, description, failingUrl);
        }
        @Override
        public boolean shouldOverrideKeyEvent(WebView view,
                                              android.view.KeyEvent event) {
            return mClient.shouldOverrideKeyEvent(view, event);
        }
        @Override
        public void onUnhandledKeyEvent(WebView view,
                                        android.view.KeyEvent event) {
            mClient.onUnhandledKeyEvent(view, event);
        }
    }

    // -------------------------------------------------------------------------
    // WebChromeClient implementation for the sub window
    // -------------------------------------------------------------------------

    private class SubWindowChromeClient extends WebChromeClient {
        // The main WebChromeClient.
        private final WebChromeClient mClient;

        SubWindowChromeClient(WebChromeClient client) {
            mClient = client;
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mClient.onProgressChanged(view, newProgress);
        }
        @Override
        public boolean onCreateWindow(WebView view, boolean dialog,
                                      boolean userGesture, android.os.Message resultMsg) {
            return mClient.onCreateWindow(view, dialog, userGesture, resultMsg);
        }
        @Override
        public void onCloseWindow(WebView window) {
            if (window != mSubView) {
                Log.e(TAG, "Can't close the window");
            }
            mWebViewController.dismissSubWindow(Tab.this);
        }
    }

}
