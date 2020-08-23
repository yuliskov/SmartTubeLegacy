package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.exoplayer.ExoPlayerWrapper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer.ExternalPlayerWrapper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser;
import com.liskovsoft.smartyoutubetv.fragments.TwoFragmentManager;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator.YouTubeHelpers;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final String TAG = ExoInterceptor.class.getSimpleName();
    private final DelayedCommandCallInterceptor mDelayedInterceptor;
    private final BackgroundActionManager mManager;
    private final TwoFragmentManager mFragmentsManager;
    private OnMediaFoundCallback mExoCallback;
    private OnMediaFoundCallback mRealExoCallback;
    private final ExoNextInterceptor mNextInterceptor;
    private final HistoryInterceptor mHistoryInterceptor;
    private final SmartPreferences mPrefs;
    private final ActionsSender mSender;
    private String mCurrentUrl;
    public static final String URL_VIDEO_DATA = "get_video_info";
    public static final String URL_TV_TRANSPORT = "gen_204";
    private final boolean mIsBeta;
    private String mOriginUrl;

    public ExoInterceptor(Context context,
                          DelayedCommandCallInterceptor delayedInterceptor,
                          ExoNextInterceptor nextInterceptor,
                          HistoryInterceptor historyInterceptor) {
        super(context);
        
        mContext = context;
        mFragmentsManager = (TwoFragmentManager) context;
        mDelayedInterceptor = delayedInterceptor;
        mNextInterceptor = nextInterceptor;
        mHistoryInterceptor = historyInterceptor;
        mManager = new BackgroundActionManager(mFragmentsManager.getKeyHandler());
        mPrefs = SmartPreferences.instance(mContext);
        mSender = new ActionsSender(mContext, this);
        
        boolean useExternalPlayer = !SmartPreferences.USE_EXTERNAL_PLAYER_NONE.equals(mPrefs.getUseExternalPlayer());

        if (useExternalPlayer) {
            mExoCallback = ExternalPlayerWrapper.create(mContext, this);
        } else {
            mRealExoCallback = mExoCallback = new ExoPlayerWrapper(mContext, this);
        }

        mIsBeta = "beta".equals(BuildConfig.FLAVOR);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video intercepted: " + url);

        mOriginUrl = url;

        if (mRealExoCallback != null) { // video may be processed externally, so we need to restore
            mExoCallback = mRealExoCallback;
        }

        mCurrentUrl = unlockVideos(url);

        mManager.init(mCurrentUrl);

        // 'next' should not be fired at this point
        if (mManager.cancelPlayback()) {
            Log.d(TAG, "Video canceled: " + mCurrentUrl);

            if (mIsBeta) {
                MessageHelpers.showMessage(mContext, mManager.getReason());
            }

            if (mManager.isOpened()) { // return to player when suggestions doesn't work
                mExoCallback.onFalseCall();
            }

            return filterVideoInfoResponse(mOriginUrl);
        }

        return processCurrentUrl();
    }

    private WebResourceResponse processCurrentUrl() {
        mExoCallback.onStart();

        // Video title and other infos
        // long running code
        new Thread(() -> {
            mExoCallback.onMetadata(
                    mNextInterceptor.getMetadata(
                            mManager.getVideoId(mCurrentUrl),
                            mManager.getPlaylistId(mCurrentUrl)));
        }).start();

        // Clip content
        // long running code
        new Thread(() -> {
            try {
                parseAndOpenExoPlayer(getUrlData(mCurrentUrl));
            } catch (IllegalStateException e) {
                e.printStackTrace();
                Log.e(TAG, "Url doesn't exist or its content broken: " + mCurrentUrl);
                MessageHelpers.showLongMessage(mContext, "Url doesn't exist or its content is broken. " + e.getMessage());
            }
        }).start();

        return filterVideoInfoResponse(mOriginUrl);
    }

    /**
     * For parsing details see {@link YouTubeMediaParser}
     */
    private void parseAndOpenExoPlayer(InputStream inputStream) {
        final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mContext, inputStream);
        Log.d(TAG, "Video manifest received");
        dataParser.parse(mExoCallback);
    }

    public void updateLastCommand(GenericCommand command) {
        mDelayedInterceptor.setCommand(command);
        // force call command without adding to the history (in case WebView)
        mDelayedInterceptor.forceRun(true);
    }

    public TwoFragmentManager getFragmentsManager() {
        return mFragmentsManager;
    }

    public BackgroundActionManager getBackgroundActionManager() {
        return mManager;
    }

    public String getCurrentUrl() {
        return mCurrentUrl;
    }

    public void closeVideo() {
        if (mRealExoCallback != null) { // don't response in exo mode
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(ExoPlayerFragment.BUTTON_BACK, true);
        mSender.bindActions(intent);
        mManager.onCancel();
    }

    public void jumpToNextVideo() {
        if (mRealExoCallback != null) { // don't response in exo mode
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(ExoPlayerFragment.BUTTON_NEXT, true);
        mSender.bindActions(intent);
        mManager.onContinue();
    }

    public HistoryInterceptor getHistoryInterceptor() {
        return mHistoryInterceptor;
    }
    
    private String unlockVideos(String url) {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);

        fixJsonError(query);

        switch(mPrefs.getCurrentVideoType()) {
            case SmartPreferences.VIDEO_TYPE_DEFAULT:
                // NOTE: should be unlocked in video_info_interceptor.js
                // YouTubeHelpers.unlockAgeRestrictedVideos(query);
                break;
            case SmartPreferences.VIDEO_TYPE_LIVE:
            case SmartPreferences.VIDEO_TYPE_UPCOMING:
            case SmartPreferences.VIDEO_TYPE_UNDEFINED:
                YouTubeHelpers.unlockLiveStreams(query);
                break;
        }

        return query.toString();
    }

    public void openExternally(OnMediaFoundCallback playerWrapper) {
        mExoCallback = playerWrapper;
        processCurrentUrl();
    }

    private void fixJsonError(MyUrlEncodedQueryString query) {
        query.remove("access_token");
        query.remove("action_display_post");
    }
}
