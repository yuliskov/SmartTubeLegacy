package com.liskovsoft.browser;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import java.util.List;

public interface UI {
    boolean needsRestoreAllTabs();
    void onProgressChanged(Tab tab);
    void addTab(Tab tab);
    void detachTab(Tab tab);
    void attachTab(Tab tab);
    void onSetWebView(Tab tab, WebView view);
    void showComboView(ComboViews startingView, Bundle extras);
    void onPause();
    boolean isWebShowing();
    void showAutoLogin(Tab tab);
    void hideAutoLogin(Tab tab);
    void onPageStopped(Tab tab);
    void removeTab(Tab tab);
    void removeSubWindow(View subviewContainer);
    void onHideCustomView();
    void setActiveTab(Tab tab);
    boolean onBackKey();
    boolean onMenuKey();
    void onResume();
    void setUseQuickControls(boolean useQuickControls);
    boolean isCustomViewShowing();
    void onVoiceResult(String mVoiceResult);
    void editUrl(boolean clearInput, boolean forceIME);
    void updateTabs(List<Tab> tabs);
    void showWeb(boolean animate);
    boolean dispatchKey(int code, KeyEvent event);
    void bookmarkedStatusHasChanged(Tab tab);

    enum ComboViews {
        History,
        Bookmarks,
        Snapshots,
    }
}
