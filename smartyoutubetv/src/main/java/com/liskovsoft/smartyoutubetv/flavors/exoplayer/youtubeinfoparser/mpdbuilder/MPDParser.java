package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;

import java.util.List;

public interface MPDParser {
    List<MediaItem> parse();
}
