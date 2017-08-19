package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

public interface YouTubeInfoParser2 {
    void getUrlByTag(String iTag, UrlFoundCallback urlFoundCallback);
    void getMPDPlaylist(String type, MPDFoundCallback mpdPlaylistFoundCallback);
}
