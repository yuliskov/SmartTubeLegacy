package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.interceptors.ads.AdAwayInterceptor;
import com.liskovsoft.smartyoutubetv.interceptors.ads.BrowseAdInterceptor;
import com.liskovsoft.smartyoutubetv.interceptors.ads.VideoInfoAdInterceptor;
import com.liskovsoft.smartyoutubetv.interceptors.scripts.LegacyMainScriptManagerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link RequestInterceptorProcessor} represents a processing of {@link RequestInterceptor}
 */
public class RequestInterceptorProcessor {
    private static final String TAG = RequestInterceptorProcessor.class.getSimpleName();
    private final List<RequestInterceptor> mInterceptors;
    //private final FileExtensionInterceptor mFileExtensionInterceptor;

    public RequestInterceptorProcessor(Context context) {
        //mFileExtensionInterceptor = new FileExtensionInterceptor(context);
        mInterceptors = new ArrayList<>();
        mInterceptors.add(new AdAwayInterceptor(context));
        mInterceptors.add(new BrowseAdInterceptor(context)); // impact performance
        mInterceptors.add(new VideoInfoAdInterceptor(context)); // impact performance
        //mInterceptors.add(new ContentSecurityPolicyInterceptor(context));
        mInterceptors.add(new OpenExternalPlayerInterceptor(context));
        mInterceptors.add(new PlaybackStatsInterceptor(context));
        mInterceptors.add(new LegacyMainScriptManagerInterceptor(context));
        //mInterceptors.add(new AllUrlsInterceptor(context));
    }

    /**
     * Calling interceptors until encountering non-null response.<br/>
     * So, interceptors order is important!<br/>
     * It's critical for Ad blocking, for example.
     */
    public WebResourceResponse process(String url) {
        Log.d(TAG, "Intercepting url: " + url);

        WebResourceResponse result = null;

        // don't process media files
        //if (mFileExtensionInterceptor.test(url)) {
        //    return null;
        //}

        for (RequestInterceptor interceptor : mInterceptors) {
            if (interceptor.test(url)) {
                result = interceptor.intercept(url);

                // Stop calling interceptors when encountering non-null response.
                // Usually last interception in the list
                if (result != null) {
                    break;
                }
            }
        }

        return result;
    }
}
