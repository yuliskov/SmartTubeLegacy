package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events;

public class GetDecipherCodeDoneEvent {
    private String mCode;

    public GetDecipherCodeDoneEvent(String code) {
        mCode = code;
    }

    public String getCode() {
        return mCode;
    }
}
