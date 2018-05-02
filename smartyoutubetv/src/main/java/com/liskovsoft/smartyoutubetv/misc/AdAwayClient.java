package com.liskovsoft.smartyoutubetv.misc;

public class AdAwayClient {
    /**
     * Couple of ads have been taken from <a href="https://www.reddit.com/r/dropgoogle/comments/5tnjxl/block_youtube_ads_2017_hosts_file/">this post</a>
     */
    private static final String[] mAdAwayList = {
            "pixel.moatads.com",
            "rtd.tubemogul.com",
            "innovid.com",
            "s.innovid.com",
            "2mdn.net",
            "s0.2mdn.net",
            "files.adform.net",
            "fwmrm.net",
            "2975c.v.fwmrm.net",
            "secure-ds.serving-sys.com",
            "video-stats.video.google.com",
            "www.adwords.google.lloymlincs.com",
            "ads.adadapted.com",
            "www.doubleclickbygoogle.com",
            "adservice.google.ca",
            "adservice.google.com",
            "adservice.google.com.au",
            "adservices.google.com",
            "feedads.googleadservices.com",
            "imasdk.googleapis.com",
            "m4.afs.googleadservices.com",
            "unblockyoutube.biz",
            "unblockyoutubeproxy.com",
            "unblockyoutube.tv",
            "youtube-proxy.info",
            "youtuberid.com",
            "gotoyoutube.us",
            "s2.youtube.com",
            "s.youtube.com",
            "youtube.112.2o7.net",
            "ad.youtube.com",
            "ads.youtube.com",
            "youtube.com/api/stats/ads",
            "doubleclick.net",
            "ad.doubleclick.net",
            "partnerad.l.doubleclick.net",
            "pubads.g.doubleclick.net",
            "securepubads.g.doubleclick.net",
            "googleads.g.doubleclick.net",
            "googleads2.g.doubleclick.net",
            "googleads4.g.doubleclick.net",
            "static.doubleclick.net",
            "ad-g.doubleclick.net",
            "ad.mo.doubleclick.net",
            "pagead-googlehosted.l.google.com",
            "pagead.l.doubleclick.net",
            "pagead.l.google.com",
            "partnerad.l.google.com",
            "cm.g.doubleclick.net",
            "googlesyndication.com",
            "pagead.googlesyndication.com",
            "pagead1.googlesyndication.com",
            "pagead2.googlesyndication.com",
            "pagead3.googlesyndication.com",
            "video-ad-stats.googlesyndication.com",
            "tpc.googlesyndication.com",
            "googleadservices.com",
            "www.googleadservices.com",
            "partner.googleadservices.com",
            "www.partner.googleadservices.com",
            "pagead2.googleadservices.com",
            "domains.googlesyndication.com",
            "4.afs.googleadservices.com",
            "syndication.exoclick.com",
            "ads.exoclick.com",
            "cdn11.contentabc.com"
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
