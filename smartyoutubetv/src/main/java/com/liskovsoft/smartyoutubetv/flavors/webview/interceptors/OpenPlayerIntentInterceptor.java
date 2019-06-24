package com.liskovsoft.smartyoutubetv.flavors.webview.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import okhttp3.Response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

public class OpenPlayerIntentInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final String[] mDevicesToProcess = {
            //"mibox_mini (forrestgump)",
            //"MiTV4 (pulpfiction)",
            "mbx reference board (g18ref) (g18ref)",
            "mitv3s-43 (hancock)"
    };
    private Boolean mCachedDeviceMatchResult = null;

    public OpenPlayerIntentInterceptor(Context context) {
        super(context);

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
        Response response = OkHttpHelpers.doOkHttpRequest(url);

        if (response == null) // network error
            return null;

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

    private void openExternalPlayer(String url) {
        String videoLink = url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(videoLink), "video/mp4");
        mContext.startActivity(intent);
    }
}
