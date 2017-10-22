package com.liskovsoft.smartyoutubetv.misc;

import android.net.Uri;

import java.net.URI;
import java.net.URISyntaxException;

public class MyUrlEncodedQueryString {
    private final Uri mParsedUri;
    private final UrlEncodedQueryString mQueryString;

    private MyUrlEncodedQueryString(String url) {
        mParsedUri = Uri.parse(url);
        if (mParsedUri.getHost() == null) {
            url = "http://fakeurl.com?" + url;
        }
        mQueryString = UrlEncodedQueryString.parse(getURI(url));
    }

    private URI getURI(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static MyUrlEncodedQueryString parse(String url) {
        return new MyUrlEncodedQueryString(url);
    }

    public void remove(String key) {
        mQueryString.remove(key);
    }

    public String get(String key) {
        return mQueryString.get(key);
    }

    public void set(String key, String value) {
        mQueryString.set(key, value);
    }

    @Override
    public String toString() {
        String path = mParsedUri.getPath();
        String host = mParsedUri.getHost();
        String scheme = mParsedUri.getScheme();

        if (host == null) {
            return mQueryString.toString();
        }

        return String.format("%s://%s%s?%s", scheme, host, path, mQueryString);
    }
}
