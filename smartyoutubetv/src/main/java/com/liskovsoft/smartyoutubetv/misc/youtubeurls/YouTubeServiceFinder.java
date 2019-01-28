package com.liskovsoft.smartyoutubetv.misc.youtubeurls;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;

public class YouTubeServiceFinder implements ServiceFinder {
    private final Context mContext;
    private final IntentTranslator mTranslator;
    private String mDefaultUrl;
    private boolean mIsPersistent;

    public YouTubeServiceFinder(Context context) {
        mContext = context;

        SmartPreferences prefs = SmartPreferences.instance(mContext);

        switch (prefs.getBootPage()) {
            case SmartPreferences.MUSIC_PAGE:
                mIsPersistent = true;
                mDefaultUrl = mContext.getString(R.string.youtube_music_page);
                break;
            case SmartPreferences.SUBSCRIPTIONS_PAGE:
                mIsPersistent = true;
                mDefaultUrl = mContext.getString(R.string.youtube_subscriptions_page);
                break;
            case SmartPreferences.DEFAULT_PAGE:
                mIsPersistent = false;
                mDefaultUrl = mContext.getString(R.string.youtube_main_page);
                break;
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
