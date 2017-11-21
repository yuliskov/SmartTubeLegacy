package com.liskovsoft.smartyoutubetv.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.liskovsoft.smartyoutubetv.R;

public class BootstrapCheckBox extends LinearLayout {
    private String mTitleText;
    private LinearLayout wrapper;
    private LinearLayout content;
    private CheckBox mChkbox;
    private final int PADDING = Utils.convertDpToPixel(15, getContext());
    private float mNormalTextSize;
    private float mZoomedTextSize;

    public BootstrapCheckBox(Context context) {
        super(context);
        init();
    }

    public BootstrapCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BootstrapButton,
                0, 0);

        try {
            mTitleText = a.getString(R.styleable.BootstrapCheckBox_titleText);
        } finally {
            a.recycle();
        }

        init();
    }

    public BootstrapCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BootstrapCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate();
        applyDefaultAttributes();
        transferClicks();
        setOnFocus();
        calculateTextSize();
        setDefaultState();
    }

    private void calculateTextSize() {
        mNormalTextSize = Utils.convertPixelsToDp(mChkbox.getTextSize(), this.getContext());
        mZoomedTextSize = mNormalTextSize * 1.3f;
    }

    private void setDefaultState() {
        makeUnfocused();
    }

    private void applyDefaultAttributes() {
        if (mTitleText != null) {
            mChkbox.setText(mTitleText);
        }
        initCheckBox();
        setFocusable(false);
        setClickable(false);
    }

    private void initCheckBox() {
        int states[][] = {{android.R.attr.state_focused}, {}};
        int colors[] = {Color.TRANSPARENT, Color.DKGRAY};
        CompoundButtonCompat.setButtonTintList(mChkbox, new ColorStateList(states, colors));
    }

    private void setOnFocus() {
        wrapper.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    makeFocused();
                } else {
                    makeUnfocused();
                }
            }
        });
    }

    private void makeUnfocused() {
        //text.setTextAppearance(BootstrapButton.this.getContext(), R.style.BootstrapButtonTextUnfocused);
        mChkbox.setTextColor(Color.DKGRAY);
        mChkbox.setTextSize(mNormalTextSize);
        int semitransparentBlack = Color.argb(70, 0, 0, 0);
        content.setBackgroundColor(semitransparentBlack);
        wrapper.setPadding(PADDING, PADDING, PADDING, PADDING);
    }

    private void makeFocused() {
        //text.setTextAppearance(BootstrapButton.this.getContext(), R.style.BootstrapButtonTextFocused);
        mChkbox.setTextColor(Color.BLACK);
        mChkbox.setTextSize(mZoomedTextSize);
        content.setBackgroundColor(Color.WHITE);
        wrapper.setPadding(0, 0, 0, 0);
    }

    private void inflate() {
        inflate(getContext(), R.layout.bootstrap_checkbox, this);
        wrapper = (LinearLayout) findViewById(R.id.bootstrap_checkbox_wrapper);
        content = (LinearLayout) findViewById(R.id.bootstrap_checkbox_content);
        mChkbox = (CheckBox) findViewById(R.id.bootstrap_checkbox);
    }

    private void transferClicks() {
        wrapper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VERSION.SDK_INT >= 15) {
                    BootstrapCheckBox.this.callOnClick();
                } else {
                    BootstrapCheckBox.this.performClick();
                }
                mChkbox.setChecked(!mChkbox.isChecked());
            }
        });
    }

    public boolean isChecked() {
        return mChkbox.isChecked();
    }
}
