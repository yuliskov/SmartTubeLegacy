package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events;

public class PostDecipheredSignaturesEvent {
    private final String[] mSignatures;
    private final int mId;

    public PostDecipheredSignaturesEvent(String[] signatures, int id) {
        mSignatures = signatures;
        mId = id;
    }

    public String[] getSignatures() {
        return mSignatures;
    }

    public int getId() {
        return mId;
    }
}
