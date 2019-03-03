package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
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
    private final Context mContext;
    private static final String toAppend =
            "&ver=2&referrer=https%3A%2F%2Fwww.youtube.com%2Ftv&cmt=0&fmt=137&fs=0&rt=292.768&euri=https%3A%2F%2Fwww" +
            ".youtube.com%2Ftv%23%2Fwatch%2Fvideo%2Fidle%3Fv%3DPugNThnZVF0%26resume&lact=485&state=paused&volume=100&c=TVHTML5&cver=6" +
            ".20180807&cplayer=UNIPLAYER&cbrand=LG&cbr=Safari&cbrver&ctheme=CLASSIC&cmodel=42LA660S-ZA&cnetwork&cos&cosver&cplatform=TV" +
            "&final=1&hl=ru_RU&cr=UA&feature=g-topic-rch&afmt=140&idpj=-8&ldpj=-2&muted=0&st=13.347&et=13.347&conn=1";

    public YouTubeTracker(Context context) {
        mContext = context;
    }

    public void track(String trackingUrl, String videoUrl) {
        if (!trackingUrl.contains("youtube.com/api/stats/watchtime")) {
            Log.d(TAG, "This tracking url isn't supported: " + trackingUrl);
            return;
        }

        if (trackingUrl.contains("youtube.com/api/stats/watchtime")) {
            Map<String, String> headers = getHeaders();
            trackingUrl = processUrl(trackingUrl, videoUrl);
            Log.d(TAG, "Tracking url: " + trackingUrl);
            Log.d(TAG, "Tracking headers: " + headers);
            Response response = OkHttpHelpers.doGetOkHttpRequest(trackingUrl, headers);
            Log.d(TAG, "Tracking response: " + response);
        }
    }

    private Map<String, String> getHeaders() {
        Map<String, String> result = new HashMap<>();

        SmartPreferences prefs = SmartPreferences.instance(mContext);
        result.put("Authorization", prefs.getAuthorizationHeader());
        result.put("Referer", "https://www.youtube.com/tv");
        result.put("User-Agent", "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)");
        result.put("X-YouTube-Client-Name", "TVHTML5");
        result.put("X-YouTube-Page-CL", "233168751");
        result.put("X-YouTube-Page-Label", "youtube.ytfe.desktop_20190208_2_RC0");
        result.put("X-YouTube-Utc-Offset", "120");

        return result;
    }

    private String processUrl(String url, String videoUrl) {
        MyQueryString result = MyQueryStringFactory.parse(url + toAppend);
        MyQueryString videoInfo = MyQueryStringFactory.parse(videoUrl);

        result.remove("fexp");
        result.remove("plid");
        result.remove("subscribed");

        String cpn = "cpn";
        result.set(cpn, videoInfo.get(cpn));
        result.set("el", "leanback");

        return result.toString();
    }
}
