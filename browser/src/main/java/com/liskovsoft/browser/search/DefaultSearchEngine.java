package com.liskovsoft.browser.search;

import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.text.TextUtils;
import android.util.Log;

public class DefaultSearchEngine implements SearchEngine {

    private static final String TAG = "DefaultSearchEngine";

    private final SearchableInfo mSearchable;

    private final CharSequence mLabel;

    private DefaultSearchEngine(Context context, SearchableInfo searchable) {
        mSearchable = searchable;
        mLabel = loadLabel(context, mSearchable.getSearchActivity());
    }

    public static DefaultSearchEngine create(Context context) {
        SearchManager searchManager =
                (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        ComponentName name = null;
        // TODO: method not found
        //ComponentName name = searchManager.getWebSearchActivity();
        if (true)
            throw new IllegalStateException("Method getWebSearchActivity not found");
        if (name == null) return null;
        SearchableInfo searchable = searchManager.getSearchableInfo(name);
        if (searchable == null) return null;
        return new DefaultSearchEngine(context, searchable);
    }

    private CharSequence loadLabel(Context context, ComponentName activityName) {
        PackageManager pm = context.getPackageManager();
        try {
            ActivityInfo ai = pm.getActivityInfo(activityName, 0);
            return ai.loadLabel(pm);
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e(TAG, "Web search activity not found: " + activityName);
            return null;
        }
    }

    public String getName() {
        String packageName = mSearchable.getSearchActivity().getPackageName();
        // Use "google" as name to avoid showing Google twice (app + OpenSearch)
        if ("com.google.android.googlequicksearchbox".equals(packageName)) {
            return SearchEngine.GOOGLE;
        } else if ("com.android.quicksearchbox".equals(packageName)) {
            return SearchEngine.GOOGLE;
        } else {
            return packageName;
        }
    }

    public CharSequence getLabel() {
        return mLabel;
    }

    public void startSearch(Context context, String query, Bundle appData, String extraData) {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.setComponent(mSearchable.getSearchActivity());
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(SearchManager.QUERY, query);
            if (appData != null) {
                intent.putExtra(SearchManager.APP_DATA, appData);
            }
            if (extraData != null) {
                intent.putExtra(SearchManager.EXTRA_DATA_KEY, extraData);
            }
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            viewIntent.setPackage(context.getPackageName());
            PendingIntent pending = PendingIntent.getActivity(context, 0, viewIntent,
                    PendingIntent.FLAG_ONE_SHOT);
            intent.putExtra(SearchManager.EXTRA_WEB_SEARCH_PENDINGINTENT, pending);
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Log.e(TAG, "Web search activity not found: " + mSearchable.getSearchActivity());
        }
    }

    public Cursor getSuggestions(Context context, String query) {
        SearchManager searchManager =
                (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        // TODO: method not found
        //return searchManager.getSuggestions(mSearchable, query);
        if (true)
            throw new IllegalStateException("Method getSuggestions not found");
        return null;
    }

    public boolean supportsSuggestions() {
        return !TextUtils.isEmpty(mSearchable.getSuggestAuthority());
    }

    public void close() {
    }

    @Override
    public String toString() {
        return "ActivitySearchEngine{" + mSearchable + "}";
    }

    @Override
    public boolean wantsEmptyQuery() {
        return false;
    }

}
