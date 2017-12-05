package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc;

import java.util.Random;

public class SimpleYouTubeGenericInfo implements YouTubeGenericInfo {
    private final Random mRandrom;
    private String mLengthSeconds;
    private String mTitle;
    private String mAuthor;
    private String mViewCount;
    private String mTimestamp;

    public SimpleYouTubeGenericInfo() {
        mRandrom = new Random();
    }

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
        // random date within a year
        int max = Integer.parseInt(mTimestamp);
        int oneYear = 31_536_000;
        int min = max - oneYear;
        int randomNum = mRandrom.nextInt(max + 1 - min) + min;
        return String.valueOf(randomNum);
    }

    @Override
    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }
}
