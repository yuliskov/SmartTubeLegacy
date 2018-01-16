package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;

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
