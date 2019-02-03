package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.common.prefs.CommonParams;

public class AdAwayClient {
    private final Context mContext;
    /**
     * Couple of ads have been taken from <a href="https://www.reddit.com/r/dropgoogle/comments/5tnjxl/block_youtube_ads_2017_hosts_file/">this post</a>
     */
    private final String[] mAdList;

    public AdAwayClient(Context context) {
        mContext = context;
        mAdList = new CommonParams(context).getAdUrls();
    }

    public boolean isAd(String url) {
        for (String subUrl : mAdList) {
            boolean contains = Helpers.matchSubstr(url, subUrl);
            if (contains) {
                return true;
            }
        }

        return false;
    }

}
