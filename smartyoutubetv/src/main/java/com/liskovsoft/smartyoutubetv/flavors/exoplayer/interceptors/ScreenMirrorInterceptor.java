package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.ExoIntent;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ScreenMirrorInterceptor extends RequestInterceptor {
    private static final String TAG = ScreenMirrorInterceptor.class.getSimpleName();
    private final SmartPreferences mPrefs;
    private final MainExoInterceptor mExoRootInterceptor;
    private boolean mPrevPaused;
    private long mStartPlayMS;
    private int mDelta;
    private LoungeData mPrevLoungeData;

    public ScreenMirrorInterceptor(Context context, MainExoInterceptor exoRootInterceptor) {
        super(context);
        mExoRootInterceptor = exoRootInterceptor;
        mPrefs = CommonApplication.getPreferences();
    }

    @Override
    public boolean test(String url) {
        return mPrefs.isMirrorEnabled();
    }

    @Override
    public WebResourceResponse intercept(String url) {
        WebResourceResponse res = null;

        String postData = mPrefs.getPostData();

        Log.d(TAG, url);
        Log.d(TAG, postData);

        if (mPrefs.isMirrorEnabled() && postData != null) {
            LoungeData loungeData = LoungeData.parse(postData, url);

            if (loungeData.isDisconnected()) {
                mExoRootInterceptor.getTwoFragmentManager().closeExoPlayer();
            } else {
                if (loungeData.getState() != LoungeData.STATE_UNDEFINED) {
                    fixLoungeState(loungeData);

                    calcPausedPosition(loungeData);

                    fixLoungeTime(loungeData);

                    res = postFormData(loungeData.getUrl(), loungeData.toString());

                    syncPlayer(loungeData);
                }
            }
        }

        return res;
    }

    private void fixLoungeTime(LoungeData loungeData) {
        if (loungeData.getCurrentTime() >= loungeData.getDuration()) {
            loungeData.setCurrentTime(0);
        }
    }

    private void syncPlayer(LoungeData loungeData) {
        boolean stateActual = (System.currentTimeMillis() - mPrefs.getLastVideoActionTimeMS()) < 1_000;
        Log.d(TAG, "syncPlayer: State is actual? " + stateActual);
        if (stateActual && (loungeData.getState() == LoungeData.STATE_PAUSED ||
            loungeData.getState() == LoungeData.STATE_PLAYING)) {
            ExoIntent exoIntent = new ExoIntent();
            exoIntent.setPositionSec(loungeData.getCurrentTime());
            exoIntent.setPaused(mPrefs.getHtmlVideoPaused());
            mExoRootInterceptor.getTwoFragmentManager().openExoPlayer(exoIntent.toIntent(), false);
        }
    }

    private void fixLoungeState(LoungeData loungeData) {
        if (loungeData.getState() == LoungeData.STATE_PAUSED &&
                !mPrefs.getHtmlVideoPaused()) {
            loungeData.setState(LoungeData.STATE_PLAYING);
        }
    }

    /**
     * Remember, html video object always paused.<br/>
     * So position isn't tracked here.
     */
    private void calcPausedPosition(LoungeData loungeData) {
        if (!mPrevPaused && !mPrefs.getHtmlVideoPaused()) {
            mDelta = 0;
        }

        if (mPrevPaused && mPrefs.getHtmlVideoPaused()) {
            mDelta = 0;
        }

        if (!mPrevPaused && mPrefs.getHtmlVideoPaused()) {
            mDelta += (int) (System.currentTimeMillis() - mStartPlayMS) / 1000;
            loungeData.setCurrentTime(loungeData.getCurrentTime() + mDelta); // set real position here
        }

        if (mPrevPaused && !mPrefs.getHtmlVideoPaused()) {
            loungeData.setCurrentTime(loungeData.getCurrentTime() + mDelta);
        }

        if (!mPrefs.getHtmlVideoPaused()) {
            mStartPlayMS = System.currentTimeMillis();
        }

        mPrevPaused = mPrefs.getHtmlVideoPaused();
    }
}
