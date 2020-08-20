package com.liskovsoft.smartyoutubetv.misc.youtubeutils;

import android.content.Context;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.misc.HeaderManager;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;

import java.util.HashMap;

import okhttp3.Response;

public class YouTubeHistoryUpdater {
    private static final String TAG = YouTubeHistoryUpdater.class.getSimpleName();
    private static final String LEN = "len";
    private static final String CMT = "cmt";
    private static final String ST = "st";
    private static final String ET = "et";
    private final HeaderManager mManager;

    public YouTubeHistoryUpdater(Context context) {
        mManager = new HeaderManager(context);
    }

    public void sync(String trackingUrl, float position, float length) {
        Log.d(TAG, String.format("Start history updating: %s, position: %s, length: %s", trackingUrl, position, length));
        HashMap<String, String> headers = mManager.getHeaders();

        String authorization = headers.get("Authorization");

        if (authorization == null) {
            Log.e(TAG, "Error: Authorization not found!");
            return;
        }

        // TODO: for testing only!
        HashMap<String, String> testHeaders = new HashMap<>();
        testHeaders.put("Authorization", authorization);

        final String fullTrackingUrl = processUrl(trackingUrl, position, length);
        Log.d(TAG, "Composed tracking url: " + fullTrackingUrl);
        //Log.d(TAG, "Tracking headers: " + headers);
        new Thread(() -> {  // avoid NetworkOnMainThreadException
            Response response = OkHttpHelpers.doGetOkHttpRequest(fullTrackingUrl.replace("api/stats/watchtime?", "api/stats/playback?"), testHeaders);

            if (response == null || !response.isSuccessful()) {
                Log.e(TAG, "Bad tracking response: " + response);
            }

            response = OkHttpHelpers.doGetOkHttpRequest(fullTrackingUrl, testHeaders);

            if (response == null || !response.isSuccessful()) {
                Log.e(TAG, "Bad tracking response: " + response);
            }
        }).start();
    }

    private String processUrl(String trackingUrl, float position, float length) {
        MyQueryString result = MyQueryStringFactory.parse(trackingUrl);

        // only for testing
        if (length == 0) {
            length = result.getFloat(LEN);
        }

        if (position == 0) {
            position = length;
        }

        // length of the video
        result.set(LEN, length);
        // watch time in seconds
        result.set(CMT, position);
        result.set(ST, position); // the same. why?
        result.set(ET, position); // the same. why?
        //result.set(ST, String.format("%s,%s", 0, watched - 2)); // ???
        //result.set(ET, String.format("%s,%s", 0, watched));

        // TODO: for testing only!
        //result.remove("cpn"); // required!
        //result.remove("ei"); // required!
        //result.remove("ns"); // required!
        //result.remove("ver"); // required!
        //result.remove("final"); // required!
        //result.remove("vm"); // required!
        result.remove("el"); // can be removed!
        result.remove("referrer"); // can be removed!
        result.remove("fmt"); // can be removed!
        result.remove("fs"); // can be removed!
        result.remove("rt"); // can be removed!
        result.remove("of"); // can be removed!
        result.remove("euri"); // can be removed!
        result.remove("lact"); // can be removed!
        result.remove("cl"); // can be removed!
        result.remove("state"); // can be removed!
        result.remove("volume"); // can be removed!
        result.remove("c");  // can be removed!
        result.remove("cver");  // can be removed!
        result.remove("cplayer");  // can be removed!
        result.remove("cbrand");  // can be removed!
        result.remove("cbr");  // can be removed!
        result.remove("cbrver");  // can be removed!
        result.remove("ctheme");  // can be removed!
        result.remove("cmodel");  // can be removed!
        result.remove("cnetwork"); // can be removed!
        result.remove("cos");  // can be removed!
        result.remove("cosver");  // can be removed!
        result.remove("cplatform"); // can be removed!
        result.remove("hl"); // can be removed!
        result.remove("cr"); // can be removed!
        result.remove("rtn"); // can be removed!
        result.remove("feature"); // can be removed!
        result.remove("afmt"); // can be removed!
        result.remove("idpj"); // can be removed!
        result.remove("ldpj"); // can be removed!
        result.remove("muted"); // can be removed!
        result.remove("subscribed"); // can be removed!
        result.remove("rti"); // can be removed!
        result.remove("conn"); // can be removed!

        return result.toString();
    }
}
