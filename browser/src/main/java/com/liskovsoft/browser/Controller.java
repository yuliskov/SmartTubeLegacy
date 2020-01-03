package com.liskovsoft.browser;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebView;
import androidx.appcompat.view.ActionMode;
import com.liskovsoft.browser.IntentHandler.UrlData;
import com.liskovsoft.browser.UI.ComboViews;
import com.liskovsoft.browser.xwalk.XWalkInitHandler;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Controller implements UiController, WebViewController, ActivityController {
    private static final String TAG = Controller.class.getSimpleName();
    protected final Activity mActivity;
    private final WebViewFactory mFactory;
    private final BrowserSettings mSettings;
    private final TabControl mTabControl;
    private final CrashRecoveryHandler mCrashRecoveryHandler;
    private final IntentHandler mIntentHandler;
    private UI mUi;
    private boolean mActivityPaused = true;
    private WakeLock mWakeLock;
    // A bitmap that is re-used in createScreenshot as scratch space
    private static Bitmap sThumbnailBitmap;
    private Handler mHandler;
    private NetworkStateHandler mNetworkHandler;

    // For select and find, we keep track of the ActionMode so that
    // finish() can be called as desired.
    private ActionMode mActionMode;

    private static final String SEND_APP_ID_EXTRA = "android.speech.extras.SEND_APPLICATION_ID_EXTRA";
    private static final String INCOGNITO_URI = "browser:incognito";

    private final static int WAKELOCK_TIMEOUT = 5 * 60 * 1000; // 5 minutes

    // public message ids
    public final static int LOAD_URL = 1001;
    public final static int STOP_LOAD = 1002;

    // Message Ids
    private static final int FOCUS_NODE_HREF = 102;
    private static final int RELEASE_WAKELOCK = 107;

    static final int UPDATE_BOOKMARK_THUMBNAIL = 108;

    private static final int OPEN_BOOKMARKS = 201;

    private static final int EMPTY_MENU = -1;

    // activity requestCode
    final static int COMBO_VIEW = 1;
    final static int PREFERENCES_PAGE = 3;
    final static int FILE_SELECTED = 4;
    final static int VOICE_RESULT = 6;
    private boolean mLoadStopped;
    private String mVoiceResult;
    private int mOldMenuState;
    private int mMenuState;
    private boolean mMenuIsDown;
    private boolean mShouldShowErrorConsole;
    private List<EventListener> mListeners = new ArrayList<>();
    private Uri mDefaultUrl;
    private Map<String, String> mDefaultHeaders;
    private Intent mIntent;
    private boolean mCancelPendingPause;

    public interface EventListener {
        void onControllerStart();
        void onSaveControllerState(Bundle state);
        void onRestoreControllerState(Bundle state);
        void onTabCreated(Tab tab);
    }

    public Controller(Activity browser) {
        mActivity = browser;
        mSettings = BrowserSettings.getInstance();
        mTabControl = new TabControl(this);
        mCrashRecoveryHandler = CrashRecoveryHandler.initialize(this);
        mFactory = new BrowserWebViewFactory(browser);
        mIntentHandler = new IntentHandler(mActivity, this);

        startHandler();

        mNetworkHandler = new NetworkStateHandler(mActivity, this);

        setupBrowserActivity();
    }

    private void setupBrowserActivity() {
        // we must set theme before call to setContentView
        mActivity.setTheme(R.style.BrowserTheme);
        // no more layout zooming when soft keyboard popups
        mActivity.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void start(final Intent intent) {
        // wait xwalk's initialization
        if (delayStart(intent)) {
            return;
        }

        mFactory.setNextHeaders(mDefaultHeaders);

        onControllerStart();

        try {
            // mCrashRecoverHandler has any previously saved state.
            mCrashRecoveryHandler.startRecovery(intent);
        } catch (Exception e) {
            // User should select Chrome WebView provider in Developer Options
            // Android TV Developer Options: http://corochann.com/how-to-enable-developer-mode-in-android-tv-216.html
            // You can choose your WebView provider by enabling Developer Options and selecting WebView implementation.
            // You can use any compatible Chrome version (Dev, Beta or Stable) that is installed on your device or the standalone Webview APK to act
            // as the WebView implementation.

            // 1) Show message that webview should be uninstalled
            String msg = mActivity.getResources().getString(R.string.select_webview_provider_msg);
            MessageHelpers.showLongMessage(mActivity, msg + "\n" + e.getMessage());

            // 2) Remove webview package with wrong architecture
            Helpers.removePackage(mActivity, "com.google.android.webview");

            // 3) Install webview from ApkMirror:
            // https://www.apkmirror.com/apk/google-inc/android-system-webview/

            Log.d(TAG, e);
            e.printStackTrace();
        }

        mIntent = intent; // store intent for the future call of 'restart'
    }

    public void start(final Intent intent, final Uri url) {
        mDefaultUrl = url;
        start(intent);
    }

    private boolean delayStart(final Intent intent) {
        return XWalkInitHandler.add(new Runnable(){public void run(){start(intent);}});
    }

    public void setUi(UI ui) {
        mUi = ui;
    }

    /**
     * NOTE: entry point!
     * Actually here restoration process begins.
     * @param icicle
     * @param intent
     */
    public void doStart(Bundle icicle, Intent intent) {
        // NOTE: restore callback is broken
        // onRestoreControllerState(icicle);

        long currentTabId = mTabControl.canRestoreState(icicle, false);
        if (currentTabId == -1) { // no saved state
            Log.i(TAG, "Browser state not found. Opening home page...");
            openTabToHomePage();
        } else {
            Log.d(TAG, "Restoring browser state...");
            boolean needsRestoreAllTabs = mUi.needsRestoreAllTabs();
            // TODO: restore incognito tabs not implemented
            mTabControl.restoreState(icicle, currentTabId, false, needsRestoreAllTabs);
            List<Tab> tabs = mTabControl.getTabs();

            mUi.updateTabs(tabs);
            // TabControl.restoreState() will create a new tab even if
            // restoring the state fails.
            setActiveTab(mTabControl.getCurrentTab());
        }

        // Intent is non-null when framework thinks the browser should be
        // launching with a new intent (icicle is null).
        if (intent != null) {
            mIntentHandler.onNewIntent(intent);
        }
    }

    public Tab openTabToHomePage() {

        if (mDefaultUrl != null)
            return loadUrl(mDefaultUrl.toString(), mDefaultHeaders);

        return openTab(mSettings.getHomePage(), false, true, false);
    }

    @Override
    public Tab openIncognitoTab() {
        return openTab(INCOGNITO_URI, true, true, false);
    }

    // open a non incognito tab with the given url data
    // and set as active tab
    public Tab openTab(UrlData urlData) {
        Tab tab = showPreloadedTab(urlData);
        if (tab == null) {
            tab = createNewTab(false, true, true);
            if ((tab != null) && !urlData.isEmpty()) {
                loadUrlDataIn(tab, urlData);
            }
        }
        return tab;
    }

    @Override
    public Tab openTab(String url, boolean incognito, boolean setActive, boolean useCurrent) {
        return openTab(url, incognito, setActive, useCurrent, null);
    }

    @Override
    public Tab openTab(String url, Tab parent, boolean setActive, boolean useCurrent) {
        return openTab(url, (parent != null) && parent.isPrivateBrowsingEnabled(), setActive, useCurrent, parent);
    }

    public Tab openTab(String url, boolean incognito, boolean setActive, boolean useCurrent, Tab parent) {
        Tab tab = createNewTab(incognito, setActive, useCurrent);
        if (tab != null) {
            if (parent != null && parent != tab) {
                parent.addChildTab(tab);
            }
            if (url != null) {
                loadUrl(tab, url);
            }
        }
        return tab;
    }

    /**
     * Load the URL into the given WebView and update the title bar
     * to reflect the new load.  Call this instead of WebView.loadUrl
     * directly.
     *
     * @param tab The WebView used to load url.
     * @param url The URL to load.
     */
    @Override
    public void loadUrl(Tab tab, String url) {
        loadUrl(tab, url, null);
    }

    /**
     * Use this method to control browser from outer world.
     *
     * @param url     Full site address.
     * @return Info about loaded page.
     */
    public Tab loadUrl(String url) {
        return loadUrl(url, null);
    }

    /**
     * Use this method to control browser from outer world.
     *
     * @param url     Full site address.
     * @param headers Can be null.
     * @return Info about loaded page.
     */
    public Tab loadUrl(String url, Map<String, String> headers) {
        return load(url, headers);
    }

    protected void loadUrl(Tab tab, String url, Map<String, String> headers) {
        if (tab != null) {
            tab.loadUrl(url, headers);
            mUi.onProgressChanged(tab);
        }
    }

    /**
     * Use this method to control browser from outer world.
     *
     * @param url     Full site address.
     * @param headers Can be null.
     * @return Info about loaded page.
     */
    public Tab load(String url, Map<String, String> headers) {
        if (delayLoad(url, headers)) {
            return null;
        }

        mFactory.setNextHeaders(headers);

        Tab tab = createNewTab(false, true, false);

        loadUrl(tab, url, headers);
        return tab;
    }

    private boolean delayLoad(final String url, final Map<String, String> headers) {
        return XWalkInitHandler.add(new Runnable(){public void run(){load(url, headers);}});
    }

    private Map<String, String> convertToCaseInsensitiveMap(Map<String, String> headers) {
        if (headers == null) return null;
        Map<String, String> nodeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        nodeMap.putAll(headers);
        return nodeMap;
    }

    @Override
    public TabControl getTabControl() {
        return mTabControl;
    }

    /********************** TODO: UI stuff *****************************/

    // these methods have been copied, they still need to be cleaned up

    /****************** tabs ***************************************************/

    // basic tab interactions:

    // it is assumed that tabcontrol already knows about the tab
    protected void addTab(Tab tab) {
        mUi.addTab(tab);
    }

    protected void removeTab(Tab tab) {
        mUi.removeTab(tab);
        mTabControl.removeTab(tab);
        mCrashRecoveryHandler.backupState();
    }

    private Tab createNewTab(boolean incognito, boolean setActive, boolean useCurrent) {
        Tab tab = null;
        if (useCurrent) {
            tab = mTabControl.getCurrentTab();
        }

        if (tab == null) {
            tab = mTabControl.createNewTab(incognito);
            addTab(tab);
        }

        if (setActive) {
            setActiveTab(tab);
        }

        return tab;
    }

    /**
     * Adds WebView associated with this tab to the root of the activity.
     *
     * @param tab tab that created in previous createNewTab method
     */
    @Override
    public void setActiveTab(Tab tab) {
        // monkey protection against delayed start
        if (tab != null) {
            mTabControl.setCurrentTab(tab);
            // the tab is guaranteed to have a webview after setCurrentTab
            mUi.setActiveTab(tab);
        }
    }

    @Override
    public WebViewFactory getWebViewFactory() {
        return mFactory;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public List<Tab> getTabs() {
        return mTabControl.getTabs();
    }

    public CrashRecoveryHandler getCrashRecoveryHandler() {
        return mCrashRecoveryHandler;
    }

    @Override
    public void removeSubWindow(Tab t) {
        if (t.getSubWebView() != null) {
            mUi.removeSubWindow(t.getSubViewContainer());
        }
    }

    @Override
    public void onSetWebView(Tab tab, WebView view) {
        mUi.onSetWebView(tab, view);
    }

    @Override
    public void createSubWindow(Tab tab) {

    }

    @Override
    public UI getUi() {
        return null;
    }

    @Override
    public WebView getCurrentWebView() {
        return mTabControl.getCurrentWebView();
    }

    // Helper method for getting the top window.
    @Override
    public WebView getCurrentTopWebView() {
        return mTabControl.getCurrentTopWebView();
    }

    @Override
    public Tab getCurrentTab() {
        return mTabControl.getCurrentTab();
    }

    // Remove the sub window if it exists. Also called by TabControl when the
    // user clicks the 'X' to dismiss a sub window.
    @Override
    public void dismissSubWindow(Tab tab) {
        removeSubWindow(tab);
        // dismiss the subwindow. This will destroy the WebView.
        tab.dismissSubWindow();
        WebView wv = getCurrentTopWebView();
        if (wv != null) {
            wv.requestFocus();
        }
    }

    /**
     * True if a custom ActionMode (i.e. find or select) is in use.
     */
    @Override
    public boolean isInCustomActionMode() {
        return mActionMode != null;
    }

    /**
     * End the current ActionMode.
     */
    @Override
    public void endActionMode() {
        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    @Override
    public void showAutoLogin(Tab tab) {
        if (!tab.inForeground()) Log.e(TAG, "Tab not in foreground");

        // Update the title bar to show the auto-login request.
        mUi.showAutoLogin(tab);
    }



    /**
     * TODO: bunch of non implemented methods
     */

    @Override
    public void setBlockEvents(boolean block) {

    }

    @Override
    public void onProgressChanged(Tab tab) {

    }

    @Override
    public void onPageStarted(Tab tab, Bitmap favicon) {
        
    }

    @Override
    public void onPageFinished(Tab tab, String url) {

    }

    @Override
    public void onReceivedTitle(Tab tab, String title) {

    }

    @Override
    public void onFavicon(Tab tab, Bitmap icon) {

    }

    @Override
    public boolean shouldOverrideUrlLoading(Tab tab, String url) {
        return false;
    }

    @Override
    public boolean shouldOverrideKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean onUnhandledKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public void doUpdateVisitedHistory(Tab tab, boolean isReload) {

    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {

    }

    @Override
    public void onReceivedHttpAuthRequest(Tab tab, WebView view, HttpAuthHandler handler, String host, String realm) {

    }

    @Override
    public void onDownloadStart(Tab tab, String url, String useragent, String contentDisposition, String mimeType, String referer, long
            contentLength) {

    }

    @Override
    public void showCustomView(Tab tab, View view, int requestedOrientation, CustomViewCallback callback) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onConfgurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return false;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {

    }

    @Override
    public void onContextMenuClosed(Menu menu) {

    }

    @Override
    public void onUpdatedSecurityState(Tab tab) {

    }

    @Override
    public void showFileChooser(ValueCallback<Uri[]> callback, FileChooserParams params) {

    }

    @Override
    public void updateMenuState(Tab tab, Menu menu) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        return null;
    }

    @Override
    public View getVideoLoadingProgressView() {
        return null;
    }

    @Override
    public void showSslCertificateOnError(WebView view, SslErrorHandler handler, SslError error) {

    }

    @Override
    public void onUserCanceledSsl(Tab tab) {

    }

    @Override
    public void attachSubWindow(Tab tab) {

    }

    @Override
    public void bookmarkCurrentPage() {

    }

    @Override
    public void editUrl() {

    }

    @Override
    public void showPageInfo() {

    }

    @Override
    public void openPreferences() {

    }

    @Override
    public void findOnPage() {

    }

    @Override
    public void toggleUserAgent() {

    }



    @Override
    public boolean supportsVoice() {
        return false;
    }

    @Override
    public void startVoiceRecognizer() {

    }

    /**
     * TODO: end bunch of non implemented methods
     */

    @Override
    public void handleNewIntent(Intent intent) {
        if (!mUi.isWebShowing()) {
            mUi.showWeb(false);
        }
        mIntentHandler.onNewIntent(intent);
    }


    @Override
    public BrowserSettings getSettings() {
        return mSettings;
    }

    @Override
    public boolean shouldShowErrorConsole() {
        return mShouldShowErrorConsole;
    }

    @Override
    public void bookmarkedStatusHasChanged(Tab tab) {
        // TODO: Switch to using onTabDataChanged after b/3262950 is fixed
        mUi.bookmarkedStatusHasChanged(tab);
    }

    @Override
    public void hideAutoLogin(Tab tab) {
        if (!tab.inForeground()) Log.e(TAG, "Tab not in foreground");

        mUi.hideAutoLogin(tab);
    }

    @Override
    public boolean shouldCaptureThumbnails() {
        return false;
    }

    @Override
    public void stopLoading() {
        mLoadStopped = true;
        Tab tab = mTabControl.getCurrentTab();
        WebView w = getCurrentTopWebView();
        if (w != null) {
            w.stopLoading();
            mUi.onPageStopped(tab);
        }
    }

    @Override
    public Intent createBookmarkCurrentPageIntent(boolean canBeAnEdit) {
        return null;
    }

    private Tab showPreloadedTab(final UrlData urlData) {
        if (!urlData.isPreloaded()) {
            return null;
        }
        final PreloadedTabControl tabControl = urlData.getPreloadedTab();
        final String sbQuery = urlData.getSearchBoxQueryToSubmit();
        if (sbQuery != null) {
            if (!tabControl.searchBoxSubmit(sbQuery, urlData.mUrl, urlData.mHeaders)) {
                // Could not submit query. Fallback to regular tab creation
                tabControl.destroy();
                return null;
            }
        }
        // check tab count and make room for new tab
        if (!mTabControl.canCreateNewTab()) {
            Tab leastUsed = mTabControl.getLeastUsedTab(getCurrentTab());
            if (leastUsed != null) {
                closeTab(leastUsed);
            }
        }
        Tab t = tabControl.getTab();
        t.refreshIdAfterPreload();
        mTabControl.addPreloadedTab(t);
        addTab(t);
        setActiveTab(t);
        return t;
    }

    /**
     * Load UrlData into a Tab and update the title bar to reflect the new
     * load.  Call this instead of UrlData.loadIn directly.
     *
     * @param t    The Tab used to load.
     * @param data The UrlData being loaded.
     */
    protected void loadUrlDataIn(Tab t, UrlData data) {
        if (data != null) {
            if (data.isPreloaded()) {
                // this isn't called for preloaded tabs
            } else {
                if (t != null && data.mDisableUrlOverride) {
                    t.disableUrlOverridingForLoad();
                }
                loadUrl(t, data.mUrl, data.mHeaders);
            }
        }
    }

    @Override
    public void shareCurrentPage() {
        shareCurrentPage(mTabControl.getCurrentTab());
    }

    private void shareCurrentPage(Tab tab) {
        if (tab != null) {
            sharePage(mActivity, tab.getTitle(), tab.getUrl(), tab.getFavicon(), createScreenshot(tab.getWebView(), getDesiredThumbnailWidth
                    (mActivity), getDesiredThumbnailHeight(mActivity)));
        }
    }

    static Bitmap createScreenshot(WebView view, int width, int height) {
        // TODO: not implemented
        return null;
    }

    /**
     * Share a page, providing the title, url, favicon, and a screenshot.  Uses
     * an {@link Intent} to launch the Activity chooser.
     *
     * @param c          Context used to launch a new Activity.
     * @param title      Title of the page.  Stored in the Intent with
     *                   {@link Intent#EXTRA_SUBJECT}
     * @param url        URL of the page.  Stored in the Intent with
     *                   {@link Intent#EXTRA_TEXT}
     * @param favicon    Bitmap of the favicon for the page.  Stored in the Intent
     *                   with {@link Browser#EXTRA_SHARE_FAVICON}
     * @param screenshot Bitmap of a screenshot of the page.  Stored in the
     *                   Intent with {@link Browser#EXTRA_SHARE_SCREENSHOT}
     */
    static final void sharePage(Context c, String title, String url, Bitmap favicon, Bitmap screenshot) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, url);
        send.putExtra(Intent.EXTRA_SUBJECT, title);
        send.putExtra(Browser.EXTRA_SHARE_FAVICON, favicon);
        send.putExtra(Browser.EXTRA_SHARE_SCREENSHOT, screenshot);
        try {
            c.startActivity(Intent.createChooser(send, c.getString(R.string.choosertitle_sharevia)));
        } catch (android.content.ActivityNotFoundException ex) {
            // if no app handles it, do nothing
        }
    }

    private void copy(CharSequence text) {
        ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }

    boolean didUserStopLoading() {
        return mLoadStopped;
    }

    private void pauseWebViewTimersWithDelay(final Tab tab) {
        if (pauseWebViewTimers(tab)) {
            return;
        }

        int checkTimeout = 1000;
        mCancelPendingPause = false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCancelPendingPause) {
                    return;
                }

                if (!pauseWebViewTimers(tab)) {
                    if (mWakeLock == null) {
                        PowerManager pm = (PowerManager) mActivity.getSystemService(Context.POWER_SERVICE);
                        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Browser:Controller");
                    }
                    mWakeLock.acquire();
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(RELEASE_WAKELOCK), WAKELOCK_TIMEOUT);
                }
            }
        }, checkTimeout);
    }

    /**
     * Pause all WebView timers using the WebView of the given tab
     *
     * @param tab
     * @return true if the timers are paused or tab is null
     */
    private boolean pauseWebViewTimers(Tab tab) {
        if (tab == null) {
            return true;
        }

        if (!tab.inPageLoad()) {
            CookieSyncManager.getInstance().stopSync();
            WebViewTimersControl.getInstance().onBrowserActivityPause(getCurrentWebView());
            return true;
        }

        return false;
    }

    ///**
    // * resume all WebView timers using the WebView instance of the given tab
    // *
    // * @param tab guaranteed non-null
    // */
    //private void resumeWebViewTimers(Tab tab) {
    //    if (tab == null) {
    //        return;
    //    }
    //
    //    boolean inLoad = tab.inPageLoad();
    //    if ((!mActivityPaused && !inLoad) || (mActivityPaused && inLoad)) {
    //        mCancelPendingPause = true;
    //        CookieSyncManager.getInstance().startSync();
    //        WebView w = tab.getWebView();
    //        WebViewTimersControl.getInstance().onBrowserActivityResume(w);
    //    }
    //
    //    return;
    //}

    /**
     * resume all WebView timers using the WebView instance of the given tab
     *
     * @param tab guaranteed non-null
     */
    private void resumeWebViewTimers(Tab tab) {
        if (tab == null) {
            return;
        }

        mCancelPendingPause = true;
        CookieSyncManager.getInstance().startSync();
        WebView w = tab.getWebView();
        WebViewTimersControl.getInstance().onBrowserActivityResume(w);

        return;
    }

    // Called when loading from context menu or LOAD_URL message
    protected void loadUrlFromContext(String url) {
        Tab tab = getCurrentTab();
        WebView view = tab != null ? tab.getWebView() : null;
        // In case the user enters nothing.
        if (url != null && url.length() != 0 && tab != null && view != null) {
            url = UrlUtils.smartUrlFilter(url);
            if (!((BrowserWebView) view).getWebViewClient().
                    shouldOverrideUrlLoading(view, url)) {
                loadUrl(tab, url);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save all the tabs
        Bundle saveState = createSaveState();

        onSaveControllerState(saveState);

        // crash recovery manages all save & restore state
        mCrashRecoveryHandler.writeState(saveState);
        mSettings.setLastRunPaused(true);
    }

    /**
     * Save the current state to outState. Does not write the state to
     * disk.
     *
     * @return Bundle containing the current state of all tabs.
     */
    Bundle createSaveState() {
        Bundle saveState = new Bundle();
        mTabControl.saveState(saveState);
        if (!saveState.isEmpty()) {
            // Save time so that we know how old incognito tabs (if any) are.
            saveState.putSerializable("lastActiveDate", Calendar.getInstance());
        }
        return saveState;
    }

    protected void reuseTab(Tab appTab, UrlData urlData) {
        // Dismiss the subwindow if applicable.
        dismissSubWindow(appTab);

        // Since we might kill the WebView, remove it from the
        // content view first.
        mUi.detachTab(appTab);

        // Recreate the main WebView after destroying the old one.
        mTabControl.recreateWebView(appTab);

        // TODO: analyze why the remove and add are necessary
        mUi.attachTab(appTab);

        if (mTabControl.getCurrentTab() != appTab) {
            switchToTab(appTab);
            loadUrlDataIn(appTab, urlData);
        } else {
            // If the tab was the current tab, we have to attach
            // it to the view system again.
            setActiveTab(appTab);
            loadUrlDataIn(appTab, urlData);
        }
    }

    protected void pageUp() {
        getCurrentTopWebView().pageUp(false);
    }

    protected void pageDown() {
        getCurrentTopWebView().pageDown(false);
    }

    @Override
    public void onPause() {
        // TODO: CustomView (what????)
        if (mUi.isCustomViewShowing()) {
            hideCustomView();
        }
        if (mActivityPaused) {
            Log.e(TAG, "BrowserActivity is already paused.");
            return;
        }
        mActivityPaused = true;
        Tab tab = mTabControl.getCurrentTab();
        if (tab != null) {
            tab.pause();
            pauseWebViewTimersWithDelay(tab);
        }
        mUi.onPause();
        mNetworkHandler.onPause();

        if (sThumbnailBitmap != null) {
            sThumbnailBitmap.recycle();
            sThumbnailBitmap = null;
        }
    }

    @Override
    public void onResume() {
        if (!mActivityPaused) {
            Log.e(TAG, "BrowserActivity is already resumed.");
            return;
        }
        mSettings.setLastRunPaused(false);
        mActivityPaused = false;
        Tab current = mTabControl.getCurrentTab();
        if (current != null) {
            current.resume();
            resumeWebViewTimers(current);
        }
        releaseWakeLock();

        mUi.onResume();
        mNetworkHandler.onResume();

        if (mVoiceResult != null) {
            mUi.onVoiceResult(mVoiceResult);
            mVoiceResult = null;
        }
    }

    private void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mHandler.removeMessages(RELEASE_WAKELOCK);
            mWakeLock.release();
        }
    }

    @Override
    public void hideCustomView() {
        if (mUi.isCustomViewShowing()) {
            mUi.onHideCustomView();
            // Reset the old menu state.
            mMenuState = mOldMenuState;
            mOldMenuState = EMPTY_MENU;
            mActivity.invalidateOptionsMenu();
        }
    }

    /**
     * Open the Go page.
     *
     * @param startView Tab to open. Available tabs: history, bookmarks or snapshots.
     */
    @Override
    public void bookmarksOrHistoryPicker(ComboViews startView) {
        if (mTabControl.getCurrentWebView() == null) {
            return;
        }
        // clear action mode
        if (isInCustomActionMode()) {
            endActionMode();
        }
        Bundle extras = new Bundle();
        // Disable opening in a new window if we have maxed out the windows
        extras.putBoolean(BrowserBookmarksPage.EXTRA_DISABLE_WINDOW, !mTabControl.canCreateNewTab());
        mUi.showComboView(startView, extras);
    }

    /**
     * helper method for key handler
     * returns the current tab if it can't advance
     */
    private Tab getNextTab() {
        int pos = mTabControl.getCurrentPosition() + 1;
        if (pos >= mTabControl.getTabCount()) {
            pos = 0;
        }
        return mTabControl.getTab(pos);
    }

    /**
     * helper method for key handler
     * returns the current tab if it can't advance
     */
    private Tab getPrevTab() {
        int pos = mTabControl.getCurrentPosition() - 1;
        if (pos < 0) {
            pos = mTabControl.getTabCount() - 1;
        }
        return mTabControl.getTab(pos);
    }

    boolean isMenuOrCtrlKey(int keyCode) {
        return (KeyEvent.KEYCODE_MENU == keyCode) || (KeyEvent.KEYCODE_CTRL_LEFT == keyCode) || (KeyEvent.KEYCODE_CTRL_RIGHT == keyCode);
    }

    // combo view callbacks

    // key handling
    protected void onBackKey() {
        if (!mUi.onBackKey()) {
            WebView subwindow = mTabControl.getCurrentSubWindow();
            if (subwindow != null) {
                if (subwindow.canGoBack()) {
                    subwindow.goBack();
                } else {
                    dismissSubWindow(mTabControl.getCurrentTab());
                }
            } else {
                goBackOnePageOrQuit();
            }
        }
    }

    protected boolean onMenuKey() {
        return mUi.onMenuKey();
    }

    /**
     * @param tab the tab to switch to
     * @return boolean True if we successfully switched to a different tab.  If
     * the indexth tab is null, or if that tab is the same as
     * the current one, return false.
     */
    @Override
    public boolean switchToTab(Tab tab) {
        Tab currentTab = mTabControl.getCurrentTab();
        if (tab == null || tab == currentTab) {
            return false;
        }
        setActiveTab(tab);
        return true;
    }

    @Override
    public void closeCurrentTab() {
        closeCurrentTab(false);
    }

    protected void closeCurrentTab(boolean andQuit) {
        if (mTabControl.getTabCount() == 1) {
            mCrashRecoveryHandler.clearState();
            mTabControl.removeTab(getCurrentTab());
            mActivity.finish();
            return;
        }
        final Tab current = mTabControl.getCurrentTab();
        final int pos = mTabControl.getCurrentPosition();
        Tab newTab = current.getParent();
        if (newTab == null) {
            newTab = mTabControl.getTab(pos + 1);
            if (newTab == null) {
                newTab = mTabControl.getTab(pos - 1);
            }
        }
        if (andQuit) {
            mTabControl.setCurrentTab(newTab);
            closeTab(current);
        } else if (switchToTab(newTab)) {
            // Close window
            closeTab(current);
        }
    }

    /**
     * Close the tab, remove its associated title bar, and adjust mTabControl's
     * current tab to a valid value.
     */
    @Override
    public void closeTab(Tab tab) {
        if (tab == mTabControl.getCurrentTab()) {
            closeCurrentTab();
        } else {
            removeTab(tab);
        }
    }

    @Override
    public void closeOtherTabs() {

    }

    private void updateScreenshot(Tab tab) {
        // TODO: not implemented
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    // thumbnails

    /**
     * Return the desired width for thumbnail screenshots, which are stored in
     * the database, and used on the bookmarks screen.
     *
     * @param context Context for finding out the density of the screen.
     * @return desired width for thumbnail screenshot.
     */
    static int getDesiredThumbnailWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.bookmarkThumbnailWidth);
    }

    /**
     * Return the desired height for thumbnail screenshots, which are stored in
     * the database, and used on the bookmarks screen.
     *
     * @param context Context for finding out the density of the screen.
     * @return desired height for thumbnail screenshot.
     */
    static int getDesiredThumbnailHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.bookmarkThumbnailHeight);
    }

    void goBackOnePageOrQuit() {
        Tab current = mTabControl.getCurrentTab();
        if (current == null) {
            /*
             * Instead of finishing the activity, simply push this to the back
             * of the stack and let ActivityManager to choose the foreground
             * activity. As BrowserActivity is singleTask, it will be always the
             * root of the task. So we can use either true or false for
             * moveTaskToBack().
             */
            mActivity.moveTaskToBack(true);
            return;
        }
        if (current.canGoBack()) {
            current.goBack();
        } else {
            // Check to see if we are closing a window that was created by
            // another window. If so, we switch back to that window.
            Tab parent = current.getParent();
            if (parent != null) {
                switchToTab(parent);
                // Now we close the other tab
                closeTab(current);
            } else {
                if ((current.getAppId() != null) || current.closeOnBack()) {
                    closeCurrentTab(true);
                }
                /*
                 * Instead of finishing the activity, simply push this to the back
                 * of the stack and let ActivityManager to choose the foreground
                 * activity. As BrowserActivity is singleTask, it will be always the
                 * root of the task. So we can use either true or false for
                 * moveTaskToBack().
                 */
                mActivity.moveTaskToBack(true);
            }
        }
    }

    /**
     * handle key events in browser
     *
     * @param keyCode
     * @param event
     * @return true if handled, false to pass to super
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean noModifiers = event.hasNoModifiers();
        // Even if MENU is already held down, we need to call to super to open
        // the IME on long press.
        if (!noModifiers && isMenuOrCtrlKey(keyCode)) {
            mMenuIsDown = true;
            return false;
        }

        WebView webView = getCurrentTopWebView();
        Tab tab = getCurrentTab();
        if (webView == null || tab == null) return false;

        boolean ctrl = event.hasModifiers(KeyEvent.META_CTRL_ON);
        boolean shift = event.hasModifiers(KeyEvent.META_SHIFT_ON);

        switch (keyCode) {
            case KeyEvent.KEYCODE_TAB:
                if (event.isCtrlPressed()) {
                    if (event.isShiftPressed()) {
                        // prev tab
                        switchToTab(getPrevTab());
                    } else {
                        // next tab
                        switchToTab(getNextTab());
                    }
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_SPACE:
                // WebView/WebTextView handle the keys in the KeyDown. As
                // the Activity's shortcut keys are only handled when WebView
                // doesn't, have to do it in onKeyDown instead of onKeyUp.
                if (shift) {
                    pageUp();
                } else if (noModifiers) {
                    pageDown();
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                if (!noModifiers) break;
                event.startTracking();
                return true;
            case KeyEvent.KEYCODE_FORWARD:
                if (!noModifiers) break;
                tab.goForward();
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (ctrl) {
                    tab.goBack();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (ctrl) {
                    tab.goForward();
                    return true;
                }
                break;
            //          case KeyEvent.KEYCODE_B:    // menu
            //          case KeyEvent.KEYCODE_D:    // menu
            //          case KeyEvent.KEYCODE_E:    // in Chrome: puts '?' in URL bar
            //          case KeyEvent.KEYCODE_F:    // menu
            //          case KeyEvent.KEYCODE_G:    // in Chrome: finds next match
            //          case KeyEvent.KEYCODE_H:    // menu
            //          case KeyEvent.KEYCODE_I:    // unused
            //          case KeyEvent.KEYCODE_J:    // menu
            //          case KeyEvent.KEYCODE_K:    // in Chrome: puts '?' in URL bar
            //          case KeyEvent.KEYCODE_L:    // menu
            //          case KeyEvent.KEYCODE_M:    // unused
            //          case KeyEvent.KEYCODE_N:    // in Chrome: new window
            //          case KeyEvent.KEYCODE_O:    // in Chrome: open file
            //          case KeyEvent.KEYCODE_P:    // in Chrome: print page
            //          case KeyEvent.KEYCODE_Q:    // unused
            //          case KeyEvent.KEYCODE_R:
            //          case KeyEvent.KEYCODE_S:    // in Chrome: saves page
            case KeyEvent.KEYCODE_T:
                // we can't use the ctrl/shift flags, they check for
                // exclusive use of a modifier
                if (event.isCtrlPressed()) {
                    if (event.isShiftPressed()) {
                        openIncognitoTab();
                    } else {
                        openTabToHomePage();
                    }
                    return true;
                }
                break;
            //          case KeyEvent.KEYCODE_U:    // in Chrome: opens source of page
            //          case KeyEvent.KEYCODE_V:    // text view intercepts to paste
            //          case KeyEvent.KEYCODE_W:    // menu
            //          case KeyEvent.KEYCODE_X:    // text view intercepts to cut
            //          case KeyEvent.KEYCODE_Y:    // unused
            //          case KeyEvent.KEYCODE_Z:    // unused
        }
        // it is a regular key and webview is not null
        boolean ret = mUi.dispatchKey(keyCode, event);
        return ret;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mUi.isWebShowing()) {
                    bookmarksOrHistoryPicker(ComboViews.History);
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isMenuOrCtrlKey(keyCode)) {
            mMenuIsDown = false;
            if (KeyEvent.KEYCODE_MENU == keyCode && event.isTracking() && !event.isCanceled()) {
                return onMenuKey();
            }
        }
        if (!event.hasNoModifiers()) return false;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (event.isTracking() && !event.isCanceled()) {
                    onBackKey();
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public void onActionModeStarted(android.view.ActionMode mode) {

    }

    @Override
    public void onActionModeFinished(android.view.ActionMode mode) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        return false;
    }

    /**
     * Entry point for all browser commands.
     */
    private void startHandler() {
        mHandler = new MessageHandler();
    }

    // My Custom Methods

    @Override
    public void setListener(EventListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void onControllerStart() {
        for (EventListener l : mListeners) {
            l.onControllerStart();
        }
    }

    @Override
    public void onSaveControllerState(Bundle state) {
        for (EventListener l : mListeners) {
            l.onSaveControllerState(state);
        }
    }

    @Override
    public void onRestoreControllerState(Bundle state) {
        for (EventListener l : mListeners) {
            l.onRestoreControllerState(state);
        }
    }

    @Override
    public void setDefaultUrl(Uri url){
         mDefaultUrl = url;
    }

    @Override
    public void setDefaultHeaders(Map<String, String> headers){
         mDefaultHeaders = headers;
    }

    @Override
    public Map<String,String> getDefaultHeaders() {
        return mDefaultHeaders;
    }

    @Override
    public void onTabCreated(Tab tab) {
        for (EventListener l : mListeners) {
            l.onTabCreated(tab);
        }
    }

    @Override
    public void showSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(getCurrentTab().getWebView(), InputMethodManager.SHOW_FORCED);
    }

    // End My Custom Methods

    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OPEN_BOOKMARKS:
                    bookmarksOrHistoryPicker(ComboViews.Bookmarks);
                    break;
                case FOCUS_NODE_HREF: {
                    String url = (String) msg.getData().get("url");
                    String title = (String) msg.getData().get("title");
                    String src = (String) msg.getData().get("src");
                    if (url == "") url = src; // use image if no anchor
                    if (TextUtils.isEmpty(url)) {
                        break;
                    }
                    HashMap focusNodeMap = (HashMap) msg.obj;
                    WebView view = (WebView) focusNodeMap.get("webview");
                    // Only apply the action if the top window did not change.
                    if (getCurrentTopWebView() != view) {
                        break;
                    }

                    // switch no longer work with non-constant vars
                    if (msg.arg1 == R.id.open_context_menu_id) {
                        loadUrlFromContext(url);

                    } else if (msg.arg1 == R.id.view_image_context_menu_id) {
                        loadUrlFromContext(src);

                    } else if (msg.arg1 == R.id.open_newtab_context_menu_id) {
                        final Tab parent = mTabControl.getCurrentTab();
                        openTab(url, parent, !mSettings.openInBackground(), true);

                    } else if (msg.arg1 == R.id.copy_link_context_menu_id) {
                        copy(url);

                    } else if (msg.arg1 == R.id.save_link_context_menu_id || msg.arg1 == R.id.download_context_menu_id) {
                        DownloadHandler.onDownloadStartNoStream(mActivity, url, view.getSettings().getUserAgentString(), null, null, null, view
                                .isPrivateBrowsingEnabled());

                    }
                    break;
                }

                case LOAD_URL:
                    loadUrlFromContext((String) msg.obj);
                    break;

                case STOP_LOAD:
                    stopLoading();
                    break;

                case RELEASE_WAKELOCK:
                    if (mWakeLock != null && mWakeLock.isHeld()) {
                        mWakeLock.release();
                        // if we reach here, Browser should be still in the
                        // background loading after WAKELOCK_TIMEOUT (5-min).
                        // To avoid burning the battery, stop loading.
                        mTabControl.stopAllLoading();
                    }
                    break;

                case UPDATE_BOOKMARK_THUMBNAIL:
                    Tab tab = (Tab) msg.obj;
                    if (tab != null) {
                        updateScreenshot(tab);
                    }
                    break;
            }
        }
    }
}
