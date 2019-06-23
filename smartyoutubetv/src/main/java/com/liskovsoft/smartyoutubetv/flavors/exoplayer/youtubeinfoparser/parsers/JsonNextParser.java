package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import com.jayway.jsonpath.DocumentContext;

public class JsonNextParser {
    private static final String VIDEO_TITLE = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.title.runs[0].text";
    private static final String VIEW_COUNT = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.shortViewCountText.runs[0].text";
    private static final String LIKES_COUNT = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.likesCount.runs[0].text";
    private static final String DISLIKES_COUNT = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer" +
            ".contents[0].videoMetadataRenderer.dislikesCount.runs[0].text";
    private static final String DESCRIPTION = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.description.runs[0].text";
    private static final String PUBLISHED_DATE = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer" +
            ".contents[0].videoMetadataRenderer.publishedTimeText.runs[0].text";
    private static final String ID = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0].videoMetadataRenderer.videoId";
    private static final String NEXT_VIDEO_ID = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.sets[0].nextVideoRenderer" +
            ".maybeHistoryEndpointRenderer.endpoint.watchEndpoint.videoId";
    private static final String NEXT_VIDEO_TITLE = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.sets[0].nextVideoRenderer" +
            ".maybeHistoryEndpointRenderer.item.previewButtonRenderer.title.runs[0].text";
    private final DocumentContext mParser;

    public JsonNextParser(String nextContent) {
        mParser = ParserUtils.createJsonInfoParser(nextContent);
    }

    public VideoMetadata extractVideoMetadata() {
        VideoMetadata videoMetadata = new VideoMetadata();

        videoMetadata.setTitle(str(VIDEO_TITLE));
        videoMetadata.setViewCount(str(VIEW_COUNT));
        videoMetadata.setLikesCount(str(LIKES_COUNT));
        videoMetadata.setDislikesCount(str(DISLIKES_COUNT));
        videoMetadata.setDescription(str(DESCRIPTION));
        videoMetadata.setPublishedDate(str(PUBLISHED_DATE));
        videoMetadata.setId(str(ID));

        VideoMetadata nextVideoMetadata = new VideoMetadata();
        nextVideoMetadata.setId(str(NEXT_VIDEO_ID));
        nextVideoMetadata.setTitle(str(NEXT_VIDEO_TITLE));

        videoMetadata.setNextVideoMetadata(nextVideoMetadata);

        return videoMetadata;
    }

    private String str(String path) {
        return ParserUtils.extractString(path, mParser);
    }

    public static class VideoMetadata {
        private String mTitle;
        private String mViewCount;
        private String mLikesCount;
        private String mDislikesCount;
        private String mDescription;
        private String mPublishedDate;
        private String mId;

        private VideoMetadata mNextVideoMetadata;

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getViewCount() {
            return mViewCount;
        }

        public void setViewCount(String viewCount) {
            mViewCount = viewCount;
        }

        public String getLikesCount() {
            return mLikesCount;
        }

        public void setLikesCount(String likesCount) {
            mLikesCount = likesCount;
        }

        public String getDislikesCount() {
            return mDislikesCount;
        }

        public void setDislikesCount(String dislikesCount) {
            mDislikesCount = dislikesCount;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getPublishedDate() {
            return mPublishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            mPublishedDate = publishedDate;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public VideoMetadata getNextVideoMetadata() {
            return mNextVideoMetadata;
        }

        public void setNextVideoMetadata(VideoMetadata autoplayMetadata) {
            mNextVideoMetadata = autoplayMetadata;
        }
    }
}
