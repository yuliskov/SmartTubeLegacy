package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.util.List;

public class DecipherSignaturesDoneEvent {
    private List<YouTubeMediaItem> mMediaItems;

    public DecipherSignaturesDoneEvent(List<YouTubeMediaItem> mediaItems) {
        mMediaItems = mediaItems;
    }

    public List<YouTubeMediaItem> getMediaItems() {
        return mMediaItems;
    }
}
