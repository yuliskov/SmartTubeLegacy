package com.liskovsoft.smartyoutubetv.flavors.exoplayer.kodi;

import android.net.Uri;
import android.util.Log;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;

public class MyHttpd extends NanoHTTPD {
    private static final String TAG = MyHttpd.class.getSimpleName();
    private static final String STRM_DASH_CONTENT =
            "#KODIPROP:inputstreamaddon=inputstream.adaptive\n" +
            "#KODIPROP:inputstream.adaptive.manifest_type=mpd\n" +
            "http://localhost:8080/dash.mpd";
    private static final String STRM_HLS_CONTENT = "%s";
    private Uri mLiveUrl;
    private String mDashContent;

    public MyHttpd() {
        super(8080);
        startServer(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        Log.i(TAG, "\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    private void startServer(int timeout, boolean daemon) {
        try {
            start(timeout, daemon);
        } catch (IOException e) {
            Log.e(TAG, "Couldn't start server:\n" + e);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        switch (session.getUri()) {
            case "/video.strm":
                return newFixedLengthResponse(getContent());
            case "/dash.mpd":
                return newFixedLengthResponse(mDashContent);
        }

        String msg = "<html><body><h1>Error: unrecognized stream url</h1></body></html>\n";
        return newFixedLengthResponse(msg);
    }

    private String getContent() {
        if (mDashContent != null) {
            return STRM_DASH_CONTENT;
        }

        if (mLiveUrl != null) {
            return String.format(STRM_HLS_CONTENT, mLiveUrl);
        }

        return null;
    }

    public void setDashStream(InputStream content) {
        mDashContent = Helpers.toString(content);
        mLiveUrl = null;
    }

    public void setLiveStream(Uri url) {
        mLiveUrl = url;
        mDashContent = null;
    }
}
