package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.events;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.util.List;

public class DecipherSignaturesEvent {
    private final List<YouTubeMediaItem> mMediaItems;

    public DecipherSignaturesEvent(List<YouTubeMediaItem> mediaItems) {
         mMediaItems = mediaItems;
    }

    public List<YouTubeMediaItem> getMediaItems() {
        return mMediaItems;
    }
}
