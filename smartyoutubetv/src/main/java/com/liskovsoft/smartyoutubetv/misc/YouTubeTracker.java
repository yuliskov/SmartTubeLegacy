package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.os.Handler;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;

public class YouTubeTracker {
    private static final String TAG = YouTubeTracker.class.getSimpleName();
    private static final String HISTORY_URL = "youtube.com/api/stats/watchtime";
    private final Context mContext;
    private static final String toAppend =
            "&ver=2&referrer=https%3A%2F%2Fwww.youtube.com%2Ftv&cmt=0&fmt=137&fs=0&rt=292.768&euri=https%3A%2F%2Fwww" +
            ".youtube.com%2Ftv%23%2Fwatch%2Fvideo%2Fidle%3Fv%3DPugNThnZVF0%26resume&lact=485&state=paused&volume=100&c=TVHTML5&cver=6" +
            ".20180807&cplayer=UNIPLAYER&cbrand=LG&cbr=Safari&cbrver&ctheme=CLASSIC&cmodel=42LA660S-ZA&cnetwork&cos&cosver&cplatform=TV" +
            "&final=1&hl=ru_RU&cr=UA&feature=g-topic-rch&afmt=140&idpj=-8&ldpj=-2&muted=0&st=13.347&et=13.347&conn=1&el=leanback";
    private final HeaderManager mManager;

    public YouTubeTracker(Context context) {
        mContext = context;
        mManager = new HeaderManager(mContext);
    }

    public void track(String trackingUrl, String videoUrl) {
        track(trackingUrl, videoUrl, 600, 1000);
    }

    public void track(String trackingUrl, String videoUrl, float length) {
        track(trackingUrl, videoUrl, length, length);
    }

    public void track(String trackingUrl, String videoUrl, float watched, float length) {
        if (checkUrl(trackingUrl)) {
            Log.d(TAG, String.format("Start history tracking: %s, %s, %s, %s", trackingUrl, videoUrl, watched, length));
            HashMap<String, String> headers = mManager.getHeaders();
            final String fullTrackingUrl = processUrl(trackingUrl, videoUrl, watched, length);
            Log.d(TAG, "Full tracking url: " + fullTrackingUrl);
            Log.d(TAG, "Tracking headers: " + headers);
            new Thread(() -> {  // avoid NetworkOnMainThreadException
                Response response = OkHttpHelpers.doGetOkHttpRequest(fullTrackingUrl, headers);
                Log.d(TAG, "Tracking response: " + response);
            }).start();
        } else {
            Log.d(TAG, "This tracking url isn't supported: " + trackingUrl);
        }
    }

    private boolean checkUrl(String trackingUrl) {
        return trackingUrl.contains(HISTORY_URL);
    }

    private String processUrl(String url, String videoUrl, float watched, float length) {
        MyQueryString result = MyQueryStringFactory.parse(url + toAppend);
        MyQueryString videoInfo = MyQueryStringFactory.parse(videoUrl);

        result.remove("fexp");
        result.remove("plid");
        result.remove("subscribed");

        // video id???
        String cpn = "cpn";
        result.set(cpn, videoInfo.get(cpn));
        // length of the video
        result.set("len", length);
        // watch time in seconds
        result.set("cmt", watched);
        result.set("st", String.format("%s,%s", 0, watched));
        result.set("et", String.format("%s,%s", 0, watched));

        return result.toString();
    }
}
