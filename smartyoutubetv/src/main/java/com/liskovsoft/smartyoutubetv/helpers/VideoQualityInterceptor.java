package com.liskovsoft.smartyoutubetv.helpers;

import android.webkit.WebResourceResponse;
import okhttp3.Response;
import java.io.InputStream;

public class VideoQualityInterceptor extends RequestInterceptor {
    @Override
    public boolean test(String url) {
        // trying to manipulate with video formats
        if (url.contains("get_video_info")) {
            return true;
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        Response response = doOkHttpRequest(url);
        VideoInfoBuilder videoInfoBuilder = new VideoInfoBuilder(response.body().byteStream());
        videoInfoBuilder.removeSDFormats();
        //videoInfoBuilder.removeFormat(248);
        //videoInfoBuilder.removeFormat(137);
        //videoInfoBuilder.removeFormat(247);
        //videoInfoBuilder.removeFormat(136);
        //videoInfoBuilder.removeFormat(244);
        //videoInfoBuilder.removeFormat(135);
        InputStream is = videoInfoBuilder.get();

        WebResourceResponse resourceResponse = new WebResourceResponse(
                getMimeType(response.body().contentType()),
                getCharset(response.body().contentType()),
                is
        );
        return resourceResponse;
    }
}
