package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

public class SimpleYouTubeGenericInfo implements YouTubeGenericInfo {
    private String mLengthSeconds;
    private String mTitle;
    private String mAuthor;

    @Override
    public String getLengthSeconds() {
        return mLengthSeconds;
    }

    @Override
    public void setLengthSeconds(String lengthSeconds) {
       mLengthSeconds = lengthSeconds;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(String title) {
         mTitle = title;
    }

    @Override
    public String getAuthor() {
        return mAuthor;
    }

    @Override
    public void setAuthor(String author) {
        mAuthor = author;
    }
}
