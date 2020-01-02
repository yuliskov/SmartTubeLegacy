package com.liskovsoft.m3uparser.m3u.models;

import android.net.Uri;

public class Segment {

    private Key key;
    private double duration;
    private String title;
    private Uri uri;

    public Segment() {
        this.key = null;
        this.duration = -1.0;
        this.title = null;
        this.uri = null;
    }

    public Key getKey() { return key; }
    public void setKey(final Key value) { this.key = value; }

    public double getDuration() { return duration; }
    public void setDuration(double value) { this.duration = value; }

    public String getTitle() { return title; }
    public void setTitle(final String value) { this.title = value; }

    public Uri getUri() { return uri; }
    public void setUri(final Uri value) { this.uri = value; }

    @Override
    public String toString() {
        return "Segment{" +
                "key=" + key +
                ", duration=" + duration +
                ", title='" + title + '\'' +
                ", uri=" + uri +
                '}';
    }
}
