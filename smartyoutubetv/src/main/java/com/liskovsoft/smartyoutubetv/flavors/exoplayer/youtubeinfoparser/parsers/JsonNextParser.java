package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.content.Intent;
import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

public class JsonNextParser {
    private static final String TAG = JsonNextParser.class.getSimpleName();
    private static final String VIDEO_TITLE = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.title.runs[0].text";
    private static final String VIEW_COUNT = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.shortViewCountText.runs[0].text";
    private static final String VIEW_COUNT_FULL = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.viewCountText.runs[0].text";
    private static final String LIKES_COUNT = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.likesCount.runs[0].text";
    private static final String DISLIKES_COUNT = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer" +
            ".contents[0].videoMetadataRenderer.dislikesCount.runs[0].text";
    private static final String DESCRIPTION = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.description.runs[0].text";
    private static final String PUBLISHED_DATE = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer" +
            ".contents[0].videoMetadataRenderer.publishedTimeText.runs[0].text";
    private static final String PUBLISHED_DATE_FULL = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer" +
            ".contents[0].videoMetadataRenderer.dateText.runs[0].text";
    private static final String VIDEO_ID = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0].videoMetadataRenderer.videoId";
    private static final String NEXT_VIDEO_ID = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.sets[0].nextVideoRenderer" +
            ".maybeHistoryEndpointRenderer.endpoint.watchEndpoint.videoId";
    private static final String NEXT_VIDEO_TITLE = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.sets[0].nextVideoRenderer" +
            ".maybeHistoryEndpointRenderer.item.previewButtonRenderer.title.runs[0].text";
    private static final String IS_SUBSCRIBED = "$.contents.singleColumnWatchNextResults.results.results.contents[1].itemSectionRenderer.contents[0]" +
            ".videoOwnerRenderer.subscribeButton.subscribeButtonRenderer.subscribed";
    private static final String LIKE_STATUS = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0]" +
            ".videoMetadataRenderer.likeStatus";
    private static final String CHANNEL_ID = "$.contents.singleColumnWatchNextResults.results.results.contents[1].itemSectionRenderer.contents[0]" +
            ".videoOwnerRenderer.subscribeButton.subscribeButtonRenderer.channelId";
    private static final String LIKE_STATUS_LIKE = "LIKE";
    private static final String LIKE_STATUS_DISLIKE = "DISLIKE";
    private static final String LIKE_STATUS_INDIFFERENT = "INDIFFERENT";
    private final DocumentContext mParser;

    public JsonNextParser(String nextContent) {
        mParser = ParserUtils.createJsonInfoParser(nextContent);
    }

    public VideoMetadata extractVideoMetadata() {
        VideoMetadata videoMetadata = null;

        try {
            videoMetadata = initCurrentVideo();
            videoMetadata.setNextVideo(initNextVideo());
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            Log.d(TAG, ex.getMessage());
        }

        return videoMetadata;
    }

    private VideoMetadata initCurrentVideo() {
        String likeStatus = str(LIKE_STATUS);

        if (likeStatus == null) {
            throw new IllegalStateException("Oops... seems that 'likeStatus' metadata format has been changed: " + LIKE_STATUS);
        }

        Boolean isSubscribed = bool(IS_SUBSCRIBED);

        if (isSubscribed == null) {
            throw new IllegalStateException("Oops... seems that 'subscribed' metadata format has been changed: " + IS_SUBSCRIBED);
        }

        VideoMetadata videoMetadata = new VideoMetadata();

        videoMetadata.setTitle(str(VIDEO_TITLE));
        videoMetadata.setViewCount(str(VIEW_COUNT_FULL));
        videoMetadata.setLikesCount(str(LIKES_COUNT));
        videoMetadata.setDislikesCount(str(DISLIKES_COUNT));
        videoMetadata.setDescription(str(DESCRIPTION));
        videoMetadata.setPublishedDate(str(PUBLISHED_DATE_FULL));
        videoMetadata.setVideoId(str(VIDEO_ID));
        videoMetadata.setSubscribed(isSubscribed);
        videoMetadata.setLiked(LIKE_STATUS_LIKE.equals(likeStatus));
        videoMetadata.setDisliked(LIKE_STATUS_DISLIKE.equals(likeStatus));
        videoMetadata.setChannelId(str(CHANNEL_ID));

        return videoMetadata;
    }

    private VideoMetadata initNextVideo() {
        VideoMetadata nextVideoMetadata = new VideoMetadata();
        nextVideoMetadata.setTitle(str(NEXT_VIDEO_TITLE));
        nextVideoMetadata.setVideoId(str(NEXT_VIDEO_ID));

        return nextVideoMetadata;
    }

    private Boolean bool(String path) {
        Boolean result = ParserUtils.extractBool(path, mParser);
        
        if (result == null) {
            Log.d(TAG, "Oops... seems that video metadata format has been changed: " + path);
        }

        return result;
    }

    private String str(String path) {
        String result = ParserUtils.extractString(path, mParser);

        if (result == null) {
            Log.d(TAG, "Oops... seems that video metadata format has been changed: " + path);
        }

        return result;
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
        private String mChannelId;

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

        public String getChannelId() {
            return mChannelId;
        }

        public void setChannelId(String channelId) {
            mChannelId = channelId;
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

        public static VideoMetadata fromIntent(Intent intent) {
            if (intent == null) {
                return null;
            }

            VideoMetadata metadata = new VideoMetadata();
            metadata.mTitle = intent.getStringExtra(ExoPlayerFragment.VIDEO_TITLE);
            metadata.mViewCount = intent.getStringExtra(ExoPlayerFragment.VIDEO_VIEW_COUNT);
            metadata.mPublishedDate = intent.getStringExtra(ExoPlayerFragment.VIDEO_DATE);
            metadata.mLiked = intent.getBooleanExtra(ExoPlayerFragment.BUTTON_LIKE, false);
            metadata.mDisliked = intent.getBooleanExtra(ExoPlayerFragment.BUTTON_DISLIKE, false);
            metadata.mSubscribed = intent.getBooleanExtra(ExoPlayerFragment.BUTTON_SUBSCRIBE, false);

            return metadata;
        }
    }
}
