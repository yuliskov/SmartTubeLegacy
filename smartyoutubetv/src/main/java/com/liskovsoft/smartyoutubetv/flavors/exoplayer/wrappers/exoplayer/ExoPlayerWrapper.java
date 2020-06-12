package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.exoplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ActionsSender;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.BackgroundActionManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.HistoryInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.SampleHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.SampleHelpers.Sample;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer.ExternalPlayerWrapper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd.MPDBuilder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.fragments.PlayerListener;
import com.liskovsoft.smartyoutubetv.fragments.TwoFragmentManager;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.squareup.otto.Subscribe;

import java.util.List;

public class ExoPlayerWrapper extends OnMediaFoundCallback implements PlayerListener {
    private static final String TAG = ExoPlayerWrapper.class.getSimpleName();
    private final SuggestionsWatcher mReceiver; // don't delete, its system bus receiver
    private final ActionsSender mActionSender;
    private final ExoInterceptor mInterceptor;
    private final HistoryInterceptor mHistory;
    private final SmartPreferences mPrefs;
    private GenericInfo mInfo;
    private String mSpec;
    private Uri mHlsUrl;
    private Uri mTrackingUrl;
    private Uri mRealTrackingUrl;
    private final Context mContext;
    private final TwoFragmentManager mFragmentsManager;
    private final BackgroundActionManager mManager;
    private Intent mCachedIntent;
    private static final String PARAM_VIDEO_ID = "video_id";
    private static final String ACTION_CLOSE_SUGGESTIONS = "action_close_suggestions";
    private static final String ACTION_DISABLE_KEY_EVENTS = "action_disable_key_events";
    private static final long BROWSER_INIT_TIME_MS = 10_000;
    private final Runnable mPauseBrowser;
    private boolean mDoPauseBrowser;
    private final Handler mHandler;
    private boolean mBlockHandlers;
    private VideoMetadata mMetadata;
    private boolean mPlayerClosed;
    private Uri mDashUrl;
    private Intent mExoIntent;
    private ExternalPlayerWrapper mExternalPlayerWrapper;
    private MPDBuilder mMpdBuilder;
    private List<String> mUrlList;

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
                //case ACTION_DISABLE_KEY_EVENTS:
                //    mFragmentsManager.disableKeyEvents();
            }
        }

        private void returnToPlayer() {
            mHandler.post(() -> {
                if (mCachedIntent != null && !mPlayerClosed) {
                    Log.d(TAG, "Switching to the running player from suggestions or user's page");
                    prepareAndOpenExoPlayer(null); // player should already be running so pass null
                }
            });
        }
    }

    public ExoPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        mHistory = interceptor.getHistoryInterceptor();

        mFragmentsManager = interceptor.getFragmentsManager();
        mManager = interceptor.getBackgroundActionManager();

        mReceiver = new SuggestionsWatcher();
        mActionSender = new ActionsSender(context, interceptor);

        // bind onPlayerAction callback
        mFragmentsManager.setPlayerListener(this);

        mPauseBrowser = () -> {
            if (mBlockHandlers) {
                Log.d(TAG, "Browser pause has been canceled");
                return;
            }

            mDoPauseBrowser = !mManager.isMirroring();
            startPlayer();
        };

        mHandler = new Handler(Looper.getMainLooper());
        mPrefs = CommonApplication.getPreferences();
    }

    @Override
    public void onStart() {
        cleanup();
        mPlayerClosed = false;
        mBlockHandlers = true;
        mDoPauseBrowser = false;
        clearPendingEvents();
        startPlayer();
    }

    @Override
    public void onDashUrlFound(Uri dashUrl) {
        mDashUrl = dashUrl;
    }

    @Override
    public void onHLSFound(final Uri hlsUrl) {
        mHlsUrl = hlsUrl;
    }

    @Override
    public void onDashMPDFound(final MPDBuilder mpdBuilder) {
        mMpdBuilder = mpdBuilder;
    }

    @Override
    public void onGenericInfoFound(GenericInfo info) {
        mInfo = info;
    }

    @Override
    public void onTrackingUrlFound(Uri trackingUrl) {
        mTrackingUrl = trackingUrl;
    }

    @Override
    public void onRealTrackingUrlFound(Uri trackingUrl) {
        mRealTrackingUrl = trackingUrl;
    }

    @Override
    public void onStorySpecFound(String spec) {
        mSpec = spec;
    }

    @Override
    public void onUrlListFound(List<String> uriList) {
        mUrlList = uriList;
    }

    @Override
    public void onMetadata(VideoMetadata metadata) {
        mMetadata = metadata;

        if (metadata != null && mExoIntent != null) { // called async
            startPlayer(metadata.toIntent());
        }
    }

    @Override
    public void onFalseCall() {
        if (!mPlayerClosed) {
            startPlayer();
        } else { // browser's player could be running in background
            sendClose();
        }
    }

    @Override
    public void onDone() {
        if (mPlayerClosed) {
            cleanup();
            return;
        }

        Sample sample = null;

        if (mHlsUrl != null) {
            sample = SampleHelpers.buildFromHlsUri(mHlsUrl);
        } else if (mMpdBuilder != null) {
            sample = SampleHelpers.buildFromMPDPlaylist(mMpdBuilder.build());
        } else if (mDashUrl != null) {
            sample = SampleHelpers.buildFromMpdUri(mDashUrl);
        } else if (mUrlList != null) {
            sample = SampleHelpers.buildFromList(mUrlList);
        }

        if (sample == null || mInfo == null) {
            cleanup();
            mManager.onCancel();
            mFragmentsManager.switchToBrowser(true);
            return;
        }

        Log.d(TAG, "Video info has been parsed... opening exoplayer...");

        mExoIntent = createExoIntent(sample, mInfo);
        prepareAndOpenExoPlayer(mExoIntent);
    }

    private void cleanup() {
        mDashUrl = null;
        mHlsUrl = null;
        mMpdBuilder = null;
        mSpec = null;
        mExoIntent = null;
        mUrlList = null;
    }

    private Intent createExoIntent(Sample sample, GenericInfo info) {
        final Intent playerIntent = sample.buildIntent(mContext);
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_TITLE, info.getTitle());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_AUTHOR, info.getAuthor());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_VIEW_COUNT, info.getViewCount());
        playerIntent.putExtra(ExoPlayerFragment.VIDEO_ID, extractVideoId());
        playerIntent.putExtra(ExoPlayerFragment.STORYBOARD_SPEC, mSpec);
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

        if (playerIntent != null) {
            if (mMetadata != null) {
                Helpers.mergeIntents(playerIntent, mMetadata.toIntent());
            }

            mManager.onOpen(); // notify that we about to open new video

            if (mHistory != null) {
                mHistory.onStart(); // notify that we about to open new video
            }

            //Intent intent = new Intent();
            //intent.putExtra(ExoPlayerFragment.VIDEO_STARTED, true);
            //mActionSender.bindActions(intent);
        }

        mPrefs.setMirrorEnabled(mManager.isMirroring());
        startPlayer(playerIntent); // pause every time, except when mirroring

        // give the browser time to initialization
        //mHandler.postDelayed(mPauseBrowser, BROWSER_INIT_TIME_MS);
    }

    @Override
    public void onPlayerAction(Intent intent) {
        mBlockHandlers = true;
        clearPendingEvents();

        if (intent.getBooleanExtra(ExoPlayerFragment.TRACK_ENDED, false) ||
            intent.getBooleanExtra(ExoPlayerFragment.BUTTON_NEXT, false) ||
            intent.getBooleanExtra(ExoPlayerFragment.BUTTON_PREV, false)) {
            cleanup();
            mPlayerClosed = true;
            mManager.onContinue();
        } else if (intent.getBooleanExtra(ExoPlayerFragment.BUTTON_BACK, false)) {
            cleanup();
            mPlayerClosed = true;
            mManager.onCancel();
        }

        if (mHistory != null) {
            mHistory.updatePosition(intent.getFloatExtra(ExoPlayerFragment.VIDEO_POSITION, 0));
        }

        mActionSender.bindActions(intent, mMetadata);
    }

    private void clearPendingEvents() {
        mHandler.removeCallbacks(mPauseBrowser);
    }

    private void startPlayer() {
        mFragmentsManager.openExoPlayer(null, mDoPauseBrowser);
    }

    private void startPlayer(Intent intent) {
        mFragmentsManager.openExoPlayer(intent, mDoPauseBrowser);
    }

    @Override
    public void openExternalPlayer(Intent intent) {
        if (mExternalPlayerWrapper == null) {
            mExternalPlayerWrapper = ExternalPlayerWrapper.create(mContext, mInterceptor);
        }

        mInterceptor.openExternally(mExternalPlayerWrapper);
    }

    private void sendClose() {
        Intent intent = new Intent();
        intent.putExtra(ExoPlayerFragment.BUTTON_BACK, true);
        mActionSender.bindActions(intent);
    }
}
