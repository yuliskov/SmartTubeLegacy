package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.hls;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

import java.util.List;

public interface UrlListBuilder {
    void append(MediaItem mediaItem);
    boolean isEmpty();
    List<String> buildUriList();
}
