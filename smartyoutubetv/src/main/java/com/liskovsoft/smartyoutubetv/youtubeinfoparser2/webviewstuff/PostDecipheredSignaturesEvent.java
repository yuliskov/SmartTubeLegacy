package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

public class PostDecipheredSignaturesEvent {
    private final String[] mSignatures;

    public PostDecipheredSignaturesEvent(String[] signatures) {
        mSignatures = signatures;
    }

    public String[] getSignatures() {
        return mSignatures;
    }
}
