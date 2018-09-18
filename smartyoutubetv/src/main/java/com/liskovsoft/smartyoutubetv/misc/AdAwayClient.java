package com.liskovsoft.smartyoutubetv.misc;

import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

public class AdAwayClient {
    /**
     * Couple of ads have been taken from <a href="https://www.reddit.com/r/dropgoogle/comments/5tnjxl/block_youtube_ads_2017_hosts_file/">this post</a>
     */
    private static final String[] mAdAwayList = {
            "google.com/ads/user-lists", // ???? (blocked in Chrome's uBlock)
            "youtube.com/pagead/viewthroughconversion", // ???? (blocked in Chrome's uBlock)
            "youtube.com/api/stats/delayplay", // ???? (blocked in Chrome's uBlock)
            "youtube.com/csi_204", // ???? (blocked in Chrome's uBlock)
            "youtube.com/ptracking", // ???? (blocked in Chrome's uBlock)
            "youtube.com/api/stats/playback", // ???? (blocked in Chrome's uBlock)
            "youtube-nocookie.com/device_204", // ???? (blocked in Chrome's uBlock)
            "youtube.com/api/stats/qoe", // ???? (blocked in Chrome's uBlock)
            "youtube.com/get_midroll_info", // middle playback ads
            "youtube.com/get_video_info*AdSense-Viral", // note: wildcard usage
            "youtube.com/api/stats/ads",
            "doubleclick.net",
            "doubleclickbygoogle.com",
            "2mdn.net",
            "googleadservices.com",
            "googlesyndication.com",
            "video-stats.video.google.com",
            // match multiple domains like adservice.google.ca
            "adservice.google", 
            "adservices.google",
            "imasdk.googleapis.com",
            "s2.youtube.com",
            "s.youtube.com",
            "ad.youtube.com",
            "ads.youtube.com",
            "pagead-googlehosted.l.google.com",
            "pagead.l.google.com",
            "partnerad.l.google.com",
            "exoclick.com",
            "youtube.112.2o7.net",
            "www.adwords.google.lloymlincs.com",
            "ads.adadapted.com",
            "gotoyoutube.us",
            "unblockyoutube.biz",
            "unblockyoutubeproxy.com",
            "unblockyoutube.tv",
            "youtube-proxy.info",
            "youtuberid.com",
            "pixel.moatads.com",
            "rtd.tubemogul.com",
            "files.adform.net",
            "fwmrm.net",
            "secure-ds.serving-sys.com",
            "contentabc.com",
            "innovid.com"
    };


    public static boolean isAd(String url) {
        for (String suburl : mAdAwayList) {
            boolean contains = Helpers.matchSubstr(url, suburl);
            if (contains)
                return true;
        }
        return false;
    }

}
