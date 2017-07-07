package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.helpers.VideoInfoParser;
import okhttp3.Response;

import java.io.InputStream;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final String[] mDevicesToProcess = {
            "mbx reference board (g18ref) (g18ref)",
            "mitv3s-43 (hancock)",
            "MiTV4 (pulpfiction)"
            //"mibox_mini (forrestgump)"
    };
    private Boolean mCachedDeviceMatchResult = null;


    public OpenExternalPlayerInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        // trying to manipulate with video formats
        if (url.contains("get_video_info")) {
            if (mCachedDeviceMatchResult == null) {
                mCachedDeviceMatchResult = Helpers.deviceMatch(mDevicesToProcess);
            }
            return mCachedDeviceMatchResult;
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        pressBackButton();
        openExternalPlayer(url);

        return null;
    }

    private void pressBackButton() {
        if (!(mContext instanceof AppCompatActivity))
            return;
        AppCompatActivity activity = (AppCompatActivity) mContext;
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

    private String getVideoLink(String url) {
        Response response = doOkHttpRequest(url);
        InputStream videoInfo = response.body().byteStream();
        VideoInfoParser parser = new VideoInfoParser(videoInfo);
        return parser.getHDVideoLink();
    }

    private void openExternalPlayer(String url) {
        String videoLink = getVideoLink(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(videoLink), "video/mp4");
        mContext.startActivity(intent);
    }
}
