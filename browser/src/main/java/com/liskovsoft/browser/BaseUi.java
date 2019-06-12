package com.liskovsoft.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.*;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.liskovsoft.browser.xwalk.XWalkWebViewAdapter;

import java.util.List;

public abstract class BaseUi implements UI {
    private static final String TAG = BaseUi.class.getSimpleName();
    protected final Activity mActivity;
    protected final UiController mUiController;
    protected FrameLayout mFullscreenContainer;
    private CustomViewCallback mCustomViewCallback;
    private int mOriginalOrientation;

    protected final TabControl mTabControl;

    protected final FrameLayout mFixedTitlebarContainer;
    protected final FrameLayout mContentView;
    protected final FrameLayout mCustomViewContainer;
    protected final LinearLayout mErrorConsoleContainer;
    protected boolean mUseQuickControls;

    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
            new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

    protected Tab mActiveTab;
    private View mCustomView;
    private ActionBar mSupportActionBar;
    private Toast mStopToast;
    private boolean mActivityPaused;

    /**
     * Robust method when using fragments
     * @param browser fragment
     * @param controller controller
     */
    public BaseUi(Fragment browser, UiController controller) {
        mActivity = browser.getActivity();
        mUiController = controller;
        mTabControl = controller.getTabControl();
        FrameLayout frameLayout = (FrameLayout) browser.getView();
        LayoutInflater.from(mActivity).inflate(R.layout.custom_screen, frameLayout);
        mFixedTitlebarContainer = frameLayout.findViewById(R.id.fixed_titlebar_container);
        mContentView = frameLayout.findViewById(R.id.main_content);
        mCustomViewContainer = frameLayout.findViewById(R.id.fullscreen_custom_content);
        mErrorConsoleContainer = frameLayout.findViewById(R.id.error_console);

        // fix: attempt to invoke virtual method 'boolean com.liskovsoft.browser.BrowserSettings.useFullscreen()' on a null object reference
        BrowserSettings settings = BrowserSettings.getInstance();
        if (settings != null) {
            setFullscreen(settings.useFullscreen());
        }

        // dirty hack to make WebView receive focus
        initSupportActionBar();
    }

    /**
     * TODO: Don't know how, but it magically works!!! WebView now receives focus!
     */
    private void initSupportActionBar() {
        if (!(mActivity instanceof AppCompatActivity))
            return;

        mSupportActionBar = ((AppCompatActivity)mActivity).getSupportActionBar();
    }

    public void setFullscreen(boolean enabled) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (enabled) {
            winParams.flags |=  bits;
        } else {
            winParams.flags &= ~bits;
            if (mCustomView != null) {
                mCustomView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            } else {
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean needsRestoreAllTabs() {
        return true;
    }

    @Override
    public void bookmarkedStatusHasChanged(Tab tab) {
        // NOP
    }

    @Override
    public void onProgressChanged(Tab tab) {
        // TODO: not implemented
    }

    @Override
    public void detachTab(Tab tab) {
        removeTabFromContentView(tab);
    }

    @Override
    public void attachTab(Tab tab) {
        attachTabToContentView(tab);
    }

    @Override
    public void addTab(Tab tab) {
    }

    @Override
    public void updateTabs(List<Tab> tabs) {
    }

    /**
     * TODO: move method to another interface that ExtendedBaseUi will implements
     */
    public void editUrl(boolean clearInput, boolean forceIME) {}
    public void showAutoLogin(Tab tab){}
    public void hideAutoLogin(Tab tab){}

    @Override
    public void showWeb(boolean animate) {
        mUiController.hideCustomView();
    }

    /**
     * Dispatch keys to views inside content area (e.g. WebView)
     * @param code ignored, use event instead
     * @param event contains action and keycode
     * @return event is handled or not
     */
    public boolean dispatchKey(int code, KeyEvent event) {
        if (mActiveTab != null) {
            mContentView.requestFocus(); // issue #191: XWalk: no key reaction sometimes (temporal solution: use a mouse)
            return mContentView.dispatchKeyEvent(event);
        }
        return false;
    }

    Tab getActiveTab() {
        return mActiveTab;
    }

    @Override
    public boolean isCustomViewShowing() {
        return mCustomView != null;
    }

    @Override
    public void onVoiceResult(String mVoiceResult) {
        // TODO: not implemented
    }

    // key handling

    @Override
    public boolean onBackKey() {
        if (mCustomView != null) {
            mUiController.hideCustomView();
            return true;
        }
        return false;
    }

    @Override
    public boolean onMenuKey() {
        return false;
    }

    @Override
    public void onResume() {
        mActivityPaused = false;
        // check if we exited without setting active tab
        // b: 5188145
        final Tab ct = mTabControl.getCurrentTab();
        if (ct != null) {
            setActiveTab(ct);
        }
    }

    @Override
    public void setUseQuickControls(boolean useQuickControls) {
        mUseQuickControls = useQuickControls;
    }

    @Override
    public void showComboView(ComboViews startingView, Bundle extras) {
        Intent intent = new Intent(mActivity, ComboViewActivity.class);
        intent.putExtra(ComboViewActivity.EXTRA_INITIAL_VIEW, startingView.name());
        intent.putExtra(ComboViewActivity.EXTRA_COMBO_ARGS, extras);
        Tab t = getActiveTab();
        if (t != null) {
            intent.putExtra(ComboViewActivity.EXTRA_CURRENT_URL, t.getUrl());
        }
        mActivity.startActivityForResult(intent, Controller.COMBO_VIEW);
    }

    public boolean isLoading() {
        return mActiveTab != null ? mActiveTab.inPageLoad() : false;
    }

    // lifecycle

    @Override
    public void onPause() {
        if (isCustomViewShowing()) {
            onHideCustomView();
        }
        cancelStopToast();
        mActivityPaused = true;
    }

    private void cancelStopToast() {
        if (mStopToast != null) {
            mStopToast.cancel();
            mStopToast = null;
        }
    }

    protected WebView getWebView() {
        if (mActiveTab != null) {
            return mActiveTab.getWebView();
        } else {
            return null;
        }
    }

    @Override
    public boolean isWebShowing() {
        return mCustomView == null;
    }

    protected boolean isActivityPaused() {
        return mActivityPaused;
    }


    @Override
    public void onPageStopped(Tab tab) {
        cancelStopToast();
        if (tab.inForeground()) {
            mStopToast = Toast.makeText(mActivity, R.string.stopping, Toast.LENGTH_SHORT);
            mStopToast.show();
        }
    }

    @Override
    public void removeTab(Tab tab) {
        if (mActiveTab == tab) {
            removeTabFromContentView(tab);
            mActiveTab = null;
        }
    }

    /**
     * Remove the sub window from the content view.
     */
    @Override
    public void removeSubWindow(View subviewContainer) {
        mContentView.removeView(subviewContainer);
        mUiController.endActionMode();
    }


    @Override
    public void onHideCustomView() {
        getWebView().setVisibility(View.VISIBLE);
        if (mCustomView == null)
            return;
        setFullscreen(false);
        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        decor.removeView(mFullscreenContainer);
        mFullscreenContainer = null;
        mCustomView = null;
        mCustomViewCallback.onCustomViewHidden();
        // Show the content view.
        mActivity.setRequestedOrientation(mOriginalOrientation);
    }

    @Override
    public void setActiveTab(final Tab tab) {
        if (tab == null) return;

        mActiveTab = tab;
        BrowserWebView web = (BrowserWebView) mActiveTab.getWebView();
        attachTabToContentView(tab);
        tab.getTopWindow().requestFocus();
        onProgressChanged(tab);
    }

    protected void refreshWebView() {
        WebView web = getWebView();
        if (web != null) {
            web.invalidate();
        }
    }

    // NOTE: this method binds tab and webview
    protected void attachTabToContentView(Tab tab) {
        if ((tab == null) || (tab.getWebView() == null)) {
            return;
        }
        View container = tab.getViewContainer();
        View mainView  = tab.getWebView();
        mainView = findProperView(mainView);

        // Attach the WebView to the container and then attach the
        // container to the content view.
        FrameLayout wrapper =
                (FrameLayout) container.findViewById(R.id.webview_wrapper);
        ViewGroup parent = (ViewGroup) mainView.getParent();
        if (parent != wrapper) {
            if (parent != null) {
                parent.removeView(mainView);
            }
            wrapper.addView(mainView);

            // TODO: find a better place
            // NOTE: now you can add loading widget before WebView
            mUiController.onTabCreated(tab);
        }
        parent = (ViewGroup) container.getParent();
        if (parent != mContentView) {
            if (parent != null) {
                parent.removeView(container);
            }
            mContentView.addView(container, COVER_SCREEN_PARAMS);
        }
    }

    // TODO: replace with polymorphism
    private View findProperView(View mainView) {
        if (mainView instanceof XWalkWebViewAdapter) {
            mainView = ((XWalkWebViewAdapter) mainView).getXWalkView();
        }
        return mainView;
    }

    private void removeTabFromContentView(Tab tab) {
        // Remove the container that contains the main WebView.
        WebView mainView = tab.getWebView();
        View container = tab.getViewContainer();
        if (mainView == null) {
            return;
        }
        // Remove the container from the content and then remove the
        // WebView from the container. This will trigger a focus change
        // needed by WebView.
        FrameLayout wrapper =
                (FrameLayout) container.findViewById(R.id.webview_wrapper);
        wrapper.removeView(mainView);
        mContentView.removeView(container);

        // TODO: Tab could be detached or removed. Is this the same thing?
        tab.setListener(null);
    }

    @Override
    public void onSetWebView(Tab tab, WebView webView) {
        View container = tab.getViewContainer();
        if (container == null) {
            // The tab consists of a container view, which contains the main
            // WebView, as well as any other UI elements associated with the tab.
            container = mActivity.getLayoutInflater().inflate(R.layout.tab,
                    mContentView, false);
            tab.setViewContainer(container);
        }
        if (tab.getWebView() != webView) {
            // Just remove the old one.
            FrameLayout wrapper =
                    (FrameLayout) container.findViewById(R.id.webview_wrapper);
            wrapper.removeView(tab.getWebView());
        }
    }


    public void addFixedTitleBar(View view) {
        mFixedTitlebarContainer.addView(view);
    }

    public void setContentViewMarginTop(int margin) {
        LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        if (params.topMargin != margin) {
            params.topMargin = margin;
            mContentView.setLayoutParams(params);
        }
    }
}
