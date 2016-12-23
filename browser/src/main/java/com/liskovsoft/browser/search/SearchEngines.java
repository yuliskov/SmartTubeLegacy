package com.liskovsoft.browser.search;

import com.liskovsoft.browser.R;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SearchEngines {

    private static final String TAG = "SearchEngines";

    public static SearchEngine getDefaultSearchEngine(Context context) {
        return DefaultSearchEngine.create(context);
    }

    public static List<SearchEngineInfo> getSearchEngineInfos(Context context) {
        ArrayList<SearchEngineInfo> searchEngineInfos = new ArrayList<SearchEngineInfo>();
        Resources res = context.getResources();
        String[] searchEngines = res.getStringArray(R.array.search_engines);
        for (int i = 0; i < searchEngines.length; i++) {
            String name = searchEngines[i];
            SearchEngineInfo info = new SearchEngineInfo(context, name);
            searchEngineInfos.add(info);
        }
        return searchEngineInfos;
    }

    public static SearchEngine get(Context context, String name) {
        // TODO: cache
        SearchEngine defaultSearchEngine = getDefaultSearchEngine(context);
        if (TextUtils.isEmpty(name)
                || (defaultSearchEngine != null && name.equals(defaultSearchEngine.getName()))) {
            return defaultSearchEngine;
        }
        SearchEngineInfo searchEngineInfo = getSearchEngineInfo(context, name);
        if (searchEngineInfo == null) return defaultSearchEngine;
        return new OpenSearchSearchEngine(context, searchEngineInfo);
    }

    public static SearchEngineInfo getSearchEngineInfo(Context context, String name) {
        try {
            return new SearchEngineInfo(context, name);
        } catch (IllegalArgumentException exception) {
            Log.e(TAG, "Cannot load search engine " + name, exception);
            return null;
        }
    }

}
