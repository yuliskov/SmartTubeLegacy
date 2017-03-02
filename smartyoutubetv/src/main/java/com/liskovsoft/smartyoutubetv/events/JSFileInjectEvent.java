package com.liskovsoft.smartyoutubetv.events;

public class JSFileInjectEvent {
    private final String mFileName;

    public JSFileInjectEvent(String fileName) {
        mFileName = fileName;
    }

    public String getFileName() {
        return mFileName;
    }
}
