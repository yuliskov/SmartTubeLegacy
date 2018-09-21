package com.liskovsoft.smartyoutubetv.events;

public class AssetFileInjectEvent {
    private final String mFileName;
    private final String mListenerHash;

    public AssetFileInjectEvent(String fileName, String listenerHash) {
        mFileName = fileName;
        mListenerHash = listenerHash;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getListenerHash() {
        return mListenerHash;
    }
}
