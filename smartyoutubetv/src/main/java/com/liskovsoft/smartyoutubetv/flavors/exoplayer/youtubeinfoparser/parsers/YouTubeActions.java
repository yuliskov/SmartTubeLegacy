package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;

public class YouTubeActions {
    private final Context mContext;

    public YouTubeActions(Context context) {
        mContext = context;
    }

    public void like(String videoId) {
        
    }

    public void removeLike(String videoId) {
        
    }

    public void dislike(String videoId) {
        
    }

    public void subscribe(String videoId) {

    }

    public void unsubscribe(String videoId) {

    }

    /**
     * Compares objects and if needed apply new values
     */
    public void apply(VideoMetadata oldMetadata, VideoMetadata newMetadata) {
        Runnable removeLike = () -> removeLike(oldMetadata.getVideoId());
        Runnable addLike = () -> like(oldMetadata.getVideoId());
        Runnable addDislike = () -> dislike(oldMetadata.getVideoId());
        Runnable addSubscribe = () -> subscribe(oldMetadata.getVideoId());
        Runnable removeSubscribe = () -> unsubscribe(oldMetadata.getVideoId());

        apply(
                oldMetadata.isLiked(),
                newMetadata.isLiked(),
                addLike,
                removeLike
        );

        apply(
                oldMetadata.isDisliked(),
                newMetadata.isDisliked(),
                addDislike,
                removeLike
        );

        apply(
                oldMetadata.isSubscribed(),
                newMetadata.isSubscribed(),
                addSubscribe,
                removeSubscribe
        );
    }

    private void apply(boolean oldVal, boolean newVal, Runnable positive, Runnable negative) {
        if (!oldVal && newVal) {
            positive.run();
        } else if (oldVal && !newVal) {
            negative.run();
        }
    }
}
