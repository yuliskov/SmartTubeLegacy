package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.tmp;

import java.io.InputStream;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
    InputStream getResult();
}
