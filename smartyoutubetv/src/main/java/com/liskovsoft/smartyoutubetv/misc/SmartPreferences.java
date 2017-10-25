package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoKeys;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser.VideoFormat;

public final class SmartPreferences {
    private static final String VIDEO_FORMAT_NAME = "videoFormatName"; // e.g. '360p' or '720p'
    private static SmartPreferences mInstance;
    private Context mContext;
    private SharedPreferences mPrefs;

    public static SmartPreferences instance(Context ctx) {
        if (mInstance == null)
            mInstance = new SmartPreferences(ctx);
        return mInstance;
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
}
