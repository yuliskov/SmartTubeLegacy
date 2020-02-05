package com.liskovsoft.smartyoutubetv.misc.myquerystring;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.liskovsoft.sharedutils.helpers.Helpers;

import java.net.URI;
import java.net.URISyntaxException;

public class MyUrlEncodedQueryString implements MyQueryString {
    private String mUrl;
    private Uri mParsedUri;
    private UrlEncodedQueryStringBase mQueryString;

    private MyUrlEncodedQueryString(String url) {
        if (url == null) {
            return;
        }

        mParsedUri = Uri.parse(url);

        if (mParsedUri.getHost() == null) { // not full url
            mUrl = "http://fakeurl.com?" + url;
        } else {
            mUrl = url;
        }

        mQueryString = UrlEncodedQueryStringBase.parse(getURI(mUrl));
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

    @Override
    public void remove(String key) {
        mQueryString.remove(key);
    }

    @Override
    public String get(String key) {
        return mQueryString.get(key);
    }

    @Override
    public float getFloat(String key) {
        String val = get(key);
        return val != null ? Float.parseFloat(val) : 0;
    }

    @Override
    public void set(String key, String value) {
        mQueryString.set(key, value);
    }

    @Override
    public void set(String key, float value) {
        set(key, String.valueOf(value));
    }

    @Override
    public void set(String key, int value) {
        set(key, String.valueOf(value));
    }

    @NonNull
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

    @Override
    public boolean isValid() {
        if (mUrl == null) {
            return false;
        }

        return Helpers.matchAll(mUrl, "[^\\/?&]+=[^\\/&]+");
    }

    @Override
    public boolean isEmpty() {
        return mUrl == null || mUrl.isEmpty();
    }

    @Override
    public boolean contains(String key) {
        return mQueryString.contains(key);
    }
}
