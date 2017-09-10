package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

import java.util.Date;

public class SimpleYouTubeGenericInfo implements YouTubeGenericInfo {
    private String mLengthSeconds;
    private String mTitle;
    private String mAuthor;
    private String mViewCount;
    private String mTimestamp;
    private Date mDate;

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

    @Override
    public String getViewCount() {
        return mViewCount;
    }

    @Override
    public void setViewCount(String viewCount) {
        mViewCount = viewCount;
    }

    @Override
    public String getTimestamp() {
        return mTimestamp;
    }

    @Override
    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
        mDate = new Date((long) Integer.parseInt(mTimestamp) * 1000);
    }

    @Override
    public String getPublishedDate() {
        if (mDate == null) {
            return "Date unknown";
        }
        return mDate.toString();
    }
}
