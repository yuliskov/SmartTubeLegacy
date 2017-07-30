package com.liskovsoft.smartyoutubetv.youtubeinfoparser;

import java.io.InputStream;

public interface YouTubeInfoVisitable {
    // Common params
    String URL = "url";
    String TYPE = "type";
    String ITAG = "itag";
    String S = "s";
    // End Common params

    // DASH params
    String CLEN = "clen";
    String BITRATE = "bitrate";
    String PROJECTION_TYPE = "projection_type";
    String XTAGS = "xtags";
    String SIZE = "size";
    String INDEX = "index";
    String FPS = "fps";
    String LMT = "lmt";
    String QUALITY_LABEL = "quality_label";
    // End DASH params

    // Regular video params
    String QUALITY = "quality";
    // End Regular params

    void accept(YouTubeInfoVisitor visitor);
    InputStream getResult();
}
