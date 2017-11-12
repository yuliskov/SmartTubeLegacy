package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import java.io.InputStream;

public interface MPDFoundCallback {
    void onFound(InputStream mpdPlaylist);
}
