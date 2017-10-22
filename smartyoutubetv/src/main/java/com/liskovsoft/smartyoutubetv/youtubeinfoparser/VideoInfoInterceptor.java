package com.liskovsoft.smartyoutubetv.youtubeinfoparser;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.SwitchResolutionEvent;
import com.liskovsoft.smartyoutubetv.events.VideoFormatEvent;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;
import java.util.Set;

public class VideoInfoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private VideoFormat mSelectedFormat = VideoFormat._720p_;

    public VideoInfoInterceptor(Context context) {
        mContext = context;

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
        mSelectedFormat = VideoFormat.fromName(event.getFormatName());
    }


    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        //// test code, delete in production
        //if (true) {
        //    InputStream inputStream = Helpers.getAsset(mContext, "get_video_info_4k_2");
        //    return createResponse(MediaType.parse("application/x-www-form-urlencoded"), inputStream);
        //}

        //url = unlockAllFormats(url);

        Response response = doOkHttpRequest(url);
        VideoInfoBuilder videoInfoBuilder = new YouTubeVideoInfoBuilder(response.body().byteStream());

        Set<VideoFormat> supportedFormats = videoInfoBuilder.getSupportedFormats();

        Browser.getBus().post(new VideoFormatEvent(supportedFormats, mSelectedFormat));

        videoInfoBuilder.selectFormat(mSelectedFormat);

        InputStream is = videoInfoBuilder.get();

        return createResponse(response.body().contentType(), is);
    }
}
