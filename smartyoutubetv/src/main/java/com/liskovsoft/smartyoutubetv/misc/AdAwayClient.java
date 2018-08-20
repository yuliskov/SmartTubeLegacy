package com.liskovsoft.smartyoutubetv.misc;

public class AdAwayClient {
    /**
     * Couple of ads have been taken from <a href="https://www.reddit.com/r/dropgoogle/comments/5tnjxl/block_youtube_ads_2017_hosts_file/">this post</a>
     */
    private static final String[] mAdAwayList = {
            "youtube.com/get_video_info*AdSense-Viral", // note: wildcard usage
            "youtube.com/api/stats/ads",
            "doubleclick.net",
            "doubleclickbygoogle.com",
            "2mdn.net",
            "googleadservices.com",
            "googlesyndication.com",
            "video-stats.video.google.com",
            "adservice.google", // match multiple domains like ca, it, com etc
            "adservices.google", // match multiple domains like ca, it, com etc
            "imasdk.googleapis.com",
            "s2.youtube.com",
            "s.youtube.com",
            "ad.youtube.com",
            "ads.youtube.com",
            "pagead-googlehosted.l.google.com",
            "pagead.l.google.com",
            "partnerad.l.google.com",
            "exoclick.com", // experimental
            // "syndication.exoclick.com",
            // "ads.exoclick.com",
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
            "contentabc.com", // experimental
            // "cdn11.contentabc.com",
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
