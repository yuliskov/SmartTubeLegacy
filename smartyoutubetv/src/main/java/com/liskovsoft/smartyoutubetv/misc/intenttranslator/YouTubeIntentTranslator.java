package com.liskovsoft.smartyoutubetv.misc.intenttranslator;

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
        intent.setData(Uri.parse(uriString));
    }

    /**
     * Extracts channel params
     * <br/>
     * Examples of the input/output url:
     * <pre>
     * origin: https://www.youtube.com/channel/UCG8jk87DknZ40X_6urApHXA
     * result: https://www.youtube.com/tv#/channel?c=UCrLG_XHXdF1UK429FQO2Hmw&resume
     * </pre>
     * @param url desktop url (see manifest file for the patterns)
     * @return video params
     */
    private String extractChannelParamFromUrl(String url) {
        String[] patterns = {"channel/[^&\\s]*"};
        String res = Helpers.runMultiMatcher(url, patterns);

        if (res == null) {
            Log.w(TAG, "Url isn't a channel: " + url);
            return null;
        }

        return res.replace("channel/", "");
    }

    /**
     * Extracts video params e.g. <code>v=xtx33RuFCik</code> from url
     * <br/>
     * Examples of the input/output url:
     * <pre>
     * origin video: https://www.youtube.com/watch?v=xtx33RuFCik
     * needed video: https://www.youtube.com/tv#/watch/video/control?v=xtx33RuFCik
     * needed video: https://www.youtube.com/tv?gl=us&hl=en-us&v=xtx33RuFCik
     * needed video: https://www.youtube.com/tv?v=xtx33RuFCik
     *
     * origin playlist: https://www.youtube.com/playlist?list=PLbl01QFpbBY1XGwNb8SBmoA3hshpK1pZj
     * needed playlist: https://www.youtube.com/tv#/watch/video/control?list=PLbl01QFpbBY1XGwNb8SBmoA3hshpK1pZj&resume
     * </pre>
     * @param url desktop url (see manifest file for the patterns)
     * @return video params
     */
    private String extractVideoIdParamFromUrl(String url) {
        String[] patterns = {"list=[^&\\s]*", "v=[^&\\s]*", "youtu.be/[^&\\s]*"};
        String res = Helpers.runMultiMatcher(url, patterns);

        if (res == null) {
            Log.w(TAG, "Url isn't a video: " + url);
            return null;
        }

        return res.replace("youtu.be/", "v=");
    }

    private Uri transformUri(final Uri uri) {
        if (uri == null) {
            return null;
        }

        String url = uri.toString();

        String videoParam = extractVideoIdParamFromUrl(url);
        String template = VIDEO_TEMPLATE_URL;

        if (videoParam == null) {
            videoParam = extractChannelParamFromUrl(url);
            template = CHANNEL_TEMPLATE_URL;
        }

        if (videoParam == null) {
            return Uri.parse(mDefaultUrl);
        }

        String fullUrl = String.format(template, videoParam);
        return Uri.parse(fullUrl);
    }
}
