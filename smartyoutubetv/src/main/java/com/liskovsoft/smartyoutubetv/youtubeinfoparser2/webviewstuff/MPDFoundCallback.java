package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import java.io.InputStream;

public interface MPDFoundCallback {
    void onFound(InputStream mpdPlaylist);
}
