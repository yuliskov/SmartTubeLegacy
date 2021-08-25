package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class VideoInfoAdInterceptor extends RequestInterceptor {
    private static final CharSequence VIDEO_INFO_URL = "youtube.com/get_video_info?";
    private static final CharSequence PLAYER_INFO_URL = "youtube.com/youtubei/v1/player";
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public VideoInfoAdInterceptor(Context context) {
        super(context);
        mContext = context;
        mPrefs = CommonApplication.getPreferences();
    }

    @Override
    public boolean test(String url) {
        if (url == null) {
            return false;
        }

        return !SmartUtils.isExo(mContext) && (url.contains(VIDEO_INFO_URL) || url.contains(PLAYER_INFO_URL));
    }

    @Override
    public WebResourceResponse intercept(String url) {
        WebResourceResponse result = null;

        if (url.contains(PLAYER_INFO_URL)) {
            result = filterPlayerInfoResponse(url, mPrefs.getPostData());
        } else if (url.contains(VIDEO_INFO_URL)) {
            result = filterVideoInfoResponse(url);
        }

        return result;
    }
}
