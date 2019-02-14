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
    private MyContentServer mServer;

    public ExternalPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        initServer();
    }

    private void initServer() {
        mServer = new MyContentServer();
    }

    @Override
    public void onDashMPDFound(InputStream mpdContent) {
        mServer.setDashStream(mpdContent);
    }

    @Override
    public void onDashUrlFound(Uri dashUrl) {
        mServer.setLiveStream(dashUrl);
    }

    @Override
    public void onDone() {
        openExternalPlayer();
    }

    private void openExternalPlayer() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(mServer.getDashUrl()), "video/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
