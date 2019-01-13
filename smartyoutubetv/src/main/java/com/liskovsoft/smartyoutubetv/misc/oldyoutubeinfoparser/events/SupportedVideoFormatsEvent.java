package com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.events;

public class SupportedVideoFormatsEvent {
    private final String mFormats;

    public SupportedVideoFormatsEvent(String formats) {
        mFormats = formats;
    }

    public String getFormats() {
        return mFormats;
    }
}
