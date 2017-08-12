package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.net.Uri;

public interface YouTubeInfoParser2 {
    void getUrlByTag(String iTag, UrlFoundCallback urlFoundCallback);
}
