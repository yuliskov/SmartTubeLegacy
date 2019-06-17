package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

/**
 * <b>Storyboard parser</b><br/>
 * <em>Storyboard</em> is a collection of timeline thumbnails<br/>
 * Parses input (get_video_info) to {@link Storyboard}
 */
public class YouTubeStoryParser {
    private static final String TAG = YouTubeStoryParser.class.getSimpleName();
    private JsonInfoParser mParser;

    /**
     * Extracts subtitle, images and other objects from the <em>get_video_info</em> file
     * <br/>
     * For video object parsing use {@link SimpleYouTubeInfoParser}
     * @param content get_video_info file content
     */
    public YouTubeStoryParser(String content, JsonInfoParser parser) {
        if (content == null) {
            throw new IllegalStateException("content cannot be null");
        }

        mParser = parser;
    }

    public Storyboard extractStory() {
        return null;
    }

    public class Storyboard {
        
    }
}
