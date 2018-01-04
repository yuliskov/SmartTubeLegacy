package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.VideoFormat;

public final class SmartPreferences {
    private static final String VIDEO_FORMAT_NAME = "videoFormatName"; // e.g. '360p' or '720p'
    private static final String BOOTSTRAP_ACTIVITY_NAME = "bootstrapActivityName";
    private static final String BOOTSTRAP_CHECKBOX_CHECKED = "bootstrapCheckBoxChecked";
    private static final String BOOTSTRAP_AUTOFRAMERATE_CHECKED = "display_rate_switch";
    private static final String BOOTSTRAP_SELECTED_LANGUAGE = "bootstrapSelectedLanguage";
    private static final String BOOTSTRAP_UPDATE_CHECKED = "bootstrapUpdateChecked";
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

    public void setSelectedFormat(VideoFormat fmt) {
        String name = fmt.toString();
        mPrefs.edit()
                .putString(VIDEO_FORMAT_NAME, name)
                .apply();
    }

    public VideoFormat getSelectedFormat() {
        String name = mPrefs.getString(VIDEO_FORMAT_NAME, "720p");
        return VideoFormat.fromName(name);
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

    public boolean getBootstrapUpdateCheck() {
        boolean isChecked = mPrefs.getBoolean(BOOTSTRAP_UPDATE_CHECKED, true);
        return isChecked;
    }

    public void setBootstrapUpdateCheck(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(BOOTSTRAP_UPDATE_CHECKED, isChecked)
                .apply();
    }
}
