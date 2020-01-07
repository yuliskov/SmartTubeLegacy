package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.content.Intent;
import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

import java.io.InputStream;
import java.util.Arrays;

public class JsonNextParser {
    private static final String TAG = JsonNextParser.class.getSimpleName();
    private static final String VIDEO_DATA_ROOT = "$.contents.singleColumnWatchNextResults.results.results.contents[0].itemSectionRenderer.contents[0].videoMetadataRenderer";
    private static final String VIDEO_DATA_ROOT2 = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.replayVideoRenderer.pivotVideoRenderer";
    private static final String VIDEO_OWNER_ROOT = "$.contents.singleColumnWatchNextResults.results.results.contents[1].itemSectionRenderer.contents[0].videoOwnerRenderer";
    private static final String NEXT_VIDEO_DATA_ROOT = "$.contents.singleColumnWatchNextResults.autoplay.autoplay.sets[0].nextVideoRenderer";
    private static final String VIDEO_TITLE =           VIDEO_DATA_ROOT + ".title.runs[0].text";
    private static final String VIEW_COUNT =            VIDEO_DATA_ROOT + ".shortViewCountText.runs[0].text";
    private static final String VIEW_COUNT_FULL =       VIDEO_DATA_ROOT + ".viewCountText.runs[0].text";
    private static final String LIKES_COUNT =           VIDEO_DATA_ROOT + ".likesCount.runs[0].text";
    private static final String DISLIKES_COUNT =        VIDEO_DATA_ROOT + ".dislikesCount.runs[0].text";
    private static final String DESCRIPTION =           VIDEO_DATA_ROOT + ".description.runs[0].text";
    private static final String PUBLISHED_DATE =        VIDEO_DATA_ROOT + ".publishedTimeText.runs[0].text";
    private static final String PUBLISHED_DATE_FULL =   VIDEO_DATA_ROOT + ".dateText.runs[0].text";
    private static final String VIDEO_ID =              VIDEO_DATA_ROOT + ".videoId";
    private static final String LIKE_STATUS =           VIDEO_DATA_ROOT + ".likeStatus";
    // NOTE: PERCENT_WATCHED not working for current video
    private static final String PERCENT_WATCHED =       VIDEO_DATA_ROOT + ".thumbnailOverlays[0].thumbnailOverlayResumePlaybackRenderer.percentDurationWatched";
    private static final String VIDEO_AUTHOR =  VIDEO_OWNER_ROOT + ".title.runs[0].text";
    private static final String IS_SUBSCRIBED = VIDEO_OWNER_ROOT + ".subscribeButton.subscribeButtonRenderer.subscribed";
    private static final String CHANNEL_ID =    VIDEO_OWNER_ROOT + ".subscribeButton.subscribeButtonRenderer.channelId";
    // Video duration example: "4:27"
    private static final String VIDEO_DURATION =    VIDEO_DATA_ROOT2 + ".lengthText.runs[0].text";
    private static final String NEXT_VIDEO_ID =             NEXT_VIDEO_DATA_ROOT + ".maybeHistoryEndpointRenderer.endpoint.watchEndpoint.videoId";
    private static final String NEXT_VIDEO_ID_2 =           NEXT_VIDEO_DATA_ROOT + ".autoplayEndpointRenderer.endpoint.watchEndpoint.videoId";
    private static final String NEXT_VIDEO_PLAYLIST_ID =    NEXT_VIDEO_DATA_ROOT + ".autoplayEndpointRenderer.endpoint.watchEndpoint.playlistId";
    private static final String NEXT_VIDEO_TITLE =          NEXT_VIDEO_DATA_ROOT + ".maybeHistoryEndpointRenderer.item.previewButtonRenderer.title.runs[0].text";
    private static final String LIKE_STATUS_LIKE = "LIKE";
    private static final String LIKE_STATUS_DISLIKE = "DISLIKE";
    private static final String LIKE_STATUS_INDIFFERENT = "INDIFFERENT";
    private final DocumentContext mParser;

    public <T> JsonNextParser(T nextContent) {
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
        videoMetadata.setAuthor(str(VIDEO_AUTHOR));
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
        videoMetadata.setPercentWatched(integer(PERCENT_WATCHED));

        return videoMetadata;
    }

    private VideoMetadata initNextVideo() {
        VideoMetadata nextVideoMetadata = new VideoMetadata();
        nextVideoMetadata.setTitle(str(NEXT_VIDEO_TITLE));
        nextVideoMetadata.setVideoId(str(NEXT_VIDEO_ID, NEXT_VIDEO_ID_2));
        nextVideoMetadata.setPlaylistId(str(NEXT_VIDEO_PLAYLIST_ID));

        return nextVideoMetadata;
    }

    private Integer integer(String... paths) {
        Integer result = null;

        for (String path : paths) {
            result = ParserUtils.extractInt(path, mParser);
            if (result != null) {
                break;
            }
        }

        if (result == null) {
            Log.e(TAG, "Oops... seems that video metadata format has been changed: " + Arrays.toString(paths));
        }

        return result;
    }

    private Boolean bool(String... paths) {
        Boolean result = null;

        for (String path : paths) {
            result = ParserUtils.extractBool(path, mParser);
            if (result != null) {
                break;
            }
        }
        
        if (result == null) {
            Log.d(TAG, "Oops... seems that video metadata format has been changed: " + Arrays.toString(paths));
        }

        return result;
    }

    private String str(String... paths) {
        String result = null;

        for (String path : paths) {
            result = ParserUtils.extractString(path, mParser);
            if (result != null) {
                break;
            }
        }

        if (result == null) {
            Log.d(TAG, "Oops... seems that video metadata format has been changed: " + Arrays.toString(paths));
        }

        return result;
    }

    public static class VideoMetadata {
        private String mTitle;
        private String mAuthor;
        private String mViewCount;
        private String mLikesCount;
        private String mDislikesCount;
        private String mDescription;
        private String mPublishedDate;
        private Boolean mSubscribed;
        private Boolean mLiked;
        private Boolean mDisliked;
        private String mVideoId;
        private String mChannelId;
        private String mPlaylistId;
        private Integer mPercentWatched;

        private VideoMetadata mNextVideo;

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public void setAuthor(String author) {
            mAuthor = author;
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

        public Boolean isSubscribed() {
            return mSubscribed;
        }

        public void setSubscribed(Boolean subscribed) {
            mSubscribed = subscribed;
        }

        public Boolean isLiked() {
            return mLiked;
        }

        public void setLiked(Boolean liked) {
            mLiked = liked;
        }

        public Boolean isDisliked() {
            return mDisliked;
        }

        public void setDisliked(Boolean disliked) {
            mDisliked = disliked;
        }

        public String getChannelId() {
            return mChannelId;
        }

        public void setChannelId(String channelId) {
            mChannelId = channelId;
        }

        public String getPlaylistId() {
            return mPlaylistId;
        }

        public void setPlaylistId(String playlistId) {
            mPlaylistId = playlistId;
        }

        public Integer getPercentWatched() {
            return mPercentWatched;
        }

        public void setPercentWatched(Integer percentWatched) {
            mPercentWatched = percentWatched;
        }

        public Intent toIntent() {
            Intent intent = new Intent();
            intent.putExtra(ExoPlayerFragment.VIDEO_TITLE, mTitle);
            intent.putExtra(ExoPlayerFragment.VIDEO_AUTHOR, mAuthor);
            intent.putExtra(ExoPlayerFragment.VIDEO_VIEW_COUNT, mViewCount);
            intent.putExtra(ExoPlayerFragment.VIDEO_DATE, mPublishedDate);
            intent.putExtra(ExoPlayerFragment.BUTTON_LIKE, mLiked);
            intent.putExtra(ExoPlayerFragment.BUTTON_DISLIKE, mDisliked);
            intent.putExtra(ExoPlayerFragment.BUTTON_SUBSCRIBE, mSubscribed);
            intent.putExtra(ExoPlayerFragment.PERCENT_WATCHED, mPercentWatched);

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
            metadata.mLiked =
                    intent.hasExtra(ExoPlayerFragment.BUTTON_LIKE) ?
                    intent.getBooleanExtra(ExoPlayerFragment.BUTTON_LIKE, false) :
                    null;
            metadata.mDisliked = intent.hasExtra(ExoPlayerFragment.BUTTON_DISLIKE) ?
                    intent.getBooleanExtra(ExoPlayerFragment.BUTTON_DISLIKE, false) :
                    null;
            metadata.mSubscribed = intent.hasExtra(ExoPlayerFragment.BUTTON_SUBSCRIBE) ?
                    intent.getBooleanExtra(ExoPlayerFragment.BUTTON_SUBSCRIBE, false) :
                    null;

            return metadata;
        }
    }
}
