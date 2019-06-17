package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

import java.util.List;

public class DecipherSignaturesEvent {
    private final List<MediaItem> mMediaItems;

    public DecipherSignaturesEvent(List<MediaItem> mediaItems) {
         mMediaItems = mediaItems;
    }

    public List<MediaItem> getMediaItems() {
        return mMediaItems;
    }
}
