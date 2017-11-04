package com.liskovsoft.smartyoutubetv.flavors.xwalk.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.liskovsoft.smartyoutubetv.misc.MyUrlEncodedQueryString;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.VideoInfoParser;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.YouTubeVideoInfoParser;
import okhttp3.Response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final String[] mDevicesToProcess = {
            "mbx reference board (g18ref) (g18ref)",
            "mitv3s-43 (hancock)"
            //"MiTV4 (pulpfiction)"
            //"mibox_mini (forrestgump)"
    };
    private Boolean mCachedDeviceMatchResult = null;

    public OpenExternalPlayerInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        if (url.contains("get_video_info") || url.contains("googlevideo")) {
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

        if (url.contains("get_video_info")) {
            return cleanupDashInfo(url);
        }

        pressBackButton();
        openExternalPlayer(url);

        return null;
    }

    private WebResourceResponse cleanupDashInfo(String url) {
        Response response = doOkHttpRequest(url);
        InputStream videoInfo = response.body().byteStream();
        Scanner s = new Scanner(videoInfo).useDelimiter("\\A");
        String queryUrl = s.hasNext() ? s.next() : "";
        MyUrlEncodedQueryString parsedQuery = MyUrlEncodedQueryString.parse(queryUrl);
        parsedQuery.set("adaptive_fmts", "");
        parsedQuery.set("dashmpd", "");
        ByteArrayInputStream is = new ByteArrayInputStream(parsedQuery.toString().getBytes(Charset.forName("UTF8")));
        return createResponse(response.body().contentType(), is);
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
        VideoInfoParser parser = new YouTubeVideoInfoParser(videoInfo);
        return parser.getHDVideoLink();
    }

    private void openExternalPlayer(String url) {
        String videoLink = url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(videoLink), "video/mp4");
        mContext.startActivity(intent);
    }
}
