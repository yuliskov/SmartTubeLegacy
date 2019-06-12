package com.liskovsoft.smartyoutubetv.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.R;

public abstract class BootstrapButtonBase extends LinearLayout {
    private TextView mTipTextView;
    private String mTipText;
    private String mOldTipText;

    public BootstrapButtonBase(Context context) {
        super(context);
    }

    public BootstrapButtonBase(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BootstrapButtonBase,
                0, 0);

        try {
            mTipText = a.getString(R.styleable.BootstrapButtonBase_tipText);
        } finally {
            a.recycle();
        }
    }

    public BootstrapButtonBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BootstrapButtonBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mTipTextView = getRootView().findViewById(R.id.tip_text);
    }

    protected void makeFocused() {
        showTipText();
    }

    protected void makeUnfocused() {
        hideTipText();
    }

    private void showTipText() {
        if (mTipTextView == null || mTipText == null) {
            return;
        }

        mOldTipText = mTipTextView.getText().toString();
        mTipTextView.setText(mTipText);
    }

    private void hideTipText() {
        if (mTipTextView == null || mOldTipText == null) {
            return;
        }

        mTipTextView.setText(mOldTipText);
    }

    protected abstract View getWrapper();

    public void enable() {
        View view = getWrapper();
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }

    public void disable() {
        View view = getWrapper();
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        makeUnfocused();
    }
}
