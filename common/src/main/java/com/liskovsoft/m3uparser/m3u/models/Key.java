package com.liskovsoft.m3uparser.m3u.models;

import android.net.Uri;

public class Key {

    private String method;
    private Uri uri;
    private String iv;

    public Key() {
        this.method = null;
        this.uri = null;
        this.iv = null;
    }

    public Key(final Key src) {
        this();
        if (src != null) {
            this.method = src.method;
            this.uri = src.uri;
            this.iv = src.iv;
        }
    }

    public String getMethod() { return method; }
    public void setMethod(final String value) { this.method = value; }

    public Uri getUri() { return uri; }
    public void setUri(Uri value) { this.uri = value; }

    public String getIV() { return iv; }
    public void setIV(final String value) { this.iv = value; }

    @Override
    public String toString() {
        return "Key{" +
                "method='" + method + '\'' +
                ", uri=" + uri +
                ", iv='" + iv + '\'' +
                '}';
    }
}
