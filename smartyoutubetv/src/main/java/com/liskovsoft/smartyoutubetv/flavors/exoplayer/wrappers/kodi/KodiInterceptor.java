package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.kodi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SyncButtonsCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.DelayedCommandCallInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.server.MyContentServer;

import java.io.InputStream;
import java.util.HashMap;

public class KodiInterceptor extends MPDExtractInterceptor {
    private final Context mContext;
    private final DelayedCommandCallInterceptor mInterceptor;
    private MyContentServer mHttpd;
    private static String STRM_URL = "http://localhost:8080/video.strm";

    public KodiInterceptor(Context context, DelayedCommandCallInterceptor interceptor) {
        super(context);
        mContext = context;
        mInterceptor = interceptor;
        initHttpd();
    }

    private void initHttpd() {
        mHttpd = new MyContentServer();
    }

    @Override
    protected void onDashMPDFound(InputStream mpdContent) {
        mHttpd.setDashContent(mpdContent);
        closeWebPlayerWindow();
        openKodi();
    }

    @Override
    protected void onLiveUrlFound(Uri hlsUrl) {
        mHttpd.setLiveContent(hlsUrl);
        closeWebPlayerWindow();
        openKodi();
    }

    public void updateLastCommand(GenericCommand command) {
        mInterceptor.setCommand(command);
        // force call command without adding to the history (in case WebView)
        mInterceptor.forceRun(false);
    }

    private void openKodi() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(STRM_URL), "*/*");
        intent.setClassName("org.xbmc.kodi", "org.xbmc.kodi.Splash");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void closeWebPlayerWindow() {
        // Web-player's window not needed anymore. Close it.
        HashMap<String, Object> map = new HashMap<>();
        map.put(ExoPlayerFragment.BUTTON_BACK, true);
        updateLastCommand(new SyncButtonsCommand(map));
    }
}
