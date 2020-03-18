package com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;

class YouTubeIntentTranslator implements IntentTranslator {
    private static final String TAG = YouTubeIntentTranslator.class.getSimpleName();
    private final static String DIAL_EXTRA = "com.amazon.extra.DIAL_PARAM";
    private final static String VIDEO_TEMPLATE_URL = "%s#?%s";
    private final static String CHANNEL_TEMPLATE_URL = "%s#?c=%s";
    private final String mDefaultUrl;
    private final CommonParams mParams;
    private final String mMainPageUrl;

    public YouTubeIntentTranslator(Context context, String defaultUrl) {
        mDefaultUrl = defaultUrl;
        mParams = CommonParams.instance(context);
        mMainPageUrl = mParams.getMainPageUrl();
    }

    @Override
    public Intent translate(Intent intent) {
        if (intent == null) {
            return null;
        }

        transformRegularIntentData(intent);
        transformAmazonIntentData(intent);

        return intent;
    }

    private void transformRegularIntentData(Intent intent) {
        Uri data = intent.getData();
        if (data == null) {
            return;
        }

        intent.setData(transformUri(data));
    }

    // see Amazon's youtube apk: "org.chromium.youtube_apk.YouTubeActivity.loadStartPage(dialParam)"
    private void transformAmazonIntentData(Intent intent) {
        String dialParam = intent.getStringExtra(DIAL_EXTRA);
        if (dialParam == null) {
            return;
        }

        String uriString = String.format(VIDEO_TEMPLATE_URL, mMainPageUrl, dialParam);
        Log.d(TAG, "Received amazon casting url: " + uriString);
        intent.setData(Uri.parse(uriString));
    }

    private Uri transformUri(final Uri uri) {
        if (uri == null) {
            return null;
        }

        String url = uri.toString();

        String resultParam = YouTubeHelpers.extractVideoIdParamFromUrl(url);
        String template = VIDEO_TEMPLATE_URL;

        if (resultParam == null) {
            resultParam = YouTubeHelpers.extractChannelParamFromUrl(url);
            template = CHANNEL_TEMPLATE_URL;
        }

        if (resultParam == null) {
            return Uri.parse(mDefaultUrl);
        }

        String fullUrl = String.format(template, mMainPageUrl, resultParam);

        return Uri.parse(fullUrl);
    }
}
