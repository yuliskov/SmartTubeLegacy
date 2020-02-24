package com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class YouTubeServiceFinder implements ServiceFinder {
    private static final String TAG = YouTubeServiceFinder.class.getSimpleName();
    private final Context mContext;
    private final IntentTranslator mTranslator;
    private String mDefaultUrl;
    private boolean mIsPersistent;
    private static final String ROOT_URL = "youtube.com/tv";

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
                mIsPersistent = params.isMainPagePersistent();
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
    public boolean checkUrl(String url) {
        Log.d(TAG, "Checking url " + url);
        return url != null && url.contains(ROOT_URL);
    }

    @Override
    public Intent transformIntent(Intent origin) {
        Log.d(TAG, "Intent before transform: " + origin.toUri(0));

        Intent result = mTranslator.translate(origin);
        boolean intentIsEmpty = result == null || result.getData() == null; // doesn't contain url data

        if (intentIsEmpty) {
            result = new Intent(Intent.ACTION_VIEW, Uri.parse(mDefaultUrl));
        }

        Log.d(TAG, "Intent after transform: " + result.toUri(0));

        return result;
    }

    @Override
    public boolean isDefault(Intent intent) {
        Uri originData = intent.getData();

        Intent newIntent = transformIntent(intent);

        Uri data = newIntent.getData();
        newIntent.setData(originData);

        if (data != null) {
            return data.toString().equals(mDefaultUrl);
        }

        return false;
    }
}
