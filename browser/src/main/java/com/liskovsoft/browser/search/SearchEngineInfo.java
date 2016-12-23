package com.liskovsoft.browser.search;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.text.TextUtils;
import android.util.Log;

import com.liskovsoft.browser.R;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Locale;

/**
 * Loads and holds data for a given web search engine.
 */
public class SearchEngineInfo {

    private static String TAG = "SearchEngineInfo";

    // The fields of a search engine data array, defined in the same order as they appear in the
    // all_search_engines.xml file.
    // If you are adding/removing to this list, remember to update NUM_FIELDS below.
    private static final int FIELD_LABEL = 0;
    private static final int FIELD_KEYWORD = 1;
    private static final int FIELD_FAVICON_URI = 2;
    private static final int FIELD_SEARCH_URI = 3;
    private static final int FIELD_ENCODING = 4;
    private static final int FIELD_SUGGEST_URI = 5;
    private static final int NUM_FIELDS = 6;

    // The OpenSearch URI template parameters that we support.
    private static final String PARAMETER_LANGUAGE = "{language}";
    private static final String PARAMETER_SEARCH_TERMS = "{searchTerms}";
    private static final String PARAMETER_INPUT_ENCODING = "{inputEncoding}";

    private final String mName;

    // The array of strings defining this search engine. The array values are in the same order as
    // the above enumeration definition.
    private final String[] mSearchEngineData;

    /**
     * @throws IllegalArgumentException If the name does not refer to a valid search engine
     */
    public SearchEngineInfo(Context context, String name) throws IllegalArgumentException {
        mName = name;
        Resources res = context.getResources();

        String packageName = R.class.getPackage().getName();
        int id_data = res.getIdentifier(name, "array", packageName);
        if (id_data == 0) {
            throw new IllegalArgumentException("No resources found for " + name);
        }
        mSearchEngineData = res.getStringArray(id_data);

        if (mSearchEngineData == null) {
            throw new IllegalArgumentException("No data found for " + name);
        }
        if (mSearchEngineData.length != NUM_FIELDS) {
                throw new IllegalArgumentException(
                        name + " has invalid number of fields - " + mSearchEngineData.length);
        }
        if (TextUtils.isEmpty(mSearchEngineData[FIELD_SEARCH_URI])) {
            throw new IllegalArgumentException(name + " has an empty search URI");
        }

        // Add the current language/country information to the URIs.
        Locale locale = context.getResources().getConfiguration().locale;
        StringBuilder language = new StringBuilder(locale.getLanguage());
        if (!TextUtils.isEmpty(locale.getCountry())) {
            language.append('-');
            language.append(locale.getCountry());
        }

        String language_str = language.toString();
        mSearchEngineData[FIELD_SEARCH_URI] =
                mSearchEngineData[FIELD_SEARCH_URI].replace(PARAMETER_LANGUAGE, language_str);
        mSearchEngineData[FIELD_SUGGEST_URI] =
                mSearchEngineData[FIELD_SUGGEST_URI].replace(PARAMETER_LANGUAGE, language_str);

        // Default to UTF-8 if not specified.
        String enc = mSearchEngineData[FIELD_ENCODING];
        if (TextUtils.isEmpty(enc)) {
            enc = "UTF-8";
            mSearchEngineData[FIELD_ENCODING] = enc;
        }

        // Add the input encoding method to the URI.
        mSearchEngineData[FIELD_SEARCH_URI] =
                mSearchEngineData[FIELD_SEARCH_URI].replace(PARAMETER_INPUT_ENCODING, enc);
        mSearchEngineData[FIELD_SUGGEST_URI] =
                mSearchEngineData[FIELD_SUGGEST_URI].replace(PARAMETER_INPUT_ENCODING, enc);
    }

    public String getName() {
        return mName;
    }

    public String getLabel() {
        return mSearchEngineData[FIELD_LABEL];
    }

    /**
     * Returns the URI for launching a web search with the given query (or null if there was no
     * data available for this search engine).
     */
    public String getSearchUriForQuery(String query) {
        return getFormattedUri(searchUri(), query);
    }

    /**
     * Returns the URI for retrieving web search suggestions for the given query (or null if there
     * was no data available for this search engine).
     */
    public String getSuggestUriForQuery(String query) {
        return getFormattedUri(suggestUri(), query);
    }

    public boolean supportsSuggestions() {
        return !TextUtils.isEmpty(suggestUri());
    }

    public String faviconUri() {
        return mSearchEngineData[FIELD_FAVICON_URI];
    }

    private String suggestUri() {
        return mSearchEngineData[FIELD_SUGGEST_URI];
    }

    private String searchUri() {
        return mSearchEngineData[FIELD_SEARCH_URI];
    }

    /**
     * Formats a launchable uri out of the template uri by replacing the template parameters with
     * actual values.
     */
    private String getFormattedUri(String templateUri, String query) {
        if (TextUtils.isEmpty(templateUri)) {
            return null;
        }

        // Encode the query terms in the requested encoding (and fallback to UTF-8 if not).
        String enc = mSearchEngineData[FIELD_ENCODING];
        try {
            return templateUri.replace(PARAMETER_SEARCH_TERMS, URLEncoder.encode(query, enc));
        } catch (java.io.UnsupportedEncodingException e) {
            Log.e(TAG, "Exception occured when encoding query " + query + " to " + enc);
            return null;
        }
    }

    @Override
    public String toString() {
        return "SearchEngineInfo{" + Arrays.toString(mSearchEngineData) + "}";
    }

}
