package com.liskovsoft.smartyoutubetv.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.liskovsoft.sharedutils.mylogger.Log;

public final class SmartPreferences {
    private static final String TAG = SmartPreferences.class.getSimpleName();
    private static final String VIDEO_FORMAT_NAME = "videoFormatName"; // e.g. '360p' or '720p'
    private static final String BOOTSTRAP_ACTIVITY_NAME = "bootstrapActivityName";
    private static final String BOOTSTRAP_CHECKBOX_CHECKED = "bootstrapCheckBoxChecked";
    private static final String BOOTSTRAP_AUTOFRAMERATE_CHECKED = "display_rate_switch";
    private static final String BOOTSTRAP_SELECTED_LANGUAGE = "bootstrapSelectedLanguage";
    private static final String BOOTSTRAP_UPDATE_CHECK = "bootstrapUpdateCheck";
    private static final String BOOTSTRAP_OLD_UI_CHECKED = "bootstrapOldUIChecked";
    private static final String BOOTSTRAP_END_CARDS = "bootstrapEndCards";
    private static final String PREFERRED_CODEC = "preferredCodec";
    private static final String BOOTSTRAP_OK_PAUSE = "bootstrapOKPause";
    private static final String UNPLAYABLE_VIDEO_FIX = "unplayableVideoFix";
    private static final String LOCK_LAST_LAUNCHER = "lockLastLauncher";
    private static final String BOOT_PAGE = "bootPage";
    private static final String GLOBAL_AFR_FIX_STATE = "afrFixState";
    private static final String AUTHORIZATION_HEADER = "authorization_header";
    private static final String COOKIE_HEADER = "cookie_header";
    private static final String USE_EXTERNAL_PLAYER = "use_external_player";
    private static final String FIX_ASPECT_RATIO = "fix_aspect_ratio";
    private static final String LOG_TYPE = "log_type";
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
    public static final int PLAYBACK_UNKNOWN = 0;
    public static final int PLAYBACK_IS_WORKING = 1;
    public static final int PLAYBACK_NOT_WORKING = 2;
    public static final String AVC = "avc";
    public static final String VP9 = "vp9";
    public static final String NONE = "";
    private static SmartPreferences sInstance;
    private Context mContext;
    private SharedPreferences mPrefs;

    public static SmartPreferences instance(Context ctx) {
        if (sInstance == null)
            sInstance = new SmartPreferences(ctx);
        return sInstance;
    }

    private SmartPreferences(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setSelectedFormat(String fmt) {
        mPrefs.edit()
                .putString(VIDEO_FORMAT_NAME, fmt)
                .apply();
    }

    public String getSelectedFormat() {
        String name = mPrefs.getString(VIDEO_FORMAT_NAME, "Auto");
        return name;
    }

    public void setBootstrapActivityName(String name) {
        mPrefs.edit()
                .putString(BOOTSTRAP_ACTIVITY_NAME, name)
                .apply();
    }

    public String getBootstrapActivityName() {
        String name = mPrefs.getString(BOOTSTRAP_ACTIVITY_NAME, null);
        return name;
    }

    public void resetBootstrapActivityName() {
        mPrefs.edit().remove(BOOTSTRAP_ACTIVITY_NAME).apply();
    }

    public boolean getBootstrapSaveSelection() {
        boolean isChecked = mPrefs.getBoolean(BOOTSTRAP_CHECKBOX_CHECKED, true);
        return isChecked;
    }

    public void setBootstrapSaveSelection(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(BOOTSTRAP_CHECKBOX_CHECKED, isChecked)
                .apply();
    }

    public boolean getBootstrapAutoframerate() {
        boolean isChecked = mPrefs.getBoolean(BOOTSTRAP_AUTOFRAMERATE_CHECKED, false);
        return isChecked;
    }

    public void setBootstrapAutoframerate(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(BOOTSTRAP_AUTOFRAMERATE_CHECKED, isChecked)
                .apply();
    }

    public void setPreferredLanguage(String name) {
        mPrefs.edit()
                .putString(BOOTSTRAP_SELECTED_LANGUAGE, name)
                .apply();
    }

    public String getPreferredLanguage() {
        String name = mPrefs.getString(BOOTSTRAP_SELECTED_LANGUAGE, "");
        return name;
    }

    public void setPreferredCodec(String name) {
        mPrefs.edit()
                .putString(PREFERRED_CODEC, name)
                .apply();
    }

    public String getPreferredCodec() {
        return mPrefs.getString(PREFERRED_CODEC, "");
    }

    public String getBootstrapUpdateCheck() {
        return mPrefs.getString(BOOTSTRAP_UPDATE_CHECK, UPDATE_CHECK_STABLE);
    }

    public void setBootstrapUpdateCheck(String releaseType) {
        mPrefs.edit()
                .putString(BOOTSTRAP_UPDATE_CHECK, releaseType)
                .apply();
    }

    public boolean getBootstrapOldUI() {
        boolean isChecked = mPrefs.getBoolean(BOOTSTRAP_OLD_UI_CHECKED, false);
        return isChecked;
    }

    public void setBootstrapOldUI(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(BOOTSTRAP_OLD_UI_CHECKED, isChecked)
                .apply();
    }

    public void setEnableEndCards(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(BOOTSTRAP_END_CARDS, isChecked)
                .apply();
    }

    public boolean getEnableEndCards() {
        return mPrefs.getBoolean(BOOTSTRAP_END_CARDS, true);
    }

    public void setEnableOKPause(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(BOOTSTRAP_OK_PAUSE, isChecked)
                .apply();
    }

    public boolean getEnableOKPause() {
        return mPrefs.getBoolean(BOOTSTRAP_OK_PAUSE, true);
    }

    public void setUnplayableVideoFix(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(UNPLAYABLE_VIDEO_FIX, isChecked)
                .apply();
    }

    public boolean getUnplayableVideoFix() {
        return mPrefs.getBoolean(UNPLAYABLE_VIDEO_FIX, false);
    }

    public void setLockLastLauncher(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(LOCK_LAST_LAUNCHER, isChecked)
                .apply();
    }

    public boolean getLockLastLauncher() {
        return mPrefs.getBoolean(LOCK_LAST_LAUNCHER, false);
    }

    public void setBootPage(String name) {
        mPrefs.edit()
                .putString(BOOT_PAGE, name)
                .apply();
    }

    public String getBootPage() {
        return mPrefs.getString(BOOT_PAGE, DEFAULT_PAGE);
    }

    public void setGlobalAfrFixState(String state) {
        mPrefs.edit()
                .putString(GLOBAL_AFR_FIX_STATE, state)
                .apply();
    }

    public String getGlobalAfrFixState() {
        return mPrefs.getString(GLOBAL_AFR_FIX_STATE, GLOBAL_AFR_FIX_STATE_DISABLED);
    }

    public void setAuthorizationHeader(String header) {
        mPrefs.edit()
                .putString(AUTHORIZATION_HEADER, header)
                .apply();
    }

    public String getAuthorizationHeader() {
        return mPrefs.getString(AUTHORIZATION_HEADER, null);
    }

    public void setCookieHeader(String header) {
        mPrefs.edit()
                .putString(COOKIE_HEADER, header)
                .apply();
    }

    public String getCookieHeader() {
        return mPrefs.getString(COOKIE_HEADER, null);
    }

    public void setUseExternalPlayer(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(USE_EXTERNAL_PLAYER, isChecked)
                .apply();
    }

    public boolean getUseExternalPlayer() {
        return mPrefs.getBoolean(USE_EXTERNAL_PLAYER, false);
    }

    public void setFixAspectRatio(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(FIX_ASPECT_RATIO, isChecked)
                .apply();
    }

    public boolean getFixAspectRatio() {
        return mPrefs.getBoolean(FIX_ASPECT_RATIO, false);
    }

    public int getLogType() {
        return mPrefs.getInt(LOG_TYPE, Log.LOG_TYPE_SYSTEM);
    }

    public void setLogType(int type) {
        mPrefs.edit()
                .putInt(LOG_TYPE, type)
                .apply();
    }

    public void setPlaybackWorking(int state) {
        mPrefs.edit()
                .putInt(PLAYBACK_WORKING_KEY, state)
                .apply();
    }

    public int getPlaybackWorking() {
        return mPrefs.getInt(PLAYBACK_WORKING_KEY, PLAYBACK_UNKNOWN);
    }

    public boolean getEnableAnimatedPreviews() {
        return mPrefs.getBoolean(ANIMATED_PREVIEWS, false);
    }

    public void setEnableAnimatedPreviews(boolean enable) {
        mPrefs.edit()
                .putBoolean(ANIMATED_PREVIEWS, enable)
                .apply();
    }

    public boolean isAppJustInstalled() {
        boolean justInstalled = mPrefs.getBoolean(IS_APP_JUST_INSTALLED, true);

        if (justInstalled) {
            mPrefs.edit().putBoolean(IS_APP_JUST_INSTALLED, false).apply();
        }

        Log.d(TAG, "Is app just installed: " + justInstalled);

        return justInstalled;
    }

    public boolean getEnableBackPressExit() {
        return mPrefs.getBoolean(BACK_PRESS_EXIT, false);
    }

    public void setEnableBackPressExit(boolean enable) {
        mPrefs.edit()
                .putBoolean(BACK_PRESS_EXIT, enable)
                .apply();
    }
}
