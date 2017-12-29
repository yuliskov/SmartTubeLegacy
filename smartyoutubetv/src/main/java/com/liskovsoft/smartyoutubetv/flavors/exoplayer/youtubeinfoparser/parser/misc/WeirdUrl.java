package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// overview: http://myweirdurl.com/key/value/key2/value2/key3/value3
// regex: \/key\/([^\/]*)
public class WeirdUrl {
    private String mUrl;

    public WeirdUrl(String url) {
        mUrl = url;
    }

    public String getParam(String key) {
        if (mUrl == null) {
            return null;
        }
        final String template = "\\/%s\\/([^\\/]*)";
        Pattern pattern = Pattern.compile(String.format(template, key));
        Matcher matcher = pattern.matcher(mUrl);
        boolean result = matcher.find();
        return result ? matcher.group(1) : null;
    }

    public void setParam(String key, String value) {
        if (mUrl == null) {
            return;
        }
        if (value == null) {
            return;
        }
        removeParam(key);
        mUrl += String.format("/%s/%s", key, value);
    }

    public void removeParam(String key) {
        if (mUrl == null) {
            return;
        }
        final String template = "\\/%s\\/[^\\/]*";
        mUrl = mUrl.replaceAll(String.format(template, key), "");
    }

    @Override
    public String toString() {
        return mUrl;
    }

    public boolean isEmpty() {
        return toString() == null;
    }
}
