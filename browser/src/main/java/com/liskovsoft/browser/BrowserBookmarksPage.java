package com.liskovsoft.browser;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class BrowserBookmarksPage extends Fragment implements View.OnCreateContextMenuListener,
        LoaderManager.LoaderCallbacks<Cursor>, BreadCrumbView.Controller, OnChildClickListener {

    public static class ExtraDragState {
        public int childPosition;
        public int groupPosition;
    }

    static final String LOGTAG = "browser";

    static final int LOADER_ACCOUNTS = 1;
    static final int LOADER_BOOKMARKS = 100;

    static final String EXTRA_DISABLE_WINDOW = "disable_new_window";
    static final String PREF_GROUP_STATE = "bbp_group_state";

    static final String ACCOUNT_TYPE = "account_type";
    static final String ACCOUNT_NAME = "account_name";

    @Override
    public void onTop(BreadCrumbView view, int level, Object data) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        return false;
    }
}
