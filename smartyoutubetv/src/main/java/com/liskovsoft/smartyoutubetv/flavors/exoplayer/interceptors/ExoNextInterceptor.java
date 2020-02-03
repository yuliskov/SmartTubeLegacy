package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

/**
 * Intercepts url: https://www.youtube.com/youtubei/v1/next?key=AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8<br/>
 * NOTE: 'next' is fired after 'get_video_info'
 */
public class ExoNextInterceptor extends RequestInterceptor {
    private static final String TAG = ExoNextInterceptor.class.getSimpleName();
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private final LangUpdater mLang;
    private static final String POST_BODY = "{\"context\":{\"client\":{\"clientName\":\"TVHTML5\",\"clientVersion\":\"6.20180913\"," +
            "\"screenWidthPoints\":1280,\"screenHeightPoints\":720,\"screenPixelDensity\":1,\"theme\":\"CLASSIC\",\"utcOffsetMinutes\":180," +
            "\"webpSupport\":false,\"animatedWebpSupport\":false,\"tvAppInfo\":{\"appQuality\":\"TV_APP_QUALITY_LIMITED_ANIMATION\"}," +
            "\"acceptRegion\":\"UA\",\"deviceMake\":\"LG\",\"deviceModel\":\"42LA660S-ZA\",\"platform\":\"TV\",\"acceptLanguage\":\"%LANG%\"}," + "\"request" +
            "\":{\"consistencyTokenJars\":[]},\"user\":{\"enableSafetyMode\":false}},%VIDEO_DATA%\"racyCheckOk\":true," +
            "\"contentCheckOk\":true}";
    private static final String PLAYLIST_ID = "\"playlistId\":\"%PLAYLIST_ID%\",";
    private static final String VIDEO_ID = "\"videoId\":\"%VIDEO_ID%\",";
    private final String mPostBodyReal;
    private static final String NEXT_URL = "https://www.youtube.com/youtubei/v1/next";
    private String mNextUrl;

    public ExoNextInterceptor(Context context) {
        super(context);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
        mLang = new LangUpdater(context);
        mPostBodyReal = POST_BODY.replace("%LANG%", mLang.getPreferredBrowserLocale());
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    /**
     * Looking for 'key' params from the next urls:<br/>
     * https://www.youtube.com/youtubei/v1/next?key=AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8</br>
     * https://www.youtube.com/youtubei/v1/browse?key=AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8
     */
    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video metadata is intercepted successfully: " + url);

        if (mNextUrl == null) { // run once
            String nextUrlKey = Uri.parse(url).getQueryParameter("key");

            if (nextUrlKey != null) { // key found, save it
                mNextUrl = String.format("%s?key=%s", NEXT_URL, nextUrlKey);
            } else {
                mNextUrl = NEXT_URL; // It's ok. User is logged in. No key used.
            }
        }

        return null;
    }

    public VideoMetadata getMetadata(String videoId, String playlistId) {
        VideoMetadata metadata = null;

        if (mNextUrl != null) {
            InputStream response = postJsonData(mNextUrl, getBody(videoId, playlistId));

            if (response != null) {
                metadata = new JsonNextParser(response).extractVideoMetadata();
            }
        }

        return metadata;
    }

    private String getBody(String videoId, String playlistId) {
        // always presents
        String videoData = VIDEO_ID.replace("%VIDEO_ID%", videoId);

        // present only on play lists
        if (playlistId != null) {
            videoData += PLAYLIST_ID.replace("%PLAYLIST_ID%", playlistId);
        }

        String result = mPostBodyReal.replace("%VIDEO_DATA%", videoData);

        return result;
    }
}
