package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.SwitchResolutionEvent;
import com.liskovsoft.smartyoutubetv.events.VideoFormatEvent;
import com.liskovsoft.smartyoutubetv.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.helpers.VideoFormat;
import com.liskovsoft.smartyoutubetv.helpers.VideoInfoBuilder;
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

        //url = fix4KSupport(url);

        Response response = doOkHttpRequest(url);
        VideoInfoBuilder videoInfoBuilder = new VideoInfoBuilder(response.body().byteStream());

        Set<VideoFormat> supportedFormats = videoInfoBuilder.getSupportedFormats();

        Browser.getBus().post(new VideoFormatEvent(supportedFormats, mSelectedFormat));

        boolean modified = videoInfoBuilder.selectFormat(mSelectedFormat);

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
        String html5 = query.getQueryParameter("html5");
        String videoId = query.getQueryParameter("video_id");
        String cpn = query.getQueryParameter("cpn");

        String path = query.getPath();
        String host = query.getHost();
        String scheme = query.getScheme();

        return scheme + "://" + host + path + "?" + "html5=" + html5 + "&video_id=" + videoId + "&cpn=" + cpn;
    }
}
