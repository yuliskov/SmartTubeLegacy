package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.server.MyContentServer;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.OnMediaFoundCallback;

import java.io.InputStream;

public class ExternalPlayerWrapper extends OnMediaFoundCallback {
    private final Context mContext;
    private final ExoInterceptor mInterceptor;
    private MyContentServer mHttpd;
    private static String STRM_URL = "http://localhost:8080/video.strm";

    public ExternalPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        initHttpd();
    }

    private void initHttpd() {
        mHttpd = new MyContentServer();
    }

    @Override
    public void onDashMPDFound(InputStream mpdContent) {
        mHttpd.setDashStream(mpdContent);
    }

    @Override
    public void onDashUrlFound(Uri dashUrl) {
        mHttpd.setLiveStream(dashUrl);
    }

    @Override
    public void onDone() {
        openExternalPlayer();
    }

    private void openExternalPlayer() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(STRM_URL), "*/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
