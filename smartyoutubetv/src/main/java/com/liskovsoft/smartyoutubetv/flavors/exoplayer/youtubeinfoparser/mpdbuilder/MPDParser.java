package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;

import java.util.List;

public interface MPDParser {
    List<YouTubeMediaItem> parse();
}
