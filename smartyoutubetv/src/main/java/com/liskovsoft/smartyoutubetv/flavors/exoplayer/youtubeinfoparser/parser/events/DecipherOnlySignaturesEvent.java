package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

import java.util.List;

public class DecipherOnlySignaturesEvent {
    private final List<String> mSignatures;

    public DecipherOnlySignaturesEvent(List<String> signatures) {
        mSignatures = signatures;
    }

    public List<String> getSignatures() {
        return mSignatures;
    }
}
