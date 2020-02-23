package com.liskovsoft.smartyoutubetv.prefs;

import android.content.Context;
import com.liskovsoft.sharedutils.mylogger.Log;

public final class SmartPreferences extends SmartPreferencesBase {
    private static final String TAG = SmartPreferences.class.getSimpleName();
    private static final String VIDEO_FORMAT_NAME = "videoFormatName"; // e.g. '360p' or '720p'
    private static final String BOOTSTRAP_ACTIVITY_NAME = "bootstrapActivityName";
    private static final String BOOTSTRAP_CHECKBOX_CHECKED = "bootstrapCheckBoxChecked";
    private static final String BOOTSTRAP_SELECTED_LANGUAGE = "bootstrapSelectedLanguage";
    private static final String BOOTSTRAP_UPDATE_CHECK = "bootstrapUpdateCheck";
    private static final String BOOTSTRAP_END_CARDS = "bootstrapEndCards";
    private static final String PREFERRED_CODEC = "preferredCodec";
    private static final String BOOTSTRAP_OK_PAUSE = "bootstrapOKPause";
    private static final String UNPLAYABLE_VIDEO_FIX = "unplayableVideoFix";
    private static final String LOCK_LAST_LAUNCHER = "lockLastLauncher";
    private static final String BOOT_PAGE = "bootPage";
    private static final String GLOBAL_AFR_FIX_STATE = "afrFixState";
    private static final String USE_EXTERNAL_PLAYER = "use_external_player2";
    public static final String USE_EXTERNAL_PLAYER_NONE = "use_external_player_none";
    public static final String USE_EXTERNAL_PLAYER_4K = "use_external_player_4k";
    public static final String USE_EXTERNAL_PLAYER_FHD = "use_external_player_fhd";
    public static final String USE_EXTERNAL_PLAYER_SD = "use_external_player_sd";
    public static final String USE_EXTERNAL_PLAYER_KODI = "use_external_player_kodi";
    private static final String FIX_ASPECT_RATIO = "fix_aspect_ratio";
    private static final String LOG_TYPE = "log_type2";
    private static final String PLAYBACK_WORKING_KEY = "playback_working_key";
    private static final String ANIMATED_PREVIEWS = "animated_previews";
    public static final String MUSIC_PAGE = "music_page";
    public static final String SUBSCRIPTIONS_PAGE = "subscriptions_page";
    public static final String WATCH_LATER_PAGE = "watch_later_page";
    public static final String DEFAULT_PAGE = "default_page";
    public static final String UPDATE_CHECK_STABLE = "update_check_stable";
    public static final String UPDATE_CHECK_BETA = "update_check_beta";
    public static final String UPDATE_CHECK_DISABLED = "update_check_disabled";
    public static final String GLOBAL_AFR_FIX_STATE_ENABLED = "afr_fix_state_enabled";
    public static final String GLOBAL_AFR_FIX_STATE_DISABLED = "afr_fix_state_disabled";
    private static final String IS_APP_JUST_INSTALLED = "is_app_just_installed";
    private static final String BACK_PRESS_EXIT = "back_press_exit";
    private static final String PREVIOUS_APP_VERSION_CODE = "previous_app_version_code";
    private static final String BOOT_SUCCEEDED = "boot_succeeded";
    private static final String ALT_PLAYER_MAPPING = "alt_player_mapping";
    private static final String DISABLE_AMAZON_BRIDGE = "disable_amazon_bridge";
    private static final String UGOOS_50HZ_FIX = "UGOOS_50HZ_FIX";
    private static final String USE_NEW_UI = "use_new_ui";
    private static final String HIDE_BOOT_TIPS = "hide_boot_tips";
    private static final String AUTO_SHOW_PLAYER_UI = "auto_show_player_ui";
    public static final String NEW_VIDEO_OPENED = "new_video_opened";
    public static final String CURRENT_VIDEO_POSITION = "current_video_position";
    public static final String CURRENT_VIDEO_PAUSED = "current_video_paused";
    private static final String USER_IS_LOGGED = "user_is_logged";
    private static final String HIGH_CONTRAST_ENABLED = "high_contrast_enabled";
    public static final String AD_BLOCK_STATUS = "ad_block_status";
    public static final String AD_BLOCK_ENABLED = "ad_block_enabled";
    public static final String AD_BLOCK_DISABLED = "ad_block_disabled";
    public static final String AD_BLOCK_UNDEFINED = "ad_block_undefined";
    private static final String DECREASE_PLAYER_UI_TIMEOUT = "decrease_player_ui_timeout";
    private static final String CHANNELS_CLOSE_APP = "channels_close_app";
    private static final String PLAYER_BUFFER_TYPE = "player_buffer_type";
    public static final String PLAYER_BUFFER_TYPE_LOW = "player_buffer_type_low";
    public static final String PLAYER_BUFFER_TYPE_MEDIUM = "player_buffer_type_medium";
    public static final String PLAYER_BUFFER_TYPE_BIG = "player_buffer_type_big";
    public static final int PLAYBACK_UNKNOWN = 0;
    public static final int PLAYBACK_IS_WORKING = 1;
    public static final int PLAYBACK_NOT_WORKING = 2;
    public static final String AVC = "avc";
    public static final String VP9 = "vp9";
    public static final String ALL_CODECS = "all_codecs";
    public static final String NONE = "";
    private static SmartPreferences sInstance;
    private int mPositionSec = -1;
    private long mLastUserInteraction;
    private String mAuthorizationHeader;
    private String mCookieHeader;
    private String mDefaultDisplayMode;
    private String mCurrentDisplayMode;
    private String mPostData;
    private boolean mUserLogged;
    private boolean mVideoPaused;
    private boolean mMirrorEnabled;
    private boolean mIsBrowserInBackground;
    private long mVideoOpenTimeMS;
    private String mVideoSrc;
    private long mVideoActionTimeMS;

    public static SmartPreferences instance(Context ctx) {
        if (sInstance == null)
            sInstance = new SmartPreferences(ctx);
        return sInstance;
    }

    private SmartPreferences(Context context) {
        super(context);
    }

    public void setSelectedFormat(String fmt) {
        putString(VIDEO_FORMAT_NAME, fmt);
    }

    public String getSelectedFormat() {
        String name = getString(VIDEO_FORMAT_NAME, "Auto");
        return name;
    }

    public void setBootActivityName(String name) {
        putString(BOOTSTRAP_ACTIVITY_NAME, name);
    }

    public String getBootActivityName() {
        String name = getString(BOOTSTRAP_ACTIVITY_NAME, null);
        return name;
    }

    public boolean getBootstrapSaveSelection() {
        boolean isChecked = getBoolean(BOOTSTRAP_CHECKBOX_CHECKED, true);
        return isChecked;
    }

    public void setBootstrapSaveSelection(boolean isChecked) {
        putBoolean(BOOTSTRAP_CHECKBOX_CHECKED, isChecked);
    }

    public void setPreferredLanguage(String name) {
        putString(BOOTSTRAP_SELECTED_LANGUAGE, name);
    }

    public String getPreferredLanguage() {
        String name = getString(BOOTSTRAP_SELECTED_LANGUAGE, "");
        return name;
    }

    public void setPreferredCodec(String name) {
        putString(PREFERRED_CODEC, name);
    }

    public String getPreferredCodec() {
        return getString(PREFERRED_CODEC, "");
    }

    public String getBootstrapUpdateCheck() {
        return getString(BOOTSTRAP_UPDATE_CHECK, UPDATE_CHECK_STABLE);
    }

    public void setBootstrapUpdateCheck(String releaseType) {
        putString(BOOTSTRAP_UPDATE_CHECK, releaseType);
    }

    public void setEnableEndCards(boolean isChecked) {
        putBoolean(BOOTSTRAP_END_CARDS, isChecked);
    }

    public boolean getEnableEndCards() {
        return getBoolean(BOOTSTRAP_END_CARDS, true);
    }

    public void setEnableOKPause(boolean isChecked) {
        putBoolean(BOOTSTRAP_OK_PAUSE, isChecked);
    }

    public boolean getEnableOKPause() {
        return getBoolean(BOOTSTRAP_OK_PAUSE, true);
    }

    public void setUnplayableVideoFix(boolean isChecked) {
        putBoolean(UNPLAYABLE_VIDEO_FIX, isChecked);
    }

    public boolean getUnplayableVideoFix() {
        return getBoolean(UNPLAYABLE_VIDEO_FIX, false);
    }

    public void setLockLastLauncher(boolean isChecked) {
        putBoolean(LOCK_LAST_LAUNCHER, isChecked);
    }

    public boolean getLockLastLauncher() {
        return getBoolean(LOCK_LAST_LAUNCHER, false);
    }

    public void setBootPage(String name) {
        putString(BOOT_PAGE, name);
    }

    public String getBootPage() {
        return getString(BOOT_PAGE, DEFAULT_PAGE);
    }

    public void setGlobalAfrFixState(String state) {
        putString(GLOBAL_AFR_FIX_STATE, state);
    }

    public String getGlobalAfrFixState() {
        return getString(GLOBAL_AFR_FIX_STATE, GLOBAL_AFR_FIX_STATE_DISABLED);
    }

    public void setUseExternalPlayer(String type) {
        putString(USE_EXTERNAL_PLAYER, type);
    }

    public String getUseExternalPlayer() {
        return getString(USE_EXTERNAL_PLAYER, USE_EXTERNAL_PLAYER_NONE);
    }

    public void setFixAspectRatio(boolean isChecked) {
        putBoolean(FIX_ASPECT_RATIO, isChecked);
    }

    public boolean getFixAspectRatio() {
        return getBoolean(FIX_ASPECT_RATIO, false);
    }

    public String getLogType() {
        return getString(LOG_TYPE, Log.LOG_TYPE_SYSTEM);
    }

    public void setLogType(String type) {
        putString(LOG_TYPE, type);
    }

    public void setPlaybackWorking(int state) {
        putInt(PLAYBACK_WORKING_KEY, state);
    }

    public int getPlaybackWorking() {
        return getInt(PLAYBACK_WORKING_KEY, PLAYBACK_UNKNOWN);
    }

    public boolean getEnableAnimatedPreviews() {
        return getBoolean(ANIMATED_PREVIEWS, false);
    }

    public void setEnableAnimatedPreviews(boolean enable) {
        putBoolean(ANIMATED_PREVIEWS, enable);
    }

    public boolean isAppJustInstalled() {
        boolean justInstalled = getBoolean(IS_APP_JUST_INSTALLED, true);

        if (justInstalled) {
            putBoolean(IS_APP_JUST_INSTALLED, false);
        }

        Log.d(TAG, "Is app just installed: " + justInstalled);

        return justInstalled;
    }

    public boolean getEnableBackPressExit() {
        return getBoolean(BACK_PRESS_EXIT, false);
    }

    public void setEnableBackPressExit(boolean enable) {
        putBoolean(BACK_PRESS_EXIT, enable);
    }

    public int getPreviousAppVersionCode() {
        return getInt(PREVIOUS_APP_VERSION_CODE, 0);
    }

    public void setPreviousAppVersionCode(int versionCode) {
        putInt(PREVIOUS_APP_VERSION_CODE, versionCode);
    }

    public long getLastVideoActionTimeMS() {
        long result = mVideoActionTimeMS;
        mVideoActionTimeMS = 0;
        return result;
    }

    public void setNewVideoSrc(String src) {
        mVideoSrc = src;
        mVideoActionTimeMS = System.currentTimeMillis();

        runListeners(NEW_VIDEO_OPENED);
    }

    public String getNewVideoSrc() {
        return mVideoSrc;
    }

    public void setCurrentVideoPosition(int positionSec) {
        mPositionSec = positionSec;
        mVideoActionTimeMS = System.currentTimeMillis();

        runListeners(CURRENT_VIDEO_POSITION);
    }

    public long getCurrentVideoPosition() {
        return mPositionSec;
    }

    public void setHtmlVideoPaused(boolean paused) {
        mVideoPaused = paused;
        mVideoActionTimeMS = System.currentTimeMillis();

        runListeners(CURRENT_VIDEO_PAUSED);
    }

    public boolean getHtmlVideoPaused() {
        return mVideoPaused;
    }

    public void setBootSucceeded(boolean succeeded) {
        putBoolean(BOOT_SUCCEEDED, succeeded);
    }

    public boolean getBootSucceeded() {
        return getBoolean(BOOT_SUCCEEDED, true);
    }

    public void setAltPlayerMappingEnabled(boolean enabled) {
        putBoolean(ALT_PLAYER_MAPPING, enabled);
    }

    public boolean getAltPlayerMappingEnabled() {
        return getBoolean(ALT_PLAYER_MAPPING, false);
    }

    public void setDisableYouTubeBridge(boolean disable) {
        putBoolean(DISABLE_AMAZON_BRIDGE, disable);
    }

    public boolean getDisableYouTubeBridge() {
        return getBoolean(DISABLE_AMAZON_BRIDGE, false);
    }

    public boolean getUseNewUI() {
        return getBoolean(USE_NEW_UI, false);
    }

    public void setUseNewUI(boolean useNewUI) {
        putBoolean(USE_NEW_UI, useNewUI);
    }

    public boolean getUgoos50HZFix() {
        return getBoolean(UGOOS_50HZ_FIX, false);
    }

    public void setUgoos50HZFix(boolean checked) {
        putBoolean(UGOOS_50HZ_FIX, checked);
    }

    public boolean getHideBootTips() {
        return getBoolean(HIDE_BOOT_TIPS, false);
    }

    public void setHideBootTips(boolean checked) {
        putBoolean(HIDE_BOOT_TIPS, checked);
    }

    public boolean getAutoShowPlayerUI() {
        return getBoolean(AUTO_SHOW_PLAYER_UI, true);
    }

    public void setAutoShowPlayerUI(boolean checked) {
        putBoolean(AUTO_SHOW_PLAYER_UI, checked);
    }

    // NOT PERSISTENT SETTINGS

    public long getLastUserInteraction() {
        return mLastUserInteraction;
    }

    public void setLastUserInteraction(long time) {
        mLastUserInteraction = time;
    }

    public void setAuthorizationHeader(String header) {
        mAuthorizationHeader = header;
    }

    public String getAuthorizationHeader() {
        return mAuthorizationHeader;
    }

    public void setCookieHeader(String header) {
        mCookieHeader = header;
    }

    public String getCookieHeader() {
        return mCookieHeader;
    }

    public String getDefaultDisplayMode() {
        return mDefaultDisplayMode;
    }

    public void setDefaultDisplayMode(String mode) {
        mDefaultDisplayMode = mode;
    }

    public String getCurrentDisplayMode() {
        return mCurrentDisplayMode;
    }

    public void setCurrentDisplayMode(String mode) {
        mCurrentDisplayMode = mode;
    }

    public void setPostData(String content) {
        mPostData = content;
    }

    public String getPostData() {
        return mPostData;
    }

    public void setUserLogged(boolean userLogged) {
        putBoolean(USER_IS_LOGGED, userLogged);
    }

    public boolean isUserLogged() {
        return getBoolean(USER_IS_LOGGED, false);
    }

    public void sync() {
        setUserLogged(getAuthorizationHeader() != null);
    }

    public boolean isMirrorEnabled() {
        return mMirrorEnabled;
    }

    public void setMirrorEnabled(boolean enabled) {
        mMirrorEnabled = enabled;
    }


    public boolean isBrowserInBackground() {
        return mIsBrowserInBackground;
    }

    public void setBrowserInBackground(boolean inBackground) {
        mIsBrowserInBackground = inBackground;
    }

    public boolean isHighContrastEnabled() {
        return getBoolean(HIGH_CONTRAST_ENABLED, false);
    }

    public void setHighContrastEnabled(boolean enabled) {
        putBoolean(HIGH_CONTRAST_ENABLED, enabled);
    }

    public void setVideoOpenTime(long timeMs) {
        mVideoOpenTimeMS = timeMs;
    }

    public long getVideoOpenTime() {
        return mVideoOpenTimeMS;
    }

    public String getAdBlockStatus() {
        return getString(AD_BLOCK_STATUS, AD_BLOCK_ENABLED);
    }

    public void setAdBlockStatus(String adBlockEnabled) {
        putString(AD_BLOCK_STATUS, adBlockEnabled);
    }

    public boolean getDecreasePlayerUITimeout() {
        return getBoolean(DECREASE_PLAYER_UI_TIMEOUT, false);
    }

    public void setDecreasePlayerUITimeout(boolean checked) {
        putBoolean(DECREASE_PLAYER_UI_TIMEOUT, checked);
    }

    public boolean getChannelsCloseApp() {
        return getBoolean(CHANNELS_CLOSE_APP, false);
    }

    public void setChannelsCloseApp(boolean checked) {
        putBoolean(CHANNELS_CLOSE_APP, checked);
    }

    public String getPlayerBufferType() {
        return getString(PLAYER_BUFFER_TYPE, PLAYER_BUFFER_TYPE_MEDIUM);
    }

    public void setPlayerBufferType(String playerBufferType) {
        putString(PLAYER_BUFFER_TYPE, playerBufferType);
    }
}
