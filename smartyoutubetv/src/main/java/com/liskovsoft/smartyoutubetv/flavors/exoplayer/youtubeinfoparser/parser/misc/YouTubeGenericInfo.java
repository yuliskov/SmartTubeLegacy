package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc;

public interface YouTubeGenericInfo {
    String LENGTH_SECONDS = "length_seconds";
    String TITLE = "title";
    String AUTHOR = "author";
    String VIEW_COUNT = "view_count";
    String TIMESTAMP = "timestamp";
    String getLengthSeconds();
    void setLengthSeconds(String lengthSeconds);
    String getTitle();
    void setTitle(String title);
    String getAuthor();
    void setAuthor(String author);
    String getViewCount();
    void setViewCount(String viewCount);
    String getTimestamp();
    void setTimestamp(String timestamp);
}
