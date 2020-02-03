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

    public ScreenMirrorInterceptor(Context context, MainExoInterceptor exoRootInterceptor) {
        super(context);
        mExoRootInterceptor = exoRootInterceptor;
        mPrefs = CommonApplication.getPreferences();
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        String postData = mPrefs.getPostData();

        Log.d(TAG, url);
        Log.d(TAG, postData);

        WebResourceResponse res = null;

        LoungeData loungeData = LoungeData.parse(postData);

        if (loungeData.getState() == LoungeData.STATE_PAUSED) {
            loungeData.setState(LoungeData.STATE_PLAYING);
            res = postFormData(url, loungeData.toString());
            ExoIntent exoIntent = new ExoIntent();
            exoIntent.setPositionSec(loungeData.getCurrentTime());
            mExoRootInterceptor.getTwoFragmentManager().openExoPlayer(exoIntent.toIntent(), false);
        }

        return res;
    }

}
