package com.liskovsoft.smartyoutubetv.misc;

import com.liskovsoft.smartyoutubetv.helpers.Helpers;

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
            "youtube.com/s/tv/html5/*/sound"
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
