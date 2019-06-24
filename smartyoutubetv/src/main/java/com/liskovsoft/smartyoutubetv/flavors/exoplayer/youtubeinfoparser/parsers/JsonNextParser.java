package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.content.Intent;
import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

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
    private static final String VIDEO_ID = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0].videoMetadataRenderer.videoId";
    private static final String NEXT_VIDEO_ID = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.sets[0].nextVideoRenderer" +
            ".maybeHistoryEndpointRenderer.endpoint.watchEndpoint.videoId";
    private static final String NEXT_VIDEO_TITLE = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.sets[0].nextVideoRenderer" +
            ".maybeHistoryEndpointRenderer.item.previewButtonRenderer.title.runs[0].text";
    private static final String IS_SUBSCRIBED = "$.transportControls.transportControlsRenderer.subscribeButton.toggleButtonRenderer.isToggled";
    private static final String IS_LIKED = "$.transportControls.transportControlsRenderer.likeButton.toggleButtonRenderer.isToggled";
    private static final String IS_DISLIKED = "$.transportControls.transportControlsRenderer.dislikeButton.toggleButtonRenderer.isToggled";
    private final DocumentContext mParser;

    public JsonNextParser(String nextContent) {
        mParser = ParserUtils.createJsonInfoParser(nextContent);
    }

    public VideoMetadata extractVideoMetadata() {
        VideoMetadata videoMetadata = initCurrentVideo();
        videoMetadata.setNextVideo(initNextVideo());

        return videoMetadata;
    }

    private VideoMetadata initCurrentVideo() {
        VideoMetadata videoMetadata = new VideoMetadata();

        videoMetadata.setTitle(str(VIDEO_TITLE));
        videoMetadata.setViewCount(str(VIEW_COUNT));
        videoMetadata.setLikesCount(str(LIKES_COUNT));
        videoMetadata.setDislikesCount(str(DISLIKES_COUNT));
        videoMetadata.setDescription(str(DESCRIPTION));
        videoMetadata.setPublishedDate(str(PUBLISHED_DATE));
        videoMetadata.setVideoId(str(VIDEO_ID));
        videoMetadata.setSubscribed(bool(IS_SUBSCRIBED));
        videoMetadata.setLiked(bool(IS_LIKED));
        videoMetadata.setDisliked(bool(IS_DISLIKED));

        return videoMetadata;
    }

    private VideoMetadata initNextVideo() {
        VideoMetadata nextVideoMetadata = new VideoMetadata();
        nextVideoMetadata.setTitle(str(NEXT_VIDEO_TITLE));
        nextVideoMetadata.setVideoId(str(NEXT_VIDEO_ID));

        return nextVideoMetadata;
    }

    private boolean bool(String path) {
        return ParserUtils.extractBool(path, mParser);
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
        private boolean mSubscribed;
        private boolean mLiked;
        private boolean mDisliked;
        private String mVideoId;

        private VideoMetadata mNextVideo;

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

        public String getVideoId() {
            return mVideoId;
        }

        public void setVideoId(String videoId) {
            mVideoId = videoId;
        }

        public VideoMetadata getNextVideo() {
            return mNextVideo;
        }

        public void setNextVideo(VideoMetadata autoplayMetadata) {
            mNextVideo = autoplayMetadata;
        }

        public boolean isSubscribed() {
            return mSubscribed;
        }

        public void setSubscribed(boolean subscribed) {
            mSubscribed = subscribed;
        }

        public boolean isLiked() {
            return mLiked;
        }

        public void setLiked(boolean liked) {
            mLiked = liked;
        }

        public boolean isDisliked() {
            return mDisliked;
        }

        public void setDisliked(boolean disliked) {
            mDisliked = disliked;
        }

        public Intent toIntent() {
            Intent intent = new Intent();
            intent.putExtra(ExoPlayerFragment.VIDEO_TITLE, mTitle);
            intent.putExtra(ExoPlayerFragment.VIDEO_VIEW_COUNT, mViewCount);
            intent.putExtra(ExoPlayerFragment.VIDEO_DATE, mPublishedDate);
            intent.putExtra(ExoPlayerFragment.BUTTON_LIKE, mLiked);
            intent.putExtra(ExoPlayerFragment.BUTTON_DISLIKE, mDisliked);
            intent.putExtra(ExoPlayerFragment.BUTTON_SUBSCRIBE, mSubscribed);

            return intent;
        }
    }
}
