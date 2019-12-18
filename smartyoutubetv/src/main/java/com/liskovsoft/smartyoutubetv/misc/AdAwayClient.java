package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;

public class AdAwayClient {
    private static final String TAG = AdAwayClient.class.getSimpleName();
    private final Context mContext;
    /**
     * Couple of ads have been taken from <a href="https://www.reddit.com/r/dropgoogle/comments/5tnjxl/block_youtube_ads_2017_hosts_file/">this post</a>
     */
    private final String[] mAdList;

    public AdAwayClient(Context context) {
        mContext = context;
        mAdList = CommonParams.instance(context).getAdsUrls();
    }

    public boolean isAd(String url) {
        if (mAdList != null) {
            for (String subUrl : mAdList) {
                boolean contains = Helpers.matchSubstr(url, subUrl);
                if (contains) {
                    Log.d(TAG, "Ads blocked: " + url);
                    return true;
                }
            }
        }

        return false;
    }

}
