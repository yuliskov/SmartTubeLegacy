package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

import java.util.List;

public interface MPDParser {
    List<MediaItem> parse();
}
