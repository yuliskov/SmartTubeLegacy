package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

public class ExoNextInterceptor extends RequestInterceptor {
    private static final String TAG = ExoNextInterceptor.class.getSimpleName();
    public static final String URL_NEXT_DATA = "youtubei/v1/next";
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private InputStream mResponseStream;
    private static final String POST_BODY = "{\"context\":{\"client\":{\"clientName\":\"TVHTML5\",\"clientVersion\":\"6.20180913\"," +
            "\"screenWidthPoints\":1280,\"screenHeightPoints\":720,\"screenPixelDensity\":1,\"theme\":\"CLASSIC\",\"utcOffsetMinutes\":180," +
            "\"webpSupport\":false,\"animatedWebpSupport\":false,\"tvAppInfo\":{\"appQuality\":\"TV_APP_QUALITY_LIMITED_ANIMATION\"}," +
            "\"acceptRegion\":\"UA\",\"deviceMake\":\"LG\",\"deviceModel\":\"42LA660S-ZA\",\"platform\":\"TV\"}," + "\"request" +
            "\":{\"consistencyTokenJars\":[]},\"user\":{\"enableSafetyMode\":false}},\"videoId\":\"0noUsmTB3_g\",\"racyCheckOk\":true," +
            "\"contentCheckOk\":true}";

    public ExoNextInterceptor(Context context) {
        super(context);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video metadata is intercepted successfully");

        mResponseStream = getUrlData(url);

        return null;
    }

    public VideoMetadata getMetadata() {
        if (mResponseStream == null) {
            return null;
        }

        return new JsonNextParser(Helpers.toString(mResponseStream)).extractVideoMetadata();
    }
}
