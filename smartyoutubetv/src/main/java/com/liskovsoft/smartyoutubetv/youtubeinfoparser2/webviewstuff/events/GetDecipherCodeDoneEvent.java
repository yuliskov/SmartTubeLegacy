package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.events;

public class GetDecipherCodeDoneEvent {
    private String mCode;

    public GetDecipherCodeDoneEvent(String code) {
        mCode = code;
    }

    public String getCode() {
        return mCode;
    }
}
