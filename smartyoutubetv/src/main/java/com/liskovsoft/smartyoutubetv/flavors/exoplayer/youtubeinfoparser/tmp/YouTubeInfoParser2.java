package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd.MPDFoundCallback;

public interface YouTubeInfoParser2 {
    void getUrlByTag(String iTag, UrlFoundCallback urlFoundCallback);
    void getMPDByCodec(String type, MPDFoundCallback mpdPlaylistFoundCallback);
}
