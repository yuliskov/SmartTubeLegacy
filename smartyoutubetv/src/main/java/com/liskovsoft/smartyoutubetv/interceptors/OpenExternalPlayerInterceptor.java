package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.VideoFormatEvent;
import com.liskovsoft.smartyoutubetv.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.helpers.VideoFormat;
import com.liskovsoft.smartyoutubetv.helpers.VideoInfoBuilder;
import com.liskovsoft.smartyoutubetv.helpers.VideoInfoParser;
import okhttp3.MediaType;
import okhttp3.Response;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private final Context mContext;

    public OpenExternalPlayerInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        // trying to manipulate with video formats
        if (url.contains("get_video_info")) {
            String model = Helpers.getDeviceName();
            switch (model.toLowerCase()) {
                case "mbx reference board (g18ref)":
                     return true;
            }
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        Response response = doOkHttpRequest(url);

        InputStream videoInfo = response.body().byteStream();

        VideoInfoParser parser = new VideoInfoParser(videoInfo);
        String hdVideoLink = parser.getHDVideoLink();

        openExternalPlayer(hdVideoLink);

        return null;
    }

    private void openExternalPlayer(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(uri), "video/mp4");
        mContext.startActivity(intent);
    }
}
