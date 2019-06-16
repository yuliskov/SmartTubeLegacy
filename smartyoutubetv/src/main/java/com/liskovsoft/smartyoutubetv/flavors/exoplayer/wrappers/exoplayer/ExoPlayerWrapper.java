package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.exoplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ActionsReceiver;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ActionsReceiver.Listener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ActionsSender;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.BackgroundActionManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.SampleHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.fragments.PlayerListener;
import com.liskovsoft.smartyoutubetv.fragments.TwoFragmentManager;
import com.liskovsoft.smartyoutubetv.misc.YouTubeTracker;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.squareup.otto.Subscribe;

import java.io.InputStream;
import java.util.List;

public class ExoPlayerWrapper extends OnMediaFoundCallback implements PlayerListener {
    private static final String TAG = ExoPlayerWrapper.class.getSimpleName();
    private final SuggestionsWatcher mReceiver; // don't delete, its system bus receiver
    private final ActionsSender mActionSender;
    private final ExoInterceptor mInterceptor;
    private final YouTubeTracker mTracker;
    private GenericInfo mInfo;
    private Sample mSample;
    private final Context mContext;
    private final TwoFragmentManager mFragmentsManager;
    private final BackgroundActionManager mManager;
    private Intent mCachedIntent;
    private static final String PARAM_VIDEO_ID = "video_id";
    private static final String ACTION_CLOSE_SUGGESTIONS = "action_close_suggestions";
    private static final String ACTION_DISABLE_KEY_EVENTS = "action_disable_key_events";
    private Uri mTrackingUrl;
    private final Runnable mOnNewVideo;
    private final Runnable mOnResume;
    private final Handler mHandler;
    private static final long BROWSER_INIT_TIME_MS = 10_000;
    private boolean mBlockHandlers;

    // player is opened from from get_video_info url
    // pause every time, except when mirroring
    private class BrowserStateListener implements Listener { // player is opened from from get_video_info url
        @Override
        public void onDone(Intent state) {
            Log.d(TAG, "About to start ExoPlayer fragment...");

            if (mBlockHandlers) {
                Log.d(TAG, "Browser state callback hab been canceled");
                return;
            }

            if (Log.getLogType() == Log.LOG_TYPE_FILE) {
                Log.d(TAG, "Passing browser state to ExoPlayer: " + state.getExtras());
            }

            boolean pauseBrowser = !mManager.isMirroring(mInterceptor.getCurrentUrl());

            mFragmentsManager.openExoPlayer(state, pauseBrowser);
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "Browser state is empty");
            //mManager.onCancel();
        }
    }

    private class SuggestionsWatcher {
        SuggestionsWatcher() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEvent event) {
            String action = event.getResult();

            switch (action) {
                case ACTION_CLOSE_SUGGESTIONS:
                    returnToPlayer();
                case ACTION_DISABLE_KEY_EVENTS:
                    mFragmentsManager.disableKeyEvents();
            }
        }

        private void returnToPlayer() {
            new Handler(mContext.getMainLooper()).post(() -> {
                if (mCachedIntent != null) {
                    prepareAndOpenExoPlayer(null); // player should already be running so pass null
                }
            });
        }
    }

    public ExoPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;

        mFragmentsManager = interceptor.getFragmentsManager();
        mManager = interceptor.getBackgroundActionManager();

        mReceiver = new SuggestionsWatcher();
        mActionSender = new ActionsSender(context, interceptor);

        // bind onPlayerAction callback
        mFragmentsManager.setPlayerListener(this);
        mTracker = new YouTubeTracker(mContext);

        mOnNewVideo = () -> new ActionsReceiver(mContext, new Intent(), new BrowserStateListener()).run();
        mOnResume = () -> {
            boolean pauseBrowser = !mManager.isMirroring(mInterceptor.getCurrentUrl());
            mFragmentsManager.openExoPlayer(null, pauseBrowser);
        };

        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onStart() {
        mBlockHandlers = true;
        clearPendingEvents();
        mFragmentsManager.openExoPlayer(null, false);
    }

    @Override
    public void onDashUrlFound(Uri dashUrl) {
        mSample = SampleHelpers.buildFromMpdUri(dashUrl);
    }

    @Override
    public void onHLSFound(final Uri hlsUrl) {
        mSample = SampleHelpers.buildFromHlsUri(hlsUrl);
    }

    @Override
    public void onDashMPDFound(final InputStream mpdContent) {
        mSample = SampleHelpers.buildFromMPDPlaylist(mpdContent);
    }

    @Override
    public void onUrlListFound(final List<String> urlList) {
        mSample = SampleHelpers.buildFromList(urlList);
    }

    @Override
    public void onInfoFound(GenericInfo info) {
        mInfo = info;
    }

    @Override
    public void onTrackingUrlFound(Uri trackingUrl) {
        mTrackingUrl = trackingUrl;
    }

    @Override
    public void onDone() {
        if (mSample == null || mInfo == null) {
            mManager.onCancel();
            return;
        }

        Log.d(TAG, "Video info has been parsed... opening exoplayer...");

        Intent exoIntent = createExoIntent(mSample, mInfo);
        prepareAndOpenExoPlayer(exoIntent);
    }

    private Intent createExoIntent(Sample sample, GenericInfo info) {
        final Intent playerIntent = sample.buildIntent(mContext);
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_TITLE, info.getTitle());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_AUTHOR, info.getAuthor());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_VIEW_COUNT, info.getViewCount());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_ID, extractVideoId());
        mCachedIntent = playerIntent;
        return playerIntent;
    }

    private String extractVideoId() {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(mInterceptor.getCurrentUrl());
        return query.get(PARAM_VIDEO_ID);
    }

    private void prepareAndOpenExoPlayer(final Intent playerIntent) {
        mBlockHandlers = false;
        clearPendingEvents();

        if (playerIntent == null) {
            Log.d(TAG, "Switching to the running player from suggestions or user's page");
            mFragmentsManager.openExoPlayer(null, false); // player is opened from suggestions
            mHandler.postDelayed(mOnResume, BROWSER_INIT_TIME_MS);
            return;
        }

        mFragmentsManager.openExoPlayer(playerIntent, false); // pause every time, except when mirroring
        mManager.onOpen();

        // give the browser time to initialization
        mHandler.postDelayed(mOnNewVideo, BROWSER_INIT_TIME_MS);
    }

    @Override
    public void onPlayerAction(Intent intent) {
        mBlockHandlers = true;
        clearPendingEvents();

        boolean doNotClose =
                intent.getBooleanExtra(ExoPlayerFragment.BUTTON_USER_PAGE, false) ||
                intent.getBooleanExtra(ExoPlayerFragment.BUTTON_SUGGESTIONS, false) ||
                intent.getBooleanExtra(ExoPlayerFragment.BUTTON_FAVORITES, false);

        if (!doNotClose) {
            if (mTrackingUrl != null) {
                mTracker.track(mTrackingUrl.toString(), mInterceptor.getCurrentUrl());
            }
            mManager.onClose();
        }

        mActionSender.bindActions(intent);
    }

    private void clearPendingEvents() {
        mHandler.removeCallbacks(mOnNewVideo);
        mHandler.removeCallbacks(mOnResume);
    }
}
