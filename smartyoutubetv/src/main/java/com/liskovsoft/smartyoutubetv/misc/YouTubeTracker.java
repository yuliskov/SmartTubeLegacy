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
    private final UserAgentManager mUA;
    private Map<String, String> mHeaders;

    public YouTubeTracker(Context context) {
        mContext = context;
        mUA = new UserAgentManager();
    }

    public void track(String trackingUrl, String videoUrl) {
        track(trackingUrl, videoUrl, 600, 1000);
    }

    public void track(String trackingUrl, String videoUrl, float watched, float length) {
        if (checkUrl(trackingUrl)) {
            Map<String, String> headers = getHeaders();
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

    private Map<String, String> getHeaders() {
        if (mHeaders != null) {
            return mHeaders;
        }

        Map<String, String> result = new HashMap<>();

        SmartPreferences prefs = SmartPreferences.instance(mContext);
        String authorization = prefs.getAuthorizationHeader();
        if (authorization.isEmpty()) {
            return result;
        }

        result.put("Authorization", authorization);
        result.put("Referer", "https://www.youtube.com/tv");
        result.put("User-Agent", mUA.getUA());
        result.put("X-YouTube-Client-Name", "TVHTML5");
        result.put("X-YouTube-Page-CL", "233168751");
        result.put("X-YouTube-Page-Label", "youtube.ytfe.desktop_20190208_2_RC0");
        result.put("X-YouTube-Utc-Offset", "120");

        mHeaders = result;

        return result;
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
        // watch time in seconds
        result.set("cmt", watched);
        // length of the video
        result.set("len", length);
        //result.set("st", length);
        //result.set("et", length);
        //result.set("rt", length);

        return result.toString();
    }
}
