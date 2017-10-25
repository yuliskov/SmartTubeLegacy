package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.MPDFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.UrlFoundCallback;

public interface YouTubeInfoParser2 {
    void getUrlByTag(String iTag, UrlFoundCallback urlFoundCallback);
    void getMPDByCodec(String type, MPDFoundCallback mpdPlaylistFoundCallback);
}
