package com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.liskovsoft.sharedutils.helpers.Helpers;

class YouTubeIntentTranslator implements IntentTranslator {
    private static final String TAG = YouTubeIntentTranslator.class.getSimpleName();
    private final static String DIAL_EXTRA = "com.amazon.extra.DIAL_PARAM";
    private final static String VIDEO_TEMPLATE_URL = "https://www.youtube.com/tv#?%s";
    private final static String CHANNEL_TEMPLATE_URL = "https://www.youtube.com/tv#/channel?c=%s&resume";
    private final String mDefaultUrl;

    public YouTubeIntentTranslator(String defaultUrl) {
        mDefaultUrl = defaultUrl;
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

        String uriString = String.format(VIDEO_TEMPLATE_URL, dialParam);
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

        String fullUrl = String.format(template, resultParam);
        return Uri.parse(fullUrl);
    }
}
