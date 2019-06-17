package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.Subtitle;

import java.util.List;

/**
 * Parses input (get_video_info) to {@link Subtitle}
 */
public class YouTubeSubParser {
    private static final String TAG = YouTubeSubParser.class.getSimpleName();
    private final JsonInfoParser mParser;
    private static final String VTT_MIME_TYPE = "text/vtt";
    private static final String VTT_PARAM = "vtt";
    private static final String VTT_CODECS = "wvtt";

    /**
     * Extracts subtitle, images and other objects from the <em>get_video_info</em> file
     * <br/>
     * For video object parsing use {@link SimpleYouTubeInfoParser}
     * @param content get_video_info file content
     */
    public YouTubeSubParser(String content, JsonInfoParser parser) {
        if (content == null) {
            throw new IllegalStateException("content cannot be null");
        }

        mParser = parser;
    }

    public List<Subtitle> extractAllSubs() {
        List<Subtitle> subs = mParser.extractAllSubs();
        addMimeTypes(subs);

        return subs;
    }

    /**
     * To show subtitles in <code>vtt</code> format just add <code>fmt=vtt</code> at the end of url
     * <br/>
     * Example: <code>https://www.youtube.com/api/timedtext?lang=en&v=MhQKe-aERsU&fmt=vtt</code>
     * @param subs list to process
     */
    private void addMimeTypes(List<Subtitle> subs) {
        if (subs == null) {
            return;
        }

        for (Subtitle sub : subs) {
            sub.setBaseUrl(String.format("%s&fmt=%s", sub.getBaseUrl(), VTT_PARAM));
            sub.setMimeType(VTT_MIME_TYPE);
            sub.setCodecs(VTT_CODECS);
        }
    }
}
