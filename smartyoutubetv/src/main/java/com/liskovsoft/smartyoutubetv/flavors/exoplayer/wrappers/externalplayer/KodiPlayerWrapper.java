package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer;

import android.content.Context;
import android.content.Intent;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import okhttp3.Response;

import java.io.ByteArrayInputStream;
import java.io.File;

public class KodiPlayerWrapper extends ExternalPlayerWrapper {
    private static final String TAG = KodiPlayerWrapper.class.getSimpleName();
    private static final String JSON_QUERY_TEMPLATE = "{\"jsonrpc\":\"2.0\",\"id\":\"1\"," +
            "\"method\":\"Player.Open\"," + "\"params\":{\"item\":{\"file\":\"%s\"}}}";
    private static final String STRM_FILE_TEMPLATE = "#EXTINF:0,%s\n" +
            "plugin://plugin.video.youtube/?path=/root/video&action=play_video&videoid=%s";
    private static final String STRM_FILE_NAME = "kodi-youtube-test.strm";
    private final File mStrmFile;
    private String mJsonQuery;

    protected KodiPlayerWrapper(Context context, ExoInterceptor interceptor) {
        super(context, interceptor);
        mStrmFile = new File(FileHelpers.getDownloadDir(context), STRM_FILE_NAME);
        mJsonQuery = String.format(JSON_QUERY_TEMPLATE, mStrmFile.getAbsolutePath());
    }

    protected void openExternalPlayer() {
        try {
            if (!Helpers.isPackageExists(mContext, "org.xbmc.kodi")) {
                MessageHelpers.showLongMessage(mContext, R.string.message_install_kodi);
                return;
            }

            Intent intent = new Intent();
            intent.setClassName("org.xbmc.kodi", "org.xbmc.kodi.Splash");

            Log.d(TAG, "Starting Kodi...");
            ((FragmentManager) mContext).startActivityForResult(intent, this);

            prepareStrmFile();

            Response response = postVideoData();

            if (response == null) {
                runInAsyncTask();
            } else {
                Log.d(TAG, response);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            mInterceptor.closePlayer();
        }
    }

    private void runInAsyncTask() {
        new Thread(this::postVideoDataAsync).start();
    }

    private void postVideoDataAsync() {
        try {
            Response response = null;

            for (int i = 0; i <= 5; i++) {
                Thread.sleep(3_000);
                response = postVideoData();

                if (response != null) {
                    break;
                }
            }

            if (response == null) {
                MessageHelpers.showLongMessage(mContext, R.string.message_enable_web_server);
            } else {
                Log.d(TAG, response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Response postVideoData() {
        return OkHttpHelpers.doPostOkHttpRequest("http://localhost:8080/jsonrpc", null, mJsonQuery, "Content-Type: application/json");
    }

    private void prepareStrmFile() {
        String title = mMetadata.getTitle();
        String videoId = mMetadata.getVideoId();

        String content = String.format(STRM_FILE_TEMPLATE, title, videoId);

        FileHelpers.streamToFile(new ByteArrayInputStream(content.getBytes()), mStrmFile);
    }
}
