package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;

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
