package com.liskovsoft.browser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.liskovsoft.browser.view.PageProgressView;

public class TitleBar extends RelativeLayout {
    private final UiController mUiController;
    private final TitleBarBaseUi mBaseUi;
    private NavigationBarBase mNavBar;
    private PageProgressView mProgress;
    private boolean mShowing;
    private boolean mUseQuickControls;
    private boolean mIsFixedTitleBar;
    private boolean mSkipTitleBarAnimations;
    private FrameLayout mContentView;
    private AutologinBar mAutoLogin;

    public TitleBar(Context context, UiController controller, TitleBarBaseUi ui, FrameLayout contentView) {
        super(context, null);
        mUiController = controller;
        mBaseUi = ui;
        mContentView = contentView;
        initLayout(context);
        setFixedTitleBar();
    }

    private void setFixedTitleBar() {
        // If getParent() returns null, we are initializing
        ViewGroup parent = (ViewGroup)getParent();
        if (mIsFixedTitleBar && parent != null) return;
        mIsFixedTitleBar = true;
        setSkipTitleBarAnimations(true);
        show();
        setSkipTitleBarAnimations(false);
        if (parent != null) {
            parent.removeView(this);
        }
        if (mIsFixedTitleBar) {
            mBaseUi.addFixedTitleBar(this);
        } else {
            mContentView.addView(this, makeLayoutParams());
            mBaseUi.setContentViewMarginTop(0);
        }
    }

    private ViewGroup.LayoutParams makeLayoutParams() {
        return new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    public void updateAutoLogin(Tab tab, boolean animate) {
        if (mAutoLogin == null) {
            if  (tab.getDeviceAccountLogin() == null) {
                return;
            }
            inflateAutoLoginBar();
        }
        mAutoLogin.updateAutoLogin(tab, animate);
    }

    public void showAutoLogin(boolean animate) {
        if (mUseQuickControls) {
            mBaseUi.showTitleBar();
        }
        if (mAutoLogin == null) {
            inflateAutoLoginBar();
        }
        mAutoLogin.setVisibility(View.VISIBLE);
        if (animate) {
            mAutoLogin.startAnimation(AnimationUtils.loadAnimation(
                    getContext(), R.anim.autologin_enter));
        }
    }

    public void hideAutoLogin(boolean animate) {
        if (mUseQuickControls) {
            mBaseUi.hideTitleBar();
            mAutoLogin.setVisibility(View.GONE);
            mBaseUi.refreshWebView();
        } else {
            if (animate) {
                Animation anim = AnimationUtils.loadAnimation(getContext(),
                        R.anim.autologin_exit);
                anim.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation a) {
                        mAutoLogin.setVisibility(View.GONE);
                        mBaseUi.refreshWebView();
                    }

                    @Override
                    public void onAnimationStart(Animation a) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation a) {
                    }
                });
                mAutoLogin.startAnimation(anim);
            } else if (mAutoLogin.getAnimation() == null) {
                mAutoLogin.setVisibility(View.GONE);
                mBaseUi.refreshWebView();
            }
        }
    }

    private void setSkipTitleBarAnimations(boolean b) {
        // TODO: not implemented
    }

    private void initLayout(Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        factory.inflate(R.layout.title_bar, this);
        mProgress = (PageProgressView) findViewById(R.id.progress);
        mNavBar = (NavigationBarBase) findViewById(R.id.taburlbar);
        mNavBar.setTitleBar(this);
    }

    private void inflateAutoLoginBar() {
        if (mAutoLogin != null) {
            return;
        }

        ViewStub stub = (ViewStub) findViewById(R.id.autologin_stub);
        mAutoLogin = (AutologinBar) stub.inflate();
        mAutoLogin.setTitleBar(this);
    }

    public NavigationBarBase getNavigationBar() {
        return mNavBar;
    }

    public void setProgress(int progress) {
        // TODO: not implemented
    }

    public void setUseQuickControls(boolean useQuickControls) {
        // TODO: not implemented
    }


    void setShowProgressOnly(boolean progress) {
        if (progress && !wantsToBeVisible()) {
            mNavBar.setVisibility(View.GONE);
        } else {
            mNavBar.setVisibility(View.VISIBLE);
        }
    }

    public boolean useQuickControls() {
        return mUseQuickControls;
    }

    public PageProgressView getProgressView() {
        return mProgress;
    }

    public boolean isShowing() {
        return mShowing;
    }

    public boolean wantsToBeVisible() {
        return inAutoLogin();
    }

    private boolean inAutoLogin() {
        return mAutoLogin != null && mAutoLogin.getVisibility() == View.VISIBLE;
    }

    public boolean isEditingUrl() {
        return mNavBar.isEditingUrl();
    }


    public void onResume() {
        // TODO: not implemented
    }

    public TitleBarBaseUi getUi() {
        return mBaseUi;
    }

    public UiController getUiController() {
        return mUiController;
    }

    void show() {
        cancelTitleBarAnimation(false);
        if (mUseQuickControls || mSkipTitleBarAnimations) {
            this.setVisibility(View.VISIBLE);
            this.setTranslationY(0);
        } else {
            // TODO: not implemented
        }
        mShowing = true;
    }

    private void cancelTitleBarAnimation(boolean b) {
    }

    void hide() {
        if (mUseQuickControls) {
            this.setVisibility(View.GONE);
        } else {
            if (mIsFixedTitleBar) return;
            if (!mSkipTitleBarAnimations) {
                // TODO: not implemented
            } else {
                // TODO: not implemented
            }
        }
        mShowing = false;
    }
}
