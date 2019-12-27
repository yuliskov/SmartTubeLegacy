package com.liskovsoft.smartyoutubetv.misc.youtubeutils;

import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;

public class YouTubeWatchTime {
    private final String mUrl;
    private final MyQueryString mParser;
    private static final String LEN = "len";
    private static final String CMT = "cmt";
    private static final String ST = "st";
    private static final String ET = "et";

    private YouTubeWatchTime(String url) {
        mUrl = url;
        mParser = MyQueryStringFactory.parse(url);
    }

    public static YouTubeWatchTime parse(String url) {
        return new YouTubeWatchTime(url);
    }

    public int getCurrentPosition() {
        String pos = mParser.get(CMT);

        if (Helpers.isNumeric(pos)) {
            return Integer.parseInt(pos);
        }

        return 0;
    }
}
