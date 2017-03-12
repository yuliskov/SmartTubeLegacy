package com.liskovsoft.smartyoutubetv.interceptors;

import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.SwitchResolutionEvent;
import com.liskovsoft.smartyoutubetv.events.VideoFormatEvent;
import com.liskovsoft.smartyoutubetv.helpers.VideoFormat;
import com.liskovsoft.smartyoutubetv.helpers.VideoInfoBuilder;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;
import java.util.Set;

public class VideoInfoInterceptor extends RequestInterceptor {
    private String mFormatName;

    public VideoInfoInterceptor() {
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
        mFormatName = event.getFormatName();
    }


    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        url = fix4KSupport(url);

        Response response = doOkHttpRequest(url);
        VideoInfoBuilder videoInfoBuilder = new VideoInfoBuilder(response.body().byteStream());

        Set<VideoFormat> supportedFormats = videoInfoBuilder.getSupportedFormats();

        Browser.getBus().post(new VideoFormatEvent(supportedFormats, VideoFormat.fromName(mFormatName)));

        boolean modified = videoInfoBuilder.selectFormat(VideoFormat.fromName(mFormatName));

        InputStream is = videoInfoBuilder.get();

        if (!modified) {
            return null;
        }

        return createResponse(response.body().contentType(), is);
    }

    /**
     * Query all available formats (including 4K MP4). By default format list is restricted.
     * @param url
     * @return
     */
    private String fix4KSupport(String url) {
        Uri query = Uri.parse(url);
        String videoId = query.getQueryParameter("video_id");

        String path = query.getPath();
        String host = query.getHost();
        String scheme = query.getScheme();

        return scheme + "://" + host + path + "?video_id=" + videoId;
    }
}
