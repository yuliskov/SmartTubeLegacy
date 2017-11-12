package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder.MPDFoundCallback;

public interface YouTubeInfoParser2 {
    void getUrlByTag(String iTag, UrlFoundCallback urlFoundCallback);
    void getMPDByCodec(String type, MPDFoundCallback mpdPlaylistFoundCallback);
}
