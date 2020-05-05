package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;

public class AdAwayClient {

    private static final String TAG = AdAwayClient.class.getSimpleName();

    /**
     * Couple of ads have been taken from <a href="https://www.reddit.com/r/dropgoogle/comments/5tnjxl/block_youtube_ads_2017_hosts_file/">this post</a>
     */
    private final String[] mAdList;

    public AdAwayClient(Context context) {
        mAdList = CommonParams.instance(context).getAdsUrls();
    }

    /**
     * Check contains Ad into the URL
     *
     * @param url source URL
     * @return {@code true} if URL contains Ad, {@code false} otherwise
     * @throws {@link NullPointerException} if param {@code url} is {@code null}
     */
    public boolean isAd(String url) {
        if (mAdList == null) {
            return false;
        }

        for (String subUrl : mAdList) {
            boolean contains = url.contains(subUrl);
            if (contains) {
                Log.d(TAG, "Ads blocked: " + url);
                return true;
            }
        }

        return false;
    }

}
