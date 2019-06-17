package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

import java.util.List;

public class DecipherSignaturesDoneEvent {
    private List<MediaItem> mMediaItems;

    public DecipherSignaturesDoneEvent(List<MediaItem> mediaItems) {
        mMediaItems = mediaItems;
    }

    public List<MediaItem> getMediaItems() {
        return mMediaItems;
    }
}
