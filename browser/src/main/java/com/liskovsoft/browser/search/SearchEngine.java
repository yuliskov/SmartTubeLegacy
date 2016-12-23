package com.liskovsoft.browser.search;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

/**
 * Interface for search engines.
 */
public interface SearchEngine {

    // Used if the search engine is Google
    static final String GOOGLE = "google";

    /**
     * Gets the unique name of this search engine.
     */
    public String getName();

    /**
     * Gets the human-readable name of this search engine.
     */
    public CharSequence getLabel();

    /**
     * Starts a search.
     */
    public void startSearch(Context context, String query, Bundle appData, String extraData);

    /**
     * Gets search suggestions.
     */
    public Cursor getSuggestions(Context context, String query);

    /**
     * Checks whether this search engine supports search suggestions.
     */
    public boolean supportsSuggestions();

    /**
     * Closes this search engine.
     */
    public void close();

    /**
     * Checks whether this search engine should be sent zero char query.
     */
    public boolean wantsEmptyQuery();
}
