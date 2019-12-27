package com.liskovsoft.smartyoutubetv.misc.youtubeutils;

import android.content.Context;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.misc.HeaderManager;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;
import okhttp3.Response;

import java.util.HashMap;

public class YouTubeTracker {
    private static final String TAG = YouTubeTracker.class.getSimpleName();
    private static final String HISTORY_URL = "youtube.com/api/stats/watchtime";
    private static final String DOC_ID = "docid";
    private static final String EI = "ei";
    private static final String CPN = "cpn";
    private static final String OF = "of";
    private static final String VM = "vm";
    private static final String LEN = "len";
    private static final String CMT = "cmt";
    private static final String ST = "st";
    private static final String ET = "et";
    private static final String CL = "cl";
    private static final String LACT = "lact";
    private final Context mContext;
    private static final String sUrlTemplate = "https://www.youtube.com/api/stats/watchtime?ns=yt&el=leanback&cpn=_YBRc8b3mTG7TxFG&docid" +
            "=ti_gH1pA4gM&ver=2&referrer=https%3A%2F%2Fwww.youtube.com%2Ftv&cmt=20&ei=eINEXcOXJpTt7QTZ6LuoBA&fmt=136&fs=0&rt=164" +
            ".854&of=Bq_4EmPSblOd4rZYcOQrdg&euri=https%3A%2F%2Fwww.youtube" +
            ".com%2Ftv%23%2Fwatch%2Fvideo%2Fidle%3Fv%3Dfm7kbQkG9mA%26resume&lact=750&cl=260482851&state=paused&vm" +
            "=CAEQARgEKiBITWo5SXpCYUVHNzJXbXVBVl9nWHBfNjJjWFlNQlpKZw&volume=100&subscribed=1&c=TVHTML5&cver=6" +
            ".20180913&cplayer=UNIPLAYER&cbrand=LG&cbr=Safari&cbrver&ctheme=CLASSIC&cmodel=42LA660S-ZA&cnetwork&cos&cosver&cplatform=TV&final=1&hl" +
            "=en_US&cr=UA&len=647&feature=history&afmt=140&idpj=-8&ldpj=-13&muted=0&st=211&et=211&conn=1";
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
            headers.remove("Cookie");
            headers.remove("Accept-Language");
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

    private String processUrl(String trackUrl, String videoUrl, float watched, float length) {
        MyQueryString result = MyQueryStringFactory.parse(sUrlTemplate);
        MyQueryString trackInfo = MyQueryStringFactory.parse(trackUrl);
        MyQueryString videoInfo = MyQueryStringFactory.parse(videoUrl);

        result.set(DOC_ID, trackInfo.get(DOC_ID));
        result.set(OF, trackInfo.get(OF));
        //result.set(VM, trackInfo.get(VM));
        result.set(CL, trackInfo.get(CL));
        result.set(EI, videoInfo.get(EI));
        result.set(CPN, videoInfo.get(CPN));
        result.set(LACT, videoInfo.get(LACT));

        // length of the video
        result.set(LEN, length);
        // watch time in seconds
        result.set(CMT, watched);
        result.set(ST, watched); // ???
        //result.set(ST, String.format("%s,%s", 0, watched - 2)); // ???
        result.set(ET, watched);
        //result.set(ET, String.format("%s,%s", 0, watched));

        return result.toString();
    }
}
