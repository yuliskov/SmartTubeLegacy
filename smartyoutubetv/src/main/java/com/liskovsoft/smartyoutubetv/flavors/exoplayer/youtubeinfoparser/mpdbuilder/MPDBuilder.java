package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items.YouTubeMediaItem;

import java.io.InputStream;

public interface MPDBuilder {
    void append(YouTubeMediaItem mediaItem);
    InputStream build();
}
