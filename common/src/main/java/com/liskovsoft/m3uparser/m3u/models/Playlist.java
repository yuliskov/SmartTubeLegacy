package com.liskovsoft.m3uparser.m3u.models;


import java.util.LinkedList;
import java.util.List;

public class Playlist {

    private final List<Stream> streams;
    private final List<Segment> segments;
    private final List<Media> media;

    private double targetDuration = 0.0;
    private boolean allowCache = false;
    private String playListType = null;
    private String version = null;
    private int mediaSequence = 0;

    public Playlist() {
        this.streams = new LinkedList<>();
        this.segments = new LinkedList<>();
        this.media = new LinkedList<>();
    }

    public List<Stream> getStreams() { return streams; }

    public List<Segment> getSegments() { return segments; }

    public List<Media> getMedia() { return media; }


    public boolean isAllowCache() { return allowCache; }
    public void setAllowCache(final boolean allowCache) { this.allowCache = allowCache; }

    public String getPlayListType() { return playListType; }

    public void setPlayListType(final String playListType) { this.playListType = playListType; }

    public String getVersion() { return version; }
    public void setVersion(final String version) { this.version = version; }

    public double getTargetDuration() { return targetDuration; }
    public void setTargetDuration(final double value) { this.targetDuration = value; }

    public int getMediaSequence() { return mediaSequence; }

    public void setMediaSequence(final int value) { this.mediaSequence = value; }

    @Override
    public String toString() {
        return "Playlist{" +
                "streams=" + streams +
                ", segments=" + segments +
                ", targetDuration=" + targetDuration +
                ", allowCache=" + allowCache +
                ", playListType='" + playListType + '\'' +
                ", version='" + version + '\'' +
                ", mediaSequence=" + mediaSequence +
                '}';
    }
}
