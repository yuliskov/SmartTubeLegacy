package com.liskovsoft.browser.xwalk;

import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;

public class WebBackForwardListAdapter extends WebBackForwardList {
    private final boolean mSuccess;

    public WebBackForwardListAdapter(boolean success) {
        mSuccess = success;
    }

    @Override
    public WebHistoryItem getCurrentItem() {
        return null;
    }

    @Override
    public int getCurrentIndex() {
        return 0;
    }

    @Override
    public WebHistoryItem getItemAtIndex(int index) {
        return null;
    }

    @Override
    public int getSize() {
        if (mSuccess)
            return 1;
        return 0;
    }

    @Override
    protected WebBackForwardList clone() {
        return null;
    }
}
