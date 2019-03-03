package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.server;

import android.net.Uri;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;

public class MyContentServer extends NanoHTTPD {
    private static final String TAG = MyContentServer.class.getSimpleName();
    private static final String STRM_DASH_CONTENT =
            "#KODIPROP:inputstreamaddon=inputstream.adaptive\n" +
            "#KODIPROP:inputstream.adaptive.manifest_type=mpd\n" +
            "%s";
    private static final String DASH_PATH = "/dash.mpd";
    private static final String STRM_PATH = "/video.strm";
    private static final String URL_PATTERN = "%s%s";
    private static final int SERVER_PORT = 8080;
    private Uri mLiveUrl;
    private String mDashContent;

    public MyContentServer() {
        super(SERVER_PORT);
        startServer(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        Log.i(TAG, String.format("\nRunning! Point your browsers to %s \n", getBaseName()));
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
            case STRM_PATH:
                return newFixedLengthResponse(getContent());
            case DASH_PATH:
                return newFixedLengthResponse(mDashContent);
        }

        String msg = "<html><body><h1>Error: unrecognized stream url</h1></body></html>\n";
        return newFixedLengthResponse(msg);
    }

    private String getContent() {
        if (mDashContent != null) {
            return String.format(STRM_DASH_CONTENT, getDashUrl());
        }

        if (mLiveUrl != null) {
            return String.format("%s", mLiveUrl);
        }

        return null;
    }

    public void setDashContent(InputStream content) {
        mDashContent = Helpers.toString(content);
        mLiveUrl = null;
    }

    public void setLiveContent(Uri url) {
        mLiveUrl = url;
        mDashContent = null;
    }

    public String getDashUrl() {
        return String.format(URL_PATTERN, getBaseName(), DASH_PATH);
    }

    public String getStrmUrl() {
        return String.format(URL_PATTERN, getBaseName(), STRM_PATH);
    }

    private String getBaseName() {
        return String.format("http://localhost:%s", getListeningPort());
    }
}
