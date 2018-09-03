package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events;

import java.util.List;

public class DecipherOnlySignaturesDoneEvent {
    private final List<String> mSignatures;
    private final int mId;

    public DecipherOnlySignaturesDoneEvent(List<String> signatures, int id) {
        mSignatures = signatures;
        mId = id;
    }

    public List<String> getSignatures() {
        return mSignatures;
    }

    public int getId() {
        return mId;
    }
}
