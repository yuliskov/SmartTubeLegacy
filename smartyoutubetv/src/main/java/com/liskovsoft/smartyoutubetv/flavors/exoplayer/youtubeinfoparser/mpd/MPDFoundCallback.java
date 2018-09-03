package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import java.io.InputStream;

public interface MPDFoundCallback {
    void onFound(InputStream mpdPlaylist);
}
