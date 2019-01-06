package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.hls;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;

public interface HlsBuilder {
    void append(MediaItem mediaItem);
    boolean isEmpty();
    Uri buildUri();
}
