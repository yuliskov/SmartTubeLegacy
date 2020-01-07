package com.liskovsoft.smartyoutubetv.misc.youtubeutils;

import android.content.Context;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.misc.HeaderManager;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;
import okhttp3.Response;

import java.util.HashMap;

public class YouTubeHistoryUpdater {
    private static final String TAG = YouTubeHistoryUpdater.class.getSimpleName();
    private static final String LEN = "len";
    private static final String CMT = "cmt";
    private static final String ST = "st";
    private static final String ET = "et";
    private final Context mContext;
    private final HeaderManager mManager;

    public YouTubeHistoryUpdater(Context context) {
        mContext = context;
        mManager = new HeaderManager(mContext);
    }

    public void sync(String trackingUrl, float position, float length) {
        Log.d(TAG, String.format("Start history updating: %s, position: %s, length: %s", trackingUrl, position, length));
        HashMap<String, String> headers = mManager.getHeaders();
        final String fullTrackingUrl = processUrl(trackingUrl, position, length);
        Log.d(TAG, "Composed tracking url: " + fullTrackingUrl);
        //Log.d(TAG, "Tracking headers: " + headers);
        new Thread(() -> {  // avoid NetworkOnMainThreadException
            Response response = OkHttpHelpers.doGetOkHttpRequest(fullTrackingUrl, headers);
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
        //result.set(ST, String.format("%s,%s", 0, watched - 2)); // ???
        result.set(ET, position);
        //result.set(ET, String.format("%s,%s", 0, watched));

        return result.toString();
    }
}
