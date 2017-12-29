package com.liskovsoft.browser.helpers;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * TODO: copied from somewhere in the web
 */

/**
 * Utilities for search implementations.
 *
 * @see android.app.SearchManager
 */
public class Search {
    /**
     * Key for the source identifier set by the application that launched a search intent.
     * The identifier is search-source specific string. It can be used
     * by the search provider to keep statistics of where searches are started from.
     *
     * The source identifier is stored in the {@link android.app.SearchManager#APP_DATA}
     * Bundle in {@link android.content.Intent#ACTION_SEARCH} and
     * {@link android.content.Intent#ACTION_WEB_SEARCH} intents.
     */
    public final static String SOURCE = "source";
    /**
     * Column name for suggestions cursor. <i>Optional.</i> This column may be
     * used to specify the time in {@link System#currentTimeMillis
     * System.currentTimeMillis()} (wall time in UTC) when an item was last
     * accessed within the results-providing application. If set, this may be
     * used to show more-recently-used items first.
     *
     * See {@code SearchManager.SUGGEST_COLUMN_LAST_ACCESS_HINT} in ICS.
     */
    public final static String SUGGEST_COLUMN_LAST_ACCESS_HINT = "suggest_last_access_hint";

    private Search() {
    }   // don't instantiate

    /**
     * Gets a cursor with search suggestions.
     *
     * @param searchable Information about how to get the suggestions.
     * @param query      The search text entered (so far).
     * @return a cursor with suggestions, or <code>null</null> the suggestion query failed.
     */
    public static Cursor getSuggestions(Context context, SearchableInfo searchable, String query) {
        return getSuggestions(context, searchable, query, -1);
    }

    /**
     * Gets a cursor with search suggestions.
     *
     * @param searchable Information about how to get the suggestions.
     * @param query      The search text entered (so far).
     * @param limit      The query limit to pass to the suggestion provider. This is advisory,
     *                   the returned cursor may contain more rows. Pass {@code -1} for no limit.
     * @return a cursor with suggestions, or <code>null</null> the suggestion query failed.
     */
    public static Cursor getSuggestions(Context context, SearchableInfo searchable, String query, int limit) {
        if (searchable == null) {
            return null;
        }
        String authority = searchable.getSuggestAuthority();
        if (authority == null) {
            return null;
        }
        Uri.Builder uriBuilder = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(authority).query("")  // TODO: Remove,
                // workaround for a bug in Uri.writeToParcel()
                .fragment("");  // TODO: Remove, workaround for a bug in Uri.writeToParcel()
        // if content path provided, insert it now
        final String contentPath = searchable.getSuggestPath();
        if (contentPath != null) {
            uriBuilder.appendEncodedPath(contentPath);
        }
        // append standard suggestion query path
        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY);
        // get the query selection, may be null
        String selection = searchable.getSuggestSelection();
        // inject query, either as selection args or inline
        String[] selArgs = null;
        if (selection != null) {
            selArgs = new String[]{query};
        } else {                    // no selection, use REST pattern
            uriBuilder.appendPath(query);
        }
        if (limit > 0) {
            uriBuilder.appendQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT, String.valueOf(limit));
        }
        Uri uri = uriBuilder.build();
        // finally, make the query
        return context.getContentResolver().query(uri, null, selection, selArgs, null);
    }
}
