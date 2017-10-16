package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.tmp;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff.MPDFoundCallback;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff.UrlFoundCallback;

public interface YouTubeInfoParser2 {
    void getUrlByTag(String iTag, UrlFoundCallback urlFoundCallback);
    void getMPDByCodec(String type, MPDFoundCallback mpdPlaylistFoundCallback);
}
