package com.liskovsoft.smartyoutubetv.youtubeinfoparser.events;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser.VideoFormat;

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
