package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser.GenericInfo;

public class JsonVideoMetadataParser extends JsonParserBase {
    private static final String TAG = JsonVideoMetadataParser.class.getSimpleName();
    private static final String VIDEO_TITLE = "$.videoDetails.title";
    private static final String VIDEO_ID = "$.videoDetails.videoId";
    private static final String LENGTH_SECONDS = "$.videoDetails.lengthSeconds";
    private static final String CHANNEL_ID = "$.videoDetails.channelId";
    private static final String DESCRIPTION = "$.videoDetails.shortDescription";
    private static final String THUMBNAIL = "$.videoDetails.thumbnail.thumbnails[]"; // array
    private static final String VIDEO_RATING = "$.videoDetails.averageRating"; // number (e.g 4.90)
    private static final String VIEW_COUNT = "$.videoDetails.viewCount";
    private static final String VIEW_COUNT_FULL = VIEW_COUNT;
    private static final String VIDEO_AUTHOR = "$.videoDetails.author";
    private static final String IS_LIVE = "$.videoDetails.isLiveContent";

    /**
     * Parses next json content
     * @param nextContent json content
     * @param <T> Could be String or Stream
     */
    public <T> JsonVideoMetadataParser(T nextContent) {
        super(nextContent);
    }

    public JsonVideoMetadataParser(DocumentContext parser) {
        super(parser);
    }

    public GenericInfo extractVideoMetadata() {
        GenericInfo videoMetadata = null;

        try {
            videoMetadata = initCurrentVideo();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            Log.d(TAG, ex.getMessage());
        }

        return videoMetadata;
    }

    private GenericInfo initCurrentVideo() {
        GenericInfo info = new SimpleYouTubeGenericInfo();

        info.setTitle(str(VIDEO_TITLE));
        info.setAuthor(str(VIDEO_AUTHOR));
        info.setViewCount(str(VIEW_COUNT));
        info.setDescription(str(DESCRIPTION));
        info.setVideoId(str(VIDEO_ID));
        info.setChannelId(str(CHANNEL_ID));
        info.setLengthSeconds(str(LENGTH_SECONDS));

        return info;
    }
}
