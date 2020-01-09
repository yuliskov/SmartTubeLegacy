package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
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
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

public class ExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final String TAG = ExoInterceptor.class.getSimpleName();
    private final DelayedCommandCallInterceptor mDelayedInterceptor;
    private final BackgroundActionManager mManager;
    private final TwoFragmentManager mFragmentsManager;
    private final OnMediaFoundCallback mExoCallback;
    private final ExoNextInterceptor mNextInterceptor;
    private final HistoryInterceptor mHistoryInterceptor;
    private final SmartPreferences mPrefs;
    private final ActionsSender mSender;
    private InputStream mResponseStreamSimple;
    private String mCurrentUrl;
    public static final String URL_VIDEO_DATA = "get_video_info";
    public static final String URL_TV_TRANSPORT = "gen_204";

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
        mManager = new BackgroundActionManager();
        mPrefs = SmartPreferences.instance(mContext);
        mSender = new ActionsSender(mContext, this);
        
        boolean useExternalPlayer = !SmartPreferences.USE_EXTERNAL_PLAYER_NONE.equals(mPrefs.getUseExternalPlayer());

        if (useExternalPlayer) {
            mExoCallback = ExternalPlayerWrapper.create(mContext, this);
        } else {
            mExoCallback = new ExoPlayerWrapper(mContext, this);
        }
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video intercepted: " + url);

        mCurrentUrl = url;

        mManager.init(url);

        // 'next' should not be fired at this point
        if (mManager.cancelPlayback()) {
            Log.d(TAG, "Video canceled: " + url);
            //if (mManager.isOpened()) // player is doing playback
            //    mReceiver.returnToPlayer();
            return null;
        }

        mExoCallback.onStart();
        mExoCallback.onMetadata(mNextInterceptor.getMetadata(mManager.getVideoId(mCurrentUrl), mManager.getPlaylistId(mCurrentUrl)));
        
        prepareResponseStream(url);
        parseAndOpenExoPlayer();

        return null;
    }

    // We also try looking in get_video_info since it may contain different dashmpd
    // URL that points to a DASH manifest with possibly different itag set (some itags
    // are missing from DASH manifest pointed by webpage's dashmpd, some - from DASH
    // manifest pointed by get_video_info's dashmpd).
    // The general idea is to take a union of itags of both DASH manifests (for example
    // video with such 'manifest behavior' see https://github.com/rg3/youtube-dl/issues/6093)
    private void prepareResponseStream(String url) {
        mResponseStreamSimple = getUrlData(url);
    }

    /**
     * For parsing details see {@link YouTubeMediaParser}
     */
    private void parseAndOpenExoPlayer() {
        final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mResponseStreamSimple);
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

    public void closePlayer() {
        Intent intent = new Intent();
        intent.putExtra(ExoPlayerFragment.BUTTON_BACK, true);
        mSender.bindActions(intent);
        mManager.onClose();
    }

    public HistoryInterceptor getHistoryInterceptor() {
        return mHistoryInterceptor;
    }
}
