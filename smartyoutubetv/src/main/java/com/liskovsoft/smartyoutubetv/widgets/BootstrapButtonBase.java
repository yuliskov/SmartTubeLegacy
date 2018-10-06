package com.liskovsoft.smartyoutubetv.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.R;

public class BootstrapButtonBase extends LinearLayout {
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
}
