package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import java.io.InputStream;

public interface MPDPlaylistFoundCallback {
    void onFound(InputStream mpdPlaylist);
}
