package com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser;

import java.io.InputStream;
import java.util.Set;

public interface VideoInfoBuilder {
    void removeFormat(VideoFormat format);
    InputStream get();
    Set<VideoFormat> getSupportedFormats();
    boolean selectFormat(VideoFormat format);
}
