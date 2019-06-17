package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;

public class ParserUtils {
    private static final String TAG = ParserUtils.class.getSimpleName();

    public static String extractParam(String content, String queryParam) {
        Uri videoInfo = parseUri(content);
        String value = videoInfo.getQueryParameter(queryParam);

        if (value != null && value.isEmpty()) {
            return null;
        }

        return value;
    }

    public static Uri parseUri(String content) {
        if (content.startsWith("http")) {
            return Uri.parse(content);
        }

        return Uri.parse("http://example.com?" + content);
    }

    public static boolean isEmpty(MyQueryString queryString) {
        if (queryString == null) {
            return true;
        }

        return queryString.isEmpty();
    }
}
