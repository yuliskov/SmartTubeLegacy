package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.exoplayer2.C;

public final class ExoPreferences {
    private static ExoPreferences sInstance;
    private final Context mContext;
    private SharedPreferences mPrefs;
    private static final String SELECTED_TRACK_ID = "selectedTrackId";
    private static final String SELECTED_TRACK_HEIGHT = "selectedTrackHeight";
    private static final String AUTOFRAMERATE_CHECKED = "display_rate_switch";
    private static final String SWITCH_TO_UHD_CHECKED = "switch_to_uhd";

    public static ExoPreferences instance(Context ctx) {
        if (sInstance == null)
            sInstance = new ExoPreferences(ctx);
        return sInstance;
    }

    public ExoPreferences(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getSelectedTrackId() {
        return mPrefs.getString(SELECTED_TRACK_ID, null);
    }

    public void setSelectedTrackId(String id) {
        mPrefs.edit()
                .putString(SELECTED_TRACK_ID, id)
                .apply();
    }


    public int getSelectedTrackHeight() {
        return mPrefs.getInt(SELECTED_TRACK_HEIGHT, 0);
    }

    public void setSelectedTrackHeight(int height) {
        mPrefs.edit()
                .putInt(SELECTED_TRACK_HEIGHT, height)
                .apply();
    }

    public void resetPosition(String key) {
        mPrefs.edit()
                .remove(key)
                .apply();
    }

    public void setPosition(String key, long val) {
        mPrefs.edit()
                .putLong(key, val)
                .apply();
    }

    public long getPosition(String key) {
        return mPrefs.getLong(key, C.TIME_UNSET);
    }

    public boolean getAutoframerateChecked() {
        boolean isChecked = mPrefs.getBoolean(AUTOFRAMERATE_CHECKED, false);
        return isChecked;
    }

    public void setAutoframerateChecked(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(AUTOFRAMERATE_CHECKED, isChecked)
                .apply();
    }

    public boolean getSwitchToUHDChecked() {
        boolean isChecked = mPrefs.getBoolean(SWITCH_TO_UHD_CHECKED, false);
        return isChecked;
    }

    public void setSwitchToUHDChecked(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(SWITCH_TO_UHD_CHECKED, isChecked)
                .apply();
    }
}
