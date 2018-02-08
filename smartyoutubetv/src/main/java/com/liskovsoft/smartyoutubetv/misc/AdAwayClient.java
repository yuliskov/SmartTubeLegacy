package com.liskovsoft.smartyoutubetv.misc;

public class AdAwayClient {
    private static final String[] mAdAwayList = {
            "youtube.com/api/stats/ads",
            "ad.doubleclick.net",
            "partnerad.l.doubleclick.net",
            "pubads.g.doubleclick.net",
            "securepubads.g.doubleclick.net",
            "googleads.g.doubleclick.net",
            "pagead.l.doubleclick.net",
            "cm.g.doubleclick.net",
            "pagead2.googlesyndication.com",
            "tpc.googlesyndication.com",
            "www.googleadservices.com",
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
