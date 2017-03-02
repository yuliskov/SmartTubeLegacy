package com.liskovsoft.smartyoutubetv.events;

public class CSSFileInjectEvent {
    private final String mFileName;

    public CSSFileInjectEvent(String fileName) {
        mFileName = fileName;
    }

    public String getFileName() {
        return mFileName;
    }
}
