package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import java.io.InputStream;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
    InputStream getResult();
}
