package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.events;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeMediaItem;

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
