package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;

public class YouTubeServiceFinder implements ServiceFinder {
    private final Context mContext;

    public YouTubeServiceFinder(Context context) {
        mContext = context;
    }

    @Override
    public String getUrl() {
        SmartPreferences prefs = SmartPreferences.instance(mContext);

        boolean openMusic = prefs.getOpenMusic();

        if (openMusic) {
            return mContext.getString(R.string.youtube_music_page);
        }

        return mContext.getString(R.string.youtube_main_page);
    }
}
