package com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class YouTubeHelpers {
    private static final String TAG = YouTubeHelpers.class.getSimpleName();
    /**
     * Browser: https://www.youtube.com/results?search_query=twice<br/>
     * Amazon: youtube://search?query=linkin+park&isVoice=true
     */
    private static final String[] SEARCH_KEYS = {"search_query", "query"};
    private static final String CHANNEL_URL = "/channel/";
    private static final String USER_URL = "/user/";

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

    public static boolean isBrowseIntent(Intent intent) {
        return isChannelIntent(intent) || isSearchIntent(intent);
    }

    public static boolean isChannelIntent(Intent intent) {
        return isKeyIntent(intent, CHANNEL_URL, USER_URL);
    }

    public static boolean isSearchIntent(Intent intent) {
        return isKeyIntent(intent, SEARCH_KEYS);
    }

    private static boolean isKeyIntent(Intent intent, String... keys) {
        String data = getViewData(intent);

        boolean result = false;

        for (String key : keys) {
            result = data != null && data.contains(key);

            if (result) {
                break;
            }
        }

        return result;
    }

    private static String getViewData(Intent intent) {
        String result = null;

        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            result = intent.getDataString();
        }

        return result;
    }

    /**
     * Unlock age restricted videos but locks some streams (use carefully)
     */
    public static void unlockAgeRestrictedVideos(MyUrlEncodedQueryString query) {
        query.remove("el"); // unlock age restricted videos but locks some streams (use carefully)
    }

    /**
     * Unlock age restricted videos but locks some streams (use carefully)
     */
    public static void unlockAgeRestrictedVideos2(MyUrlEncodedQueryString query) {
        removeUnusedParams(query);

        query.set("eurl", "https://youtube.googleapis.com/v/" + query.get("video_id"));
        query.set("sts", query.get("sts"));
        query.set("ps", "default");
        query.set("gl", "US");
        query.set("hl", "en");
        query.remove("access_token");
    }

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     */
    public static void unlockLiveStreams(MyUrlEncodedQueryString query) {
        //query.remove("access_token"); // needed to unlock some personal uploaded videos
        //query.set("el", "leanback");
        //query.set("ps", "leanback");

        // NOTE: don't unlock streams in video_info_interceptor.js
        // otherwise you'll get errors in youtube client
        query.set("c", "HTML5"); // needed to unlock streams
    }

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     */
    public static void unlock60FpsFormats(MyUrlEncodedQueryString query) {
        query.set("el", "info"); // unlock dashmpd url
        query.set("ps", "default"); // unlock 60fps formats
    }

    /**
     * Minimal url: https://www.youtube.com/get_video_info?video_id=<id>&eurl=https://youtube.googleapis.com/v/<id>&sts=<sts>&ps=default&gl=US&hl=en
     */
    public static void removeUnusedParams(MyUrlEncodedQueryString query) {
        // https://www.youtube.com/get_video_info?video_id=<id>&eurl=https://youtube.googleapis.com/v/<id>&sts=<sts>&ps=default&gl=US&hl=en
        // should remain only: video_id, eurl, sts, access_token (?), ps

        query.remove("cpn");
        query.remove("itct");
        query.remove("ei");
        query.remove("hl");
        query.remove("lact");
        query.remove("cos");
        query.remove("cosver");
        query.remove("cplatform");
        query.remove("width");
        query.remove("height");
        query.remove("cbrver");
        query.remove("ctheme");
        query.remove("cmodel");
        query.remove("cnetwork");
        query.remove("c");
        query.remove("cver");
        query.remove("cplayer");
        query.remove("cbrand");
        query.remove("cbr");
        query.remove("el");
        query.remove("ps");
    }
}
