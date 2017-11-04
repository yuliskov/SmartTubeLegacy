package com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.events;

import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.VideoFormat;

import java.util.Set;

public class VideoFormatEvent {
    private final Set<VideoFormat> mSupportedFormats;
    private final VideoFormat mSelectedFormat;

    public VideoFormatEvent(Set<VideoFormat> supportedFormats, VideoFormat selectedFormat) {
        mSupportedFormats = supportedFormats;
        mSelectedFormat = selectedFormat;
    }

    public Set<VideoFormat> getSupportedFormats() {
        return mSupportedFormats;
    }

    public VideoFormat getSelectedFormat() {
        return mSelectedFormat;
    }
}
