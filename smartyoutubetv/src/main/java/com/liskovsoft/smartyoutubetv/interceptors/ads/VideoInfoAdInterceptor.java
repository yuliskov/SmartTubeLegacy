package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

public class VideoInfoAdInterceptor extends RequestInterceptor {
    private static final CharSequence VIDEO_INFO_URL = "youtube.com/get_video_info?";
    private final Context mContext;

    public VideoInfoAdInterceptor(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        if (url == null) {
            return false;
        }

        return !SmartUtils.isExo(mContext) && url.contains(VIDEO_INFO_URL);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return filterVideoInfoResponse(url);
    }
}
