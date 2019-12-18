package com.liskovsoft.browser;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import com.liskovsoft.browser.search.SearchEngine;
import com.liskovsoft.browser.search.SearchEngines;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.WeakHashMap;

public class BrowserSettings implements OnSharedPreferenceChangeListener, PreferenceKeys {
    private static BrowserSettings sInstance;
    private static boolean sInitialized;
    private static String sFactoryResetUrl;
    private final Context mContext;
    private final SharedPreferences mPrefs;
    private final LinkedList<WeakReference<WebSettings>> mManagedSettings;
    private final WeakHashMap<WebSettings, String> mCustomUserAgents;
    private WebStorageSizeManager mWebStorageSizeManager;
    private float mFontSizeMult;
    private String mAppCachePath;
    private boolean mNeedsSharedSync;

    // TODO: Update UserAgent stuff to more actual versions
    private static final String LG_SMART_TV_VOICE_BUTTON_ENABLED = "Mozilla/5.0 (Unknown; Linux armv7l) " +
            "AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; " +
            "43LK5760PTA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 43LK5760PTA, wired)";

    private static final String DESKTOP_USERAGENT = "Mozilla/5.0 (X11; " +
            "Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) " +
            "Chrome/11.0.696.34 Safari/534.24";

    private static final String IPHONE_USERAGENT = "Mozilla/5.0 (iPhone; U; " +
            "CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 " +
            "(KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7";

    private static final String IPAD_USERAGENT = "Mozilla/5.0 (iPad; U; " +
            "CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 " +
            "(KHTML, like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10";

    private static final String FROYO_USERAGENT = "Mozilla/5.0 (Linux; U; " +
            "Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 " +
            "(KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

    private static final String HONEYCOMB_USERAGENT = "Mozilla/5.0 (Linux; U; " +
            "Android 3.1; en-us; Xoom Build/HMJ25) AppleWebKit/534.13 " +
            "(KHTML, like Gecko) Version/4.0 Safari/534.13";

    private static final String USER_AGENTS[] = {
            null,
            DESKTOP_USERAGENT,
            IPHONE_USERAGENT,
            IPAD_USERAGENT,
            FROYO_USERAGENT,
            HONEYCOMB_USERAGENT,
    };

    //private static final String USER_AGENTS[] = { null,
    //        DESKTOP_USERAGENT,
    //        IPHONE_USERAGENT,
    //        IPAD_USERAGENT,
    //        FROYO_USERAGENT,
    //        HONEYCOMB_USERAGENT,
    //};
    // Current state of network-dependent settings
    private boolean mLinkPrefetchAllowed = false;
    private SearchEngine mSearchEngine;

    public static void initialize(final Context context) {
        sInstance = new BrowserSettings(context);
    }

    private BrowserSettings(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mManagedSettings = new LinkedList<>();
        mCustomUserAgents = new WeakHashMap<>();
        BackgroundHandler.execute(mSetup);
    }

    public static BrowserSettings getInstance() {
        return sInstance;
    }

    // -----------------------------
    // getter/setters for general_preferences.xml
    // -----------------------------

    private boolean getPlaybackRequiresGesture() {
        return mPrefs.getBoolean(PREF_PLAYBACK_REQUIRES_GESTURE, false);
    }

    public String getHomePage() {
        return mPrefs.getString(PREF_HOMEPAGE, getFactoryResetHomeUrl(mContext));
    }

    public void setHomePage(String value) {
        mPrefs.edit().putString(PREF_HOMEPAGE, value).apply();
    }

    public boolean useFullscreen() {
        return mPrefs.getBoolean(PREF_FULLSCREEN, false);
    }

    public boolean enableJavascript() {
        return mPrefs.getBoolean(PREF_ENABLE_JAVASCRIPT, true);
    }

    public PluginState getPluginState() {
        String state = mPrefs.getString(PREF_PLUGIN_STATE, "ON");
        return PluginState.valueOf(state);
    }

    public boolean openInBackground() {
        return mPrefs.getBoolean(PREF_OPEN_IN_BACKGROUND, false);
    }

    public boolean hasDesktopUseragent(WebView view) {
        return view != null && mCustomUserAgents.get(view.getSettings()) != null;
    }

    public static String getFactoryResetHomeUrl(Context context) {
        requireInitialization();
        return sFactoryResetUrl;
    }

    public boolean allowUniversalAccessFromFileURLs() {
        return mPrefs.getBoolean(PREF_ALLOW_UNIVERSAL_ACCESS_FROM_FILE_URLS, true);
    }

    public boolean allowFileAccessFromFileURLs() {
        return mPrefs.getBoolean(PREF_ALLOW_FILE_ACCESS_FROM_FILE_URLS, true);
    }

    // -----------------------------
    // getter/setters for debug_preferences.xml
    // -----------------------------

    public boolean isHardwareAccelerated() {
        if (!isDebugEnabled()) {
            return true;
        }
        return mPrefs.getBoolean(PREF_ENABLE_HARDWARE_ACCEL, true);
    }

    public boolean isSkiaHardwareAccelerated() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_HARDWARE_ACCEL_SKIA, false);
    }

    public int getUserAgent() {
        if (!isDebugEnabled()) {
            return 0;
        }
        return Integer.parseInt(mPrefs.getString(PREF_USER_AGENT, "0"));
    }

    public boolean isDebugEnabled() {
        requireInitialization();
        return mPrefs.getBoolean(PREF_DEBUG_MENU, false);
    }

    private void updateSearchEngine(boolean force) {
        String searchEngineName = getSearchEngineName();
        if (force || mSearchEngine == null ||
                !mSearchEngine.getName().equals(searchEngineName)) {
            mSearchEngine = SearchEngines.get(mContext, searchEngineName);
        }
    }

    public SearchEngine getSearchEngine() {
        if (mSearchEngine == null) {
            updateSearchEngine(false);
        }
        return mSearchEngine;
    }

    // -----------------------------
    // getter/setters for lab_preferences.xml
    // -----------------------------

    public boolean getSavePassword() {
        return mPrefs.getBoolean(PREF_SAVE_PASSWORD, false);
    }

    public boolean getSaveFormData() {
        return mPrefs.getBoolean(PREF_SAVE_FORM_DATA, false);
    }

    public boolean useQuickControls() {
        return mPrefs.getBoolean(PREF_ENABLE_QUICK_CONTROLS, true);
    }

    public boolean blockPopupWindows() {
        return mPrefs.getBoolean(PREF_BLOCK_POPUP_WINDOWS, true);
    }

    /**
     * youtube measures all elements in 'rem' units<br/>
     * 'rem' units heavily depend on text size<br/>
     * this could be a problem when changing system font size<br/>
     * so we need to force normal text zoom<br/>
     * <br/>
     * also, don't forget to set minimum font size to 1:<br/>
     * <a href="https://stackoverflow.com/questions/41179357/android-webview-rem-units-scale-way-to-large-for-boxes">stackoverflow discussion</a><br/>
     * {@link WebSettings#setMinimumFontSize(int)}<br/>
     * {@link WebSettings#setMinimumLogicalFontSize(int)}<br/>
     * <br/>
     * other useful api that have the same effect as text zooming:<br/>
     * {@link WebSettings#setTextSize(TextSize)}<br/>
     * {@link WebSettings#setDefaultFontSize(int)}<br/>
     * @return zoom in percents
     */
    public int getTextZoom() {
        return mPrefs.getInt(PREF_TEXT_ZOOM, 100);
    }

    public ZoomDensity getDefaultZoom() {
        String zoom = mPrefs.getString(PREF_DEFAULT_ZOOM, ZoomDensity.MEDIUM.name());
        return ZoomDensity.valueOf(zoom);
    }

    public boolean loadPageInOverviewMode() {
        return mPrefs.getBoolean(PREF_LOAD_PAGE, true);
    }

    public boolean isWideViewport() {
        // youtube: fit layout into the screen (set to false)
        return mPrefs.getBoolean(PREF_WIDE_VIEWPORT, false);
    }

    public int getInitialScale() {
        // 100 - normal resolution (app default), 50 - 2160p resolution, 30 - 4k resolution, 0 - android default
        // NOTE: you may need to restart the browser in order this setting to work
        return mPrefs.getInt(PREF_INITIAL_SCALE, 0);
    }

    public boolean rememberPasswords() {
        return mPrefs.getBoolean(PREF_REMEMBER_PASSWORDS, true);
    }

    private static void requireInitialization() {
        synchronized (BrowserSettings.class) {
            while (!sInitialized) {
                try {
                    BrowserSettings.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // -----------------------------
    // getter/setters for browser recovery
    // -----------------------------
    /**
     * The last time browser was started.
     * @return The last browser start time as System.currentTimeMillis. This
     * can be 0 if this is the first time or the last tab was closed.
     */
    public long getLastRecovered() {
        return mPrefs.getLong(KEY_LAST_RECOVERED, 0);
    }

    /**
     * Sets the last browser start time.
     * @param time The last time as System.currentTimeMillis that the browser
     * was started. This should be set to 0 if the last tab is closed.
     */
    public void setLastRecovered(long time) {
        mPrefs.edit()
                .putLong(KEY_LAST_RECOVERED, time)
                .apply();
    }

    /**
     * Used to determine whether or not the previous browser run crashed. Once
     * the previous state has been determined, the value will be set to false
     * until a pause is received.
     * @return true if the last browser run was paused or false if it crashed.
     */
    public boolean wasLastRunPaused() {
        return mPrefs.getBoolean(KEY_LAST_RUN_PAUSED, false);
    }

    /**
     * Sets whether or not the last run was a pause or crash.
     * @param isPaused Set to true When a pause is received or false after
     * resuming.
     */
    public void setLastRunPaused(boolean isPaused) {
        mPrefs.edit()
                .putBoolean(KEY_LAST_RUN_PAUSED, isPaused)
                .apply();
    }

    // -----------------------------
    // getter/setters for advanced_preferences.xml
    // -----------------------------

    public String getSearchEngineName() {
        return mPrefs.getString(PREF_SEARCH_ENGINE, SearchEngine.GOOGLE);
    }

    public boolean allowAppTabs() {
        return mPrefs.getBoolean(PREF_ALLOW_APP_TABS, false);
    }

    public int getMaxTabs() {
        return mPrefs.getInt(PREF_MAX_TABS, 1);
    }

    // -----------------------------
    // getter/setters for bandwidth_preferences.xml
    // -----------------------------

    public static String getPreloadOnWifiOnlyPreferenceString(Context context) {
        return context.getResources().getString(R.string.pref_data_preload_value_wifi_only);
    }

    public static String getPreloadAlwaysPreferenceString(Context context) {
        return context.getResources().getString(R.string.pref_data_preload_value_always);
    }

    private static final String DEAULT_PRELOAD_SECURE_SETTING_KEY =
            "browser_default_preload_setting";

    public String getDefaultPreloadSetting() {
        String preload = Settings.Secure.getString(mContext.getContentResolver(),
                DEAULT_PRELOAD_SECURE_SETTING_KEY);
        if (preload == null) {
            preload = mContext.getResources().getString(R.string.pref_data_preload_default_value);
        }
        return preload;
    }

    public String getPreloadEnabled() {
        return mPrefs.getString(PREF_DATA_PRELOAD, getDefaultPreloadSetting());
    }


    public void toggleDesktopUseragent(WebView view) {
        if (view == null) {
            return;
        }
        WebSettings settings = view.getSettings();
        if (mCustomUserAgents.get(settings) != null) {
            mCustomUserAgents.remove(settings);
            settings.setUserAgentString(USER_AGENTS[getUserAgent()]);
        } else {
            mCustomUserAgents.put(settings, DESKTOP_USERAGENT);
            settings.setUserAgentString(DESKTOP_USERAGENT);
        }
    }


    /**
     * Syncs all the settings that have a Preference UI
     */
    private void syncSetting(WebSettings settings) {
        settings.setJavaScriptEnabled(enableJavascript());
        settings.setPluginState(getPluginState());
        settings.setJavaScriptCanOpenWindowsAutomatically(!blockPopupWindows());
        settings.setSavePassword(rememberPasswords());

        // fix android bug when measuring 'rem' units
        // https://stackoverflow.com/questions/41179357/android-webview-rem-units-scale-way-to-large-for-boxes
        settings.setMinimumFontSize(1);
        settings.setMinimumLogicalFontSize(1);

        // youtube: fit layout into screen
        settings.setTextZoom(getTextZoom());
        settings.setDefaultZoom(getDefaultZoom());
        settings.setLoadWithOverviewMode(loadPageInOverviewMode());
        settings.setUseWideViewPort(isWideViewport());

        if (VERSION.SDK_INT >= 16) {
            settings.setAllowUniversalAccessFromFileURLs(allowUniversalAccessFromFileURLs());
            settings.setAllowFileAccessFromFileURLs(allowFileAccessFromFileURLs());
        }

        String ua = mCustomUserAgents.get(settings);
        if (ua != null) {
            settings.setUserAgentString(ua);
        } else {
            settings.setUserAgentString(USER_AGENTS[getUserAgent()]);
        }

        if (VERSION.SDK_INT >= 17) {
            // NOTE: 'autoplay' fix on WebView engine
            settings.setMediaPlaybackRequiresUserGesture(getPlaybackRequiresGesture());
        }

        settings.setSaveFormData(getSaveFormData());
        settings.setSavePassword(getSavePassword());
    }

    public WebStorageSizeManager getWebStorageSizeManager() {
        requireInitialization();
        return mWebStorageSizeManager;
    }

    /**
     * Syncs all the settings that have no UI
     * These cannot change, so we only need to set them once per WebSettings
     */
    private void syncStaticSettings(WebSettings settings) {
        // WebView inside Browser doesn't want initial focus to be set.
        settings.setNeedInitialFocus(false);
        // enable smooth transition for better performance during panning or
        // zooming
        settings.setEnableSmoothTransition(true);

        // HTML5 API flags
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        //// HTML5 configuration parametersettings.
        settings.setAppCacheMaxSize(getWebStorageSizeManager().getAppCacheMaxSize());
        settings.setAppCachePath(getAppCachePath());
        settings.setDatabasePath(getDatabasePath());
    }

    private String getDatabasePath() {
        return mContext.getDir("databases", 0).getPath();
    }

    private String getAppCachePath() {
        if (mAppCachePath == null) {
            mAppCachePath = mContext.getDir("appcache", 0).getPath();
        }
        return mAppCachePath;
    }

    private Runnable mSetup = new Runnable() {

        @Override
        public void run() {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            mFontSizeMult = metrics.scaledDensity / metrics.density;

            mWebStorageSizeManager = new WebStorageSizeManager(mContext,
                    new WebStorageSizeManager.StatFsDiskInfo(getAppCachePath()),
                    new WebStorageSizeManager.WebKitAppCacheInfo(getAppCachePath()));

            // TODO: setup page caching

            synchronized (BrowserSettings.class) {
                sInitialized = true;
                BrowserSettings.class.notifyAll();
            }
        }
    };

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // TODO: not implemented
    }

    public void startManagingSettings(WebSettings settings) {

        if (mNeedsSharedSync) {
            syncSharedSettings();
        }

        synchronized (mManagedSettings) {
            syncStaticSettings(settings);
            syncSetting(settings);
            mManagedSettings.add(new WeakReference<>(settings));
        }
    }

    private void syncSharedSettings() {
        // TODO: not implemented
    }

    public void stopManagingSettings(WebSettings settings) {
        Iterator<WeakReference<WebSettings>> iter = mManagedSettings.iterator();
        while (iter.hasNext()) {
            WeakReference<WebSettings> ref = iter.next();
            if (ref.get() == settings) {
                iter.remove();
                return;
            }
        }
    }

    private static final String DEFAULT_LINK_PREFETCH_SECURE_SETTING_KEY =
            "browser_default_link_prefetch_setting";

    public String getDefaultLinkPrefetchSetting() {
        String preload = Settings.Secure.getString(mContext.getContentResolver(),
                DEFAULT_LINK_PREFETCH_SECURE_SETTING_KEY);
        if (preload == null) {
            preload = mContext.getResources().getString(R.string.pref_link_prefetch_default_value);
        }
        return preload;
    }

    public static String getLinkPrefetchAlwaysPreferenceString(Context context) {
        return context.getResources().getString(R.string.pref_link_prefetch_value_always);
    }

    public String getLinkPrefetchEnabled() {
        return mPrefs.getString(PREF_LINK_PREFETCH, getDefaultLinkPrefetchSetting());
    }

    public static String getLinkPrefetchOnWifiOnlyPreferenceString(Context context) {
        return context.getResources().getString(R.string.pref_link_prefetch_value_wifi_only);
    }

    // update connectivity-dependent options
    public void updateConnectionType() {
        ConnectivityManager cm = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        String linkPrefetchPreference = getLinkPrefetchEnabled();
        boolean linkPrefetchAllowed = linkPrefetchPreference.
                equals(getLinkPrefetchAlwaysPreferenceString(mContext));
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_ETHERNET:
                case ConnectivityManager.TYPE_BLUETOOTH:
                    linkPrefetchAllowed |= linkPrefetchPreference.
                            equals(getLinkPrefetchOnWifiOnlyPreferenceString(mContext));
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                case ConnectivityManager.TYPE_MOBILE_DUN:
                case ConnectivityManager.TYPE_MOBILE_MMS:
                case ConnectivityManager.TYPE_MOBILE_SUPL:
                case ConnectivityManager.TYPE_WIMAX:
                default:
                    break;
            }
        }
        if (mLinkPrefetchAllowed != linkPrefetchAllowed) {
            mLinkPrefetchAllowed = linkPrefetchAllowed;
            syncManagedSettings();
        }
    }

    private void syncManagedSettings() {
        syncSharedSettings();
        synchronized (mManagedSettings) {
            Iterator<WeakReference<WebSettings>> iter = mManagedSettings.iterator();
            while (iter.hasNext()) {
                WeakReference<WebSettings> ref = iter.next();
                WebSettings settings = ref.get();
                if (settings == null) {
                    iter.remove();
                    continue;
                }
                syncSetting(settings);
            }
        }
    }
}
