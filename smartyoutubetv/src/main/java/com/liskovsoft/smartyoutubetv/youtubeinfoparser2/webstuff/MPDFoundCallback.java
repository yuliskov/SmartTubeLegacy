package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff;

import java.io.InputStream;

public interface MPDFoundCallback {
    void onFound(InputStream mpdPlaylist);
}
