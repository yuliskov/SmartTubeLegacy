package com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter.ReplacingInputStream.ReplacePair;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ContentFilter {
    private static final String TAG = ContentFilter.class.getSimpleName();
    private final Context mContext;
    private ReplacePair[] mAdReplacement = {
            new ReplacePair("tvMastheadRenderer", "tvMastheadRendererOld")
    };

    public ContentFilter(Context context) {
        mContext = context;

        if (!SmartUtils.isAdBlockEnabled()) {
            mAdReplacement = null;
        }
    }

    public InputStream filterFirstScript(InputStream result) {
        Log.d(TAG, "Filtering first script...");
        return result;
    }

    public InputStream filterSecondScript(InputStream result) {
        Log.d(TAG, "Filtering second script...");

        if (mAdReplacement == null || mAdReplacement.length == 0) {
            return result;
        }

        return doReplacement(result, mAdReplacement);
    }

    public InputStream filterLastScript(InputStream result) {
        Log.d(TAG, "Filtering last script...");
        return result;
    }

    public InputStream filterStyles(InputStream result) {
        Log.d(TAG, "Filtering styles...");
        return result;
    }

    private InputStream doReplacement(InputStream inputStream, ReplacePair[] pairs) {
        String data = Helpers.toString(inputStream);

        for (ReplacePair pair : pairs) {
            data = data.replace(pair.first, pair.second);
        }

        return new ByteArrayInputStream(data.getBytes());
    }
}
