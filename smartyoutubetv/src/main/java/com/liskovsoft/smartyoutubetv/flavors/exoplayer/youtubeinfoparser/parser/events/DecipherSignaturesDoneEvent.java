package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items.YouTubeMediaItem;

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
