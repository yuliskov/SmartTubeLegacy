package com.liskovsoft.smartyoutubetv.helpers;

public class AdAwayClient {
    private static final String[] mAdAwayList = {"googleads.g.doubleclick.net",
            "pagead.l.doubleclick.net",
            "ad.doubleclick.net",
            "partnerad.l.doubleclick.net",
            "pubads.g.doubleclick.net",
            "cm.g.doubleclick.net",
            "securepubads.g.doubleclick.net",
            "pagead2.googlesyndication.com",
            "tpc.googlesyndication.com",
            "www.googleadservices.com",
            "syndication.exoclick.com",
            "ads.exoclick.com",
            "cdn11.contentabc.com",
            // button sounds
            "youtube.com/s/tv/html5/0b01c692/sound"
            // "sound/cross-enter.mp3",
            // "sound/same-toggle.mp3",
            // "sound/same-heavy.mp3",
            // "sound/same-light.mp3"
    };


    public static boolean isAd(String url) {
        for (String suburl : mAdAwayList) {
            boolean contains = url.contains(suburl);
            if (contains)
                return true;
        }
        return false;
    }
}
