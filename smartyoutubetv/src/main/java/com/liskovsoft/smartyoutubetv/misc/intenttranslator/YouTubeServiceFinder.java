package com.liskovsoft.smartyoutubetv.misc.intenttranslator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class YouTubeServiceFinder implements ServiceFinder {
    private final Context mContext;
    private final IntentTranslator mTranslator;
    private String mDefaultUrl;
    private boolean mIsPersistent;

    public YouTubeServiceFinder(Context context) {
        mContext = context;

        SmartPreferences prefs = SmartPreferences.instance(mContext);
        CommonParams params = CommonParams.instance(mContext);

        switch (prefs.getBootPage()) {
            case SmartPreferences.MUSIC_PAGE:
                mIsPersistent = true;
                mDefaultUrl = params.getMusicPageUrl();
                break;
            case SmartPreferences.SUBSCRIPTIONS_PAGE:
                mIsPersistent = true;
                mDefaultUrl = params.getSubscriptionsPageUrl();
                break;
            case SmartPreferences.WATCH_LATER_PAGE:
                mIsPersistent = true;
                mDefaultUrl = params.getWatchLaterPageUrl();
                break;
            case SmartPreferences.DEFAULT_PAGE:
                mIsPersistent = false;
                mDefaultUrl = params.getMainPageUrl();
                break;
        }

        if (mDefaultUrl == null) { // other services not specified, e.g. kids flavor
            mIsPersistent = false;
            mDefaultUrl = params.getMainPageUrl();
        }

        mTranslator = new YouTubeIntentTranslator(mDefaultUrl);
    }

    @Override
    public String getUrl() {
        return mDefaultUrl;
    }

    @Override
    public Intent getIntent(Intent origin) {
        if (mIsPersistent) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(mDefaultUrl));
        }

        return mTranslator.translate(origin);
    }
}
