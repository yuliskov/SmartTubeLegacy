package com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter.ReplacingInputStream.ReplacePair;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContentFilter {
    private static final String TAG = ContentFilter.class.getSimpleName();
    private final Context mContext;
    private List<ReplacePair> mSecondReplacement = new ArrayList<>();

    public ContentFilter(Context context) {
        mContext = context;

        if (SmartUtils.isAdBlockEnabled()) {
            mSecondReplacement.add(new ReplacePair("b.masthead&&!b.masthead.isMuted()", "false"));
            //mSecondReplacement.add(new ReplacePair("airstream/masthead/masthead.html", "empty.html"));
            //mSecondReplacement.add(new ReplacePair("tvMastheadRenderer", "tvMastheadRendererOld"));
        }

        if (CommonApplication.getPreferences().getEnableAnimatedPreviews()) {
            mSecondReplacement.add(new ReplacePair("animatedWebpSupport:I", "animatedWebpSupport:true"));
            //mSecondReplacement.add(new ReplacePair("animatedWebpSupport:!1", "animatedWebpSupport:true"));
            //mSecondReplacement.add(new ReplacePair("animatedWebpSupport:c.animatedWebpSupport", "animatedWebpSupport:true"));
        }

        //mSecondReplacement.add(new ReplacePair("\"high-contrast\":\"\"", "\"high-contrast\":\"high-contrast\""));
    }

    public InputStream filterFirstScript(InputStream result) {
        Log.d(TAG, "Filtering first script...");
        return result;
    }

    public InputStream filterSecondScript(InputStream result) {
        Log.d(TAG, "Filtering second script...");

        if (mSecondReplacement.size() == 0) {
            return result;
        }

        return doReplacement(result, mSecondReplacement);
        //return new ReplacingInputStream(result, mSecondReplacement);
    }

    public InputStream filterLastScript(InputStream result) {
        Log.d(TAG, "Filtering last script...");
        return result;
    }

    public InputStream filterStyles(InputStream result) {
        Log.d(TAG, "Filtering styles...");
        return result;
    }

    private InputStream doReplacement(InputStream inputStream, List<ReplacePair> pairs) {
        String data = Helpers.toString(inputStream);

        for (ReplacePair pair : pairs) {
            data = data.replace(pair.first, pair.second);
        }

        return new ByteArrayInputStream(data.getBytes());
    }
}
