package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

import java.util.List;

public class DecipherOnlySignaturesDoneEvent {
    private final List<String> mSignatures;

    public DecipherOnlySignaturesDoneEvent(List<String> signatures) {
        mSignatures = signatures;
    }

    public List<String> getSignatures() {
        return mSignatures;
    }
}
