package com.liskovsoft.browser;

public interface CombinedBookmarksCallbacks {
    void openUrl(String url);
    void openInNewTab(String... urls);
    void openSnapshot(long id);
    void close();
}
