package com.liskovsoft.smartyoutubetv.interceptors;

import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.SupportedVideoFormatsEvent;
import com.liskovsoft.smartyoutubetv.events.SwitchResolutionEvent;
import com.liskovsoft.smartyoutubetv.helpers.VideoInfoBuilder;
import com.squareup.otto.Subscribe;
import okhttp3.Response;
import java.io.InputStream;

public class VideoQualityInterceptor extends RequestInterceptor {
    private String mItag;

    public VideoQualityInterceptor() {
        Browser.getBus().register(this);
    }

    @Override
    public boolean test(String url) {
        // trying to manipulate with video formats
        if (url.contains("get_video_info")) {
            return true;
        }
        return false;
    }

    @Subscribe
    public void setDesiredResolution(SwitchResolutionEvent event) {
        mItag = event.getItag();
    }

    public void setDesiredResolution(String itag) {
        mItag = itag;
    }


    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        //if (mItag == null) {
        //    return null;
        //}

        Response response = doOkHttpRequest(url);
        VideoInfoBuilder videoInfoBuilder = new VideoInfoBuilder(response.body().byteStream());

        //String formats = videoInfoBuilder.getSupportedFormats();
        //Browser.getBus().post(new SupportedVideoFormatsEvent(formats));

        videoInfoBuilder.switchToFormat(mItag);

        InputStream is = videoInfoBuilder.get();


        return createResponse(response.body().contentType(), is);
    }
}
