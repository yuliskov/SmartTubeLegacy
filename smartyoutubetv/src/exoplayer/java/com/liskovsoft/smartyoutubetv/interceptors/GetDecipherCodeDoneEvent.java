package com.liskovsoft.smartyoutubetv.interceptors;

public class GetDecipherCodeDoneEvent {
    private String mCode;

    public GetDecipherCodeDoneEvent(String code) {
        mCode = code;
    }

    public String getCode() {
        return mCode;
    }
}
