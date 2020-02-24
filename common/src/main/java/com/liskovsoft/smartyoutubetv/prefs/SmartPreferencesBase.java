package com.liskovsoft.smartyoutubetv.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.liskovsoft.smartyoutubetv.common.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartPreferencesBase {
    private final Context mContext;
    private final SharedPreferences mPrefs;
    private Map<String, List<Runnable>> mListeners;

    public SmartPreferencesBase(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String defaultPrefsName = context.getPackageName() + "_preferences";
        PreferenceManager.setDefaultValues(context, defaultPrefsName, Context.MODE_PRIVATE, R.xml.smart_preferences, true);
        mListeners = new HashMap<>();
    }

    protected void putInt(String key, int val) {
        mPrefs.edit()
                .putInt(key, val)
                .apply();
    }

    protected int getInt(String key, int defVal) {
        return mPrefs.getInt(key, defVal);
    }

    protected void putLong(String key, long val) {
        mPrefs.edit()
                .putLong(key, val)
                .apply();
    }

    protected long getLong(String key, long defVal) {
        return mPrefs.getLong(key, defVal);
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

    public void addListener(String valueId, Runnable listener) {
        List<Runnable> listeners = mListeners.get(valueId);

        if (listeners == null) {
            listeners = new ArrayList<>();
            mListeners.put(valueId, listeners);
        }

        listeners.add(listener);

    }

    protected void runListeners(String valuedId) {
        List<Runnable> listeners = mListeners.get(valuedId);
        if (listeners != null) {
            for (Runnable listener : listeners) {
                listener.run();
            }
        }
    }
}
