package com.liskovsoft.m3uparser.m3u;


import com.liskovsoft.m3uparser.core.utils.Strings;

public class M3U {

    public static final String SIGNATURE = "#EXTM3U";
    public static final String EXT_PREFIX = "#EXT";

    public enum Tag {
        EXT_X_BYTERANGE("EXT-X-BYTERANGE"),
        EXT_X_TARGETDURATION("EXT-X-TARGETDURATION"),
        EXT_X_MEDIA_SEQUENCE("EXT-X-MEDIA-SEQUENCE"),
        EXT_X_KEY("EXT-X-KEY"),
        EXT_X_PROGRAM_DATE_TIME("EXT-X-PROGRAM-DATE-TIME"),
        EXT_X_ALLOW_CACHE("EXT-X-ALLOW-CACHE"),
        EXT_X_PLAYLIST_TYPE("EXT-X-PLAYLIST-TYPE"),
        EXT_X_ENDLIST("EXT-X-ENDLIST"),
        EXT_X_MEDIA("EXT-X-MEDIA"),
        EXT_X_STREAM_INF("EXT-X-STREAM-INF"),
        EXT_X_DISCONTINUITY("EXT-X-DISCONTINUITY"),
        EXT_X_I_FRAMES_ONLY("EXT-X-I-FRAMES-ONLY"),
        EXT_X_I_FRAME_STREAM_INF("EXT-X-I-FRAME-STREAM-INF"),
        EXT_X_VERSION("EXT-X-VERSION"),
        EXTINF("EXTINF"),

        UNKNOWN("");

        final String tagName;

        Tag(final String tagName) {
            this.tagName = tagName;
        }

        @Override
        public String toString() {
            return this.tagName;
        }

        public static M3U.Tag fromTagName(final String tagName) {
            if (!Strings.isNullOrEmpty(tagName)) {
                for (M3U.Tag tag : Tag.values()) {
                    if (tagName.toUpperCase().equals(tag.tagName)) {
                        return tag;
                    }
                }
            }

            return Tag.UNKNOWN;
        }
    }
}
