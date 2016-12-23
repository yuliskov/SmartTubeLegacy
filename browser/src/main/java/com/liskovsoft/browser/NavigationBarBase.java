package com.liskovsoft.browser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NavigationBarBase extends LinearLayout implements View.OnClickListener,
        UrlInputListener, OnFocusChangeListener, TextWatcher {
    private TitleBar mTitleBar;
    protected UrlInputView mUrlInput;
    private ImageView mLockIcon;
    private ImageView mFavicon;
    private TitleBarBaseUi mBaseUi;
    private UiController mUiController;

    public NavigationBarBase(Context context) {
        super(context);
        // TODO: not implemented
    }

    public NavigationBarBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationBarBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) { }

    @Override
    public void onClick(View view) {

    }

    public void setTitleBar(TitleBar titleBar) {
        mTitleBar = titleBar;
        mBaseUi = mTitleBar.getUi();
        mUiController = mTitleBar.getUiController();
        mUrlInput.setController(mUiController);
    }

    public boolean isEditingUrl() {
        return mUrlInput.hasFocus();
    }

    public boolean isMenuShowing() {
        return false;
    }

    protected void setFocusState(boolean focus) {
    }

    void setDisplayTitle(String title) {
        if (!isEditingUrl()) {
            if (!title.equals(mUrlInput.getText().toString())) {
                // TODO: decrease api level
                mUrlInput.setText(title, false);
            }
        }
    }

    /**
     * called from the Ui when the user wants to edit
     * @param clearInput clear the input field
     */
    void startEditingUrl(boolean clearInput, boolean forceIME) {
        // editing takes preference of progress
        setVisibility(View.VISIBLE);
        if (mTitleBar.useQuickControls()) {
            mTitleBar.getProgressView().setVisibility(View.GONE);
        }
        if (!mUrlInput.hasFocus()) {
            mUrlInput.requestFocus();
        }
        if (clearInput) {
            mUrlInput.setText("");
        }
        if (forceIME) {
            mUrlInput.showIME();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLockIcon = (ImageView) findViewById(R.id.lock);
        mFavicon = (ImageView) findViewById(R.id.favicon);
        mUrlInput = (UrlInputView) findViewById(R.id.url);
        mUrlInput.setUrlInputListener(this);
        mUrlInput.setOnFocusChangeListener(this);
        mUrlInput.setSelectAllOnFocus(true);
        mUrlInput.addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        // if losing focus and not in touch mode, leave as is
        if (hasFocus || view.isInTouchMode() || mUrlInput.needsUpdate()) {
            setFocusState(hasFocus);
        }
        if (hasFocus) {
            mBaseUi.showTitleBar();
        } else if (!mUrlInput.needsUpdate()) {
            mUrlInput.dismissDropDown();
            mUrlInput.hideIME();
            if (mUrlInput.getText().length() == 0) {
                Tab currentTab = mUiController.getTabControl().getCurrentTab();
                if (currentTab != null) {
                    setDisplayTitle(currentTab.getUrl());
                }
            }
            mBaseUi.suggestHideTitleBar();
        }
        mUrlInput.clearNeedsUpdate();
    }

    public void setCurrentUrlIsBookmark(boolean isBookmark) {
        throw new UnsupportedOperationException("setCurrentUrlIsBookmark is not implemented");
    }
}
