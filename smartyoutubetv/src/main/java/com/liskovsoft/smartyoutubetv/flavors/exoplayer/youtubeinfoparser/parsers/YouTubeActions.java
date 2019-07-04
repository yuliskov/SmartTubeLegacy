package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.misc.HeaderManager;
import okhttp3.Response;

public class YouTubeActions {
    private static final String TAG = YouTubeActions.class.getSimpleName();
    private final static String LIKE_URL = "https://www.youtube.com/youtubei/v1/like/like";
    private final static String DISLIKE_URL = "https://www.youtube.com/youtubei/v1/like/dislike";
    private final static String REMOVE_LIKE_URL = "https://www.youtube.com/youtubei/v1/like/removelike";
    private final static String SUBSCRIBE_URL = "https://www.youtube.com/youtubei/v1/subscription/subscribe";
    private final static String UNSUBSCRIBE_URL = "https://www.youtube.com/youtubei/v1/subscription/unsubscribe";
    private final static String POST_BODY = "{\"context\":{\"client\":{\"clientName\":\"TVHTML5\",\"clientVersion\":\"6.20180913\"," +
            "\"screenWidthPoints\":1280,\"screenHeightPoints\":720,\"screenPixelDensity\":1,\"theme\":\"CLASSIC\",\"utcOffsetMinutes\":180," +
            "\"webpSupport\":false,\"animatedWebpSupport\":false,\"tvAppInfo\":{\"appQuality\":\"TV_APP_QUALITY_LIMITED_ANIMATION\"}," +
            "\"acceptRegion\":\"UA\",\"deviceMake\":\"LG\",\"deviceModel\":\"42LA660S-ZA\",\"platform\":\"TV\"}," + "\"request" +
            "\":{\"consistencyTokenJars\":[]},\"user\":{\"enableSafetyMode\":false},\"clientScreenNonce\":\"MC4zODEwOTU4MjI5NDAwMzQ1\"}," +
            "%DATA_OBJ%}";
    private final static String VIDEO_ID_OBJ = "\"target\":{\"videoId\":\"%VIDEO_ID%\"}";
    private final static String CHANNEL_ID_OBJ = "\"channelIds\":[\"%CHANNEL_ID%\"]";
    private final Context mContext;
    private final HeaderManager mManager;
    private final String mLikePostBody;
    private final String mSubPostBody;

    public YouTubeActions(Context context) {
        mContext = context;
        mManager = new HeaderManager(mContext);
        mLikePostBody = POST_BODY.replace("%DATA_OBJ%", VIDEO_ID_OBJ);
        mSubPostBody = POST_BODY.replace("%DATA_OBJ%", CHANNEL_ID_OBJ);
    }

    public void like(String videoId) {
        postUrlData(LIKE_URL, mLikePostBody.replace("%VIDEO_ID%", videoId));
    }

    public void removeLike(String videoId) {
        postUrlData(REMOVE_LIKE_URL, mLikePostBody.replace("%VIDEO_ID%", videoId));
    }

    public void dislike(String videoId) {
        postUrlData(DISLIKE_URL, mLikePostBody.replace("%VIDEO_ID%", videoId));
    }

    public void subscribe(String channelId) {
        if (channelId == null) {
            return;
        }

        postUrlData(SUBSCRIBE_URL, mSubPostBody.replace("%CHANNEL_ID%", channelId));
    }

    public void unsubscribe(String channelId) {
        if (channelId == null) {
            return;
        }

        postUrlData(UNSUBSCRIBE_URL, mSubPostBody.replace("%CHANNEL_ID%", channelId));
    }

    /**
     * Compares objects and if needed compareAndApply new values
     */
    public void compareAndApply(VideoMetadata oldMetadata, VideoMetadata newMetadata) {
        Runnable removeLike = () -> removeLike(oldMetadata.getVideoId());
        Runnable addLike = () -> like(oldMetadata.getVideoId());
        Runnable addDislike = () -> dislike(oldMetadata.getVideoId());
        Runnable addSubscribe = () -> subscribe(oldMetadata.getChannelId());
        Runnable removeSubscribe = () -> unsubscribe(oldMetadata.getChannelId());

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

    private void apply(Boolean oldVal, Boolean newVal, Runnable positive, Runnable negative) {
        if (oldVal == null || newVal == null) {
            return;
        }

        if (!oldVal && newVal) {
            positive.run();
        } else if (oldVal && !newVal) {
            negative.run();
        }
    }

    private void postUrlData(String url, String body) {
        if (Looper.myLooper() == Looper.getMainLooper()) { // we are on the main thread
            new Thread(() -> postUrlDataReal(url, body)).start();
        } else {
            postUrlDataReal(url, body);
        }
    }

    private void postUrlDataReal(String url, String body) {
        OkHttpHelpers.doPostOkHttpRequest(url, mManager.getHeaders(), body, "application/json");
    }
}
