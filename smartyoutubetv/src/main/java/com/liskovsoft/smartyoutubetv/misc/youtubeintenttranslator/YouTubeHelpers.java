package com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator;

import android.util.Log;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;

public class YouTubeHelpers {
    private static final String TAG = YouTubeHelpers.class.getSimpleName();
    /**
     * Browser: https://www.youtube.com/results?search_query=twice<br/>
     * Amazon: youtube://search?query=linkin+park&isVoice=true
     */
    private static final String[] SEARCH_KEYS = {"search_query", "query"};

    /**
     * Browser: https://www.youtube.com/results?search_query=twice<br/>
     * Amazon: youtube://search?query=linkin+park&isVoice=true
     */
    public static String extractSearchString(String url) {
        MyQueryString query = MyQueryStringFactory.parse(url);

        String result = null;

        for (String key : SEARCH_KEYS) {
            result = query.get(key);

            if (result != null) {
                break;
            }
        }

        if (result == null) {
            Log.w(TAG, "Url isn't a search string: " + url);
            return null;
        }

        return result;
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
    public static String extractChannelParamFromUrl(String url) {
        String[] patterns = {"channel/[^&\\s]*"};
        String result = Helpers.runMultiMatcher(url, patterns);

        if (result == null) {
            Log.w(TAG, "Url isn't a channel: " + url);
            return null;
        }

        return result.replace("channel/", "");
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
    public static String extractVideoIdParamFromUrl(String url) {
        String[] patterns = {"list=[^&\\s]*", "v=[^&\\s]*", "youtu.be/[^&\\s]*"};
        String result = Helpers.runMultiMatcher(url, patterns);

        if (result == null) {
            Log.w(TAG, "Url isn't a video: " + url);
            return null;
        }

        return result.replace("youtu.be/", "v=");
    }
}
