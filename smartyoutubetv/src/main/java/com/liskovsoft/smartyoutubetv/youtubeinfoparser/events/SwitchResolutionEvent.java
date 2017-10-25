package com.liskovsoft.smartyoutubetv.youtubeinfoparser.events;

public class SwitchResolutionEvent {
    private String mFormatName;

    public SwitchResolutionEvent(String formatName) {
        mFormatName = formatName;
    }

    public String getFormatName() {
        return mFormatName;
    }
}
