package com.liskovsoft.browser;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import android.widget.FrameLayout;
import com.liskovsoft.browser.UI.ComboViews;

import java.util.List;


/**
 * UI aspect of the controller
 */
public interface UiController {

    UI getUi();

    WebView getCurrentWebView();

    WebView getCurrentTopWebView();

    Tab getCurrentTab();

    TabControl getTabControl();

    List<Tab> getTabs();

    Tab openTabToHomePage();

    Tab openIncognitoTab();

    Tab openTab(String url, boolean incognito, boolean setActive,
                boolean useCurrent);

    void setActiveTab(Tab tab);

    boolean switchToTab(Tab tab);

    void closeCurrentTab();

    void closeTab(Tab tab);

    void closeOtherTabs();

    void stopLoading();

    Intent createBookmarkCurrentPageIntent(boolean canBeAnEdit);

    void bookmarksOrHistoryPicker(ComboViews startView);

    void bookmarkCurrentPage();

    void editUrl();

    void handleNewIntent(Intent intent);

    boolean shouldShowErrorConsole();

    void hideCustomView();

    void attachSubWindow(Tab tab);

    void removeSubWindow(Tab tab);

    boolean isInCustomActionMode();

    void endActionMode();

    void shareCurrentPage();

    void updateMenuState(Tab tab, Menu menu);

    boolean onOptionsItemSelected(MenuItem item);

    void loadUrl(Tab tab, String url);

    void setBlockEvents(boolean block);

    Activity getActivity();

    void showPageInfo();

    void openPreferences();

    void findOnPage();

    void toggleUserAgent();

    BrowserSettings getSettings();

    boolean supportsVoice();

    void startVoiceRecognizer();

    // My custom methods

    void onTabCreated(Tab tab);

    void showSoftKeyboard();

    // End my custom methods
}
