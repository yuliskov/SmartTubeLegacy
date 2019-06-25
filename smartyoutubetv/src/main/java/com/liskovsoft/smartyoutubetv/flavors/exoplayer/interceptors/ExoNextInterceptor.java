package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

public class ExoNextInterceptor extends RequestInterceptor {
    private static final String TAG = ExoNextInterceptor.class.getSimpleName();
    private static final String URL_NEXT_DATA = "https://www.youtube.com/youtubei/v1/next";
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private final LangUpdater mLang;
    private static final String POST_BODY = "{\"context\":{\"client\":{\"clientName\":\"TVHTML5\",\"clientVersion\":\"6.20180913\"," +
            "\"screenWidthPoints\":1280,\"screenHeightPoints\":720,\"screenPixelDensity\":1,\"theme\":\"CLASSIC\",\"utcOffsetMinutes\":180," +
            "\"webpSupport\":false,\"animatedWebpSupport\":false,\"tvAppInfo\":{\"appQuality\":\"TV_APP_QUALITY_LIMITED_ANIMATION\"}," +
            "\"acceptRegion\":\"UA\",\"deviceMake\":\"LG\",\"deviceModel\":\"42LA660S-ZA\",\"platform\":\"TV\",\"acceptLanguage\":\"%LANG%\"}," + "\"request" +
            "\":{\"consistencyTokenJars\":[]},\"user\":{\"enableSafetyMode\":false}},\"videoId\":\"%VIDEO_ID%\",\"racyCheckOk\":true," +
            "\"contentCheckOk\":true}";

    public ExoNextInterceptor(Context context) {
        super(context);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
        mLang = new LangUpdater(context);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video metadata is intercepted successfully");

        return null;
    }

    public VideoMetadata getMetadata(String videoId) {
        InputStream response = postUrlData(URL_NEXT_DATA, POST_BODY.replace("%VIDEO_ID%", videoId).replace("%LANG%", mLang.getPreferredBrowserLocale()));

        if (response == null) {
            return null;
        }

        VideoMetadata metadata = new JsonNextParser(Helpers.toString(response)).extractVideoMetadata();

        return metadata;
    }
}
