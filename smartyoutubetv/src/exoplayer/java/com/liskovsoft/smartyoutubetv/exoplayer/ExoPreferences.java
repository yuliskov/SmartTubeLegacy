package com.liskovsoft.smartyoutubetv.exoplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ExoPreferences implements ExoKeys {
    private final Context mContext;
    private SharedPreferences mPrefs;

    public ExoPreferences(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public int getRendererIndex() {
        return mPrefs.getInt(ExoKeys.RENDERER_INDEX, -1);
    }

    public int getGroupIndex() {
        return mPrefs.getInt(ExoKeys.GROUP_INDEX, -1);
    }

    public int getTrackIndex() {
        return mPrefs.getInt(ExoKeys.TRACK_INDEX, -1);
    }

    public void setRendererIndex(int rendererIndex) {
        mPrefs.edit()
                .putInt(ExoKeys.RENDERER_INDEX, rendererIndex)
                .apply();
    }

    public void setGroupIndex(int groupIndex) {
        mPrefs.edit()
                .putInt(ExoKeys.GROUP_INDEX, groupIndex)
                .apply();
    }

    public void setTrackIndex(int trackIndex) {
        mPrefs.edit()
                .putInt(ExoKeys.TRACK_INDEX, trackIndex)
                .apply();
    }

    public String getSelectedTrackId() {
        return mPrefs.getString(ExoKeys.SELECTED_TRACK_ID, null);
    }

    public void setSelectedTrackId(String id) {
        mPrefs.edit()
                .putString(ExoKeys.SELECTED_TRACK_ID, id)
                .apply();
    }
}
