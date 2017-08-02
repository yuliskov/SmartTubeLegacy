package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

public enum ITagOld {
    AUDIO_115K_WEBM("251"), AUDIO_128K_AAC("140"),
    VIDEO_144P_AVC("160"), VIDEO_144P_WEBM("278"), VIDEO_240P_AVC("133"), VIDEO_240P_WEBM("242"), VIDEO_360P_AVC("134"), VIDEO_360P_WEBM("243"),
    VIDEO_480P_AVC("135"), VIDEO_480P_WEBM("244"), VIDEO_720P_AVC("136"), VIDEO_720P_WEBM("247"), VIDEO_1080P_AVC("137"), VIDEO_1080P_WEBM("248"),
    VIDEO_1440P_AVC("264"), VIDEO_1440P_WEBM("271"), VIDEO_2160P_AVC("266"), VIDEO_2160P_WEBM("313");

    private final String mITag;

    ITagOld(String iTag) {
        mITag = iTag;
    }

    public String getValue() {
        return mITag;
    }
}
