package com.liskovsoft.browser;

import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;

public abstract class TitleBarBaseUi extends BaseUi {
    protected final TitleBar mTitleBar;
    protected final NavigationBarBase mNavigationBar;

    private static final int MSG_HIDE_TITLEBAR = 1;
    public static final int HIDE_TITLEBAR_DELAY = 1500; // in ms

    protected Handler mHandler = new TileHandler();

    private class TileHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_HIDE_TITLEBAR) {
                suggestHideTitleBar();
            }
            TitleBarBaseUi.this.handleMessage(msg);
        }
    }

    public TitleBarBaseUi(Fragment browser, UiController controller) {
        super(browser, controller);

        mTitleBar = new TitleBar(mActivity, mUiController, this, mContentView);
        mTitleBar.setProgress(100);
        mNavigationBar = mTitleBar.getNavigationBar();
    }

    protected void handleMessage(Message msg) {}

    @Override
    public void onVoiceResult(String mVoiceResult) {
        super.onVoiceResult(mVoiceResult);
        mTitleBar.onResume();
    }

    /**
     * Suggest to the UI that the title bar can be hidden. The UI will then
     * decide whether or not to hide based off a number of factors, such
     * as if the user is editing the URL bar or if the page is loading
     */
    public void suggestHideTitleBar() {
        if (!isLoading() && !isEditingUrl() && !mTitleBar.wantsToBeVisible()
                && !mNavigationBar.isMenuShowing()) {
            hideTitleBar();
        }
    }

    public boolean isEditingUrl() {
        return mTitleBar.isEditingUrl();
    }

    @Override
    public void setUseQuickControls(boolean useQuickControls) {
        super.setUseQuickControls(useQuickControls);
        mTitleBar.setUseQuickControls(mUseQuickControls);
    }

    @Override
    public void setActiveTab(Tab tab) {
        super.setActiveTab(tab);
        mTitleBar.bringToFront();
    }

    protected void showTitleBar() {
        mHandler.removeMessages(MSG_HIDE_TITLEBAR);
        if (canShowTitleBar()) {
            mTitleBar.show();
        }
    }

    protected void hideTitleBar() {
        if (mTitleBar.isShowing()) {
            mTitleBar.hide();
        }
    }

    protected boolean isTitleBarShowing() {
        return mTitleBar.isShowing();
    }

    boolean canShowTitleBar() {
        return !isTitleBarShowing()
                && !isActivityPaused()
                && (getActiveTab() != null)
                && (getWebView() != null)
                && !mUiController.isInCustomActionMode();
    }

    @Override
    public void bookmarkedStatusHasChanged(Tab tab) {
        if (tab.inForeground()) {
            boolean isBookmark = tab.isBookmarkedSite();
            mNavigationBar.setCurrentUrlIsBookmark(isBookmark);
        }
    }


    @Override
    public void showAutoLogin(Tab tab) {
        updateAutoLogin(tab, true);
    }

    @Override
    public void hideAutoLogin(Tab tab) {
        updateAutoLogin(tab, true);
    }

    protected void updateNavigationState(Tab tab) {
    }

    protected void updateAutoLogin(Tab tab, boolean animate) {
        mTitleBar.updateAutoLogin(tab, animate);
    }

    public void editUrl(boolean clearInput, boolean forceIME) {
        if (mUiController.isInCustomActionMode()) {
            mUiController.endActionMode();
        }
        showTitleBar();
        if ((getActiveTab() != null) && !getActiveTab().isSnapshot()) {
            mNavigationBar.startEditingUrl(clearInput, forceIME);
        }
    }
}
