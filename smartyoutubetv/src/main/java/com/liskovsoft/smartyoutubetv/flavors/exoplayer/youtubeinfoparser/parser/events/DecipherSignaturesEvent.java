package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items.YouTubeMediaItem;

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
