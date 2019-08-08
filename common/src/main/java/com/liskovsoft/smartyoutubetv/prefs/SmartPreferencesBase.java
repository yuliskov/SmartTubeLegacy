package com.liskovsoft.smartyoutubetv.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SmartPreferencesBase {
    private final Context mContext;
    private final SharedPreferences mPrefs;

    public SmartPreferencesBase(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    protected void putInt(String key, int val) {
        mPrefs.edit()
                .putInt(key, val)
                .apply();
    }

    protected int getInt(String key, int defVal) {
        return mPrefs.getInt(key, defVal);
    }

    protected void putBoolean(String key, boolean val) {
        mPrefs.edit()
                .putBoolean(key, val)
                .apply();
    }

    protected boolean getBoolean(String key, boolean defVal) {
        return mPrefs.getBoolean(key, defVal);
    }

    protected void putString(String key, String  val) {
        mPrefs.edit()
                .putString(key, val)
                .apply();
    }

    protected String getString(String key, String defVal) {
        return mPrefs.getString(key, defVal);
    }
}
