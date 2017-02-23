package com.liskovsoft.smartyoutubetv.events;

public class SwitchResolutionEvent {
    private final String mItag;

    public SwitchResolutionEvent(String itag) {
        mItag = itag;
    }

    public String getItag() {
        return mItag;
    }
}
