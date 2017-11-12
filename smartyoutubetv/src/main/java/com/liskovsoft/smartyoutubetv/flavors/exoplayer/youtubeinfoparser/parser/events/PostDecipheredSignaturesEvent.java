package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

public class PostDecipheredSignaturesEvent {
    private final String[] mSignatures;

    public PostDecipheredSignaturesEvent(String[] signatures) {
        mSignatures = signatures;
    }

    public String[] getSignatures() {
        return mSignatures;
    }
}
