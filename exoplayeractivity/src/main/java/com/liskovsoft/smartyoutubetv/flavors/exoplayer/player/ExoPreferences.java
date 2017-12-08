package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.exoplayer2.C;

public class ExoPreferences implements ExoKeys {
    private final Context mContext;
    private SharedPreferences mPrefs;

    public ExoPreferences(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getSelectedTrackId() {
        return mPrefs.getString(ExoKeys.SELECTED_TRACK_ID, null);
    }

    public void setSelectedTrackId(String id) {
        mPrefs.edit()
                .putString(ExoKeys.SELECTED_TRACK_ID, id)
                .apply();
    }


    public int getSelectedTrackHeight() {
        return mPrefs.getInt(ExoKeys.SELECTED_TRACK_HEIGHT, 0);
    }

    public void setSelectedTrackHeight(int height) {
        mPrefs.edit()
                .putInt(ExoKeys.SELECTED_TRACK_HEIGHT, height)
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
}
