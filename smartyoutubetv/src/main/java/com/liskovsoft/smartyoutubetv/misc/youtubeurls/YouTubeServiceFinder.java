package com.liskovsoft.smartyoutubetv.misc.youtubeurls;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;

public class YouTubeServiceFinder implements ServiceFinder {
    private final Context mContext;
    private final IntentTranslator mTranslator;
    private final String mMainPageUrl;
    private final String mMusicPageUrl;
    private String mDefaultUrl;
    private boolean mIsPersistent;

    public YouTubeServiceFinder(Context context) {
        mContext = context;

        mMainPageUrl = mContext.getString(R.string.youtube_main_page);
        mMusicPageUrl = mContext.getString(R.string.youtube_music_page);

        SmartPreferences prefs = SmartPreferences.instance(mContext);

        if (prefs.getOpenMusic()) {
            mIsPersistent = true;
            mDefaultUrl = mMusicPageUrl;
        } else {
            mIsPersistent = false;
            mDefaultUrl = mMainPageUrl;
        }

        mTranslator = new YouTubeIntentTranslator(mDefaultUrl);
    }

    @Override
    public String getUrl() {
        return mDefaultUrl;
    }

    @Override
    public boolean isPersistent() {
        return mIsPersistent;
    }

    @Override
    public Intent getIntent(Intent origin) {
        if (mIsPersistent) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(mDefaultUrl));
        }

        return mTranslator.translate(origin);
    }
}
