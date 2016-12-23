package com.liskovsoft.browser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class SuggestionsAdapter extends BaseAdapter implements Filterable, View.OnClickListener {
    private final Context mContext;
    private final BrowserSettings mSettings;
    private final CompletionListener mListener;
    private final int mLinesPortrait;
    private final int mLinesLandscape;

    public SuggestionsAdapter(Context ctx, CompletionListener listener) {
        mContext = ctx;
        mSettings = BrowserSettings.getInstance();
        mListener = listener;
        mLinesPortrait = mContext.getResources().
                getInteger(R.integer.max_suggest_lines_portrait);
        mLinesLandscape = mContext.getResources().
                getInteger(R.integer.max_suggest_lines_landscape);

        // TODO: uncomment next time
        //mFilter = new SuggestFilter();
        //addSource(new CombinedCursor());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    interface CompletionListener {

        public void onSearch(String txt);

        public void onSelect(String txt, int type, String extraData);

    }
}
