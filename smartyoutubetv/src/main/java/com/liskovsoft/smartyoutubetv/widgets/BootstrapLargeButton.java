package com.liskovsoft.smartyoutubetv.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.R;

public class BootstrapLargeButton extends BootstrapButtonBase {
    private Drawable mMainIcon;
    private String mTitleText;
    private LinearLayout mWrapper;
    private LinearLayout mContent;
    private ImageView mImage;
    private TextView mText;
    private final int PADDING = Utils.convertDpToPixel(15, getContext());
    private float mNormalTextSize;
    private float mZoomedTextSize;

    public BootstrapLargeButton(Context context) {
        super(context);
        init();
    }

    public BootstrapLargeButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BootstrapLargeButton,
                0, 0);

        try {
            mMainIcon = a.getDrawable(R.styleable.BootstrapLargeButton_mainIcon);
            mTitleText = a.getString(R.styleable.BootstrapLargeButton_titleText);
        } finally {
            a.recycle();
        }
        
        init();
    }

    public BootstrapLargeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BootstrapLargeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        mNormalTextSize = Utils.convertPixelsToDp(mText.getTextSize(), this.getContext());
        mZoomedTextSize = mNormalTextSize * 1.3f;
    }

    private void setDefaultState() {
        makeUnfocused();
    }

    private void applyDefaultAttributes() {
        if (mMainIcon != null) {
            mImage.setImageDrawable(mMainIcon);
        }
        if (mTitleText != null) {
            mText.setText(mTitleText);
        }
        setFocusable(false);
        setClickable(false);
    }

    private void setOnFocus() {
        mWrapper.setOnFocusChangeListener(new OnFocusChangeListener() {
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

    protected void makeUnfocused() {
        super.makeUnfocused();
        //text.setTextAppearance(BootstrapBadgeButton.this.getContext(), R.style.BootstrapButtonTextUnfocused);
        mText.setTextColor(Color.DKGRAY);
        mText.setTextSize(mNormalTextSize);
        int semitransparentBlack = Color.argb(70, 0, 0, 0);
        mContent.setBackgroundColor(semitransparentBlack);
        mWrapper.setPadding(PADDING, PADDING, PADDING, PADDING);
        setImageColor();
    }

    protected void makeFocused() {
        super.makeFocused();
        //text.setTextAppearance(BootstrapBadgeButton.this.getContext(), R.style.BootstrapButtonTextFocused);
        mText.setTextColor(Color.BLACK);
        mText.setTextSize(mZoomedTextSize);
        mContent.setBackgroundColor(Color.WHITE);
        mWrapper.setPadding(0, 0, 0, 0);
        resetImageColor();
    }

    private void resetImageColor() {
        mImage.setColorFilter(null); // reset Tint
    }

    private void setImageColor() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        mImage.setColorFilter(filter); // greyscale
    }

    private void inflate() {
        inflate(getContext(), R.layout.bootstrap_large_button, this);
        mWrapper = findViewById(R.id.bootstrap_button_wrapper);
        mContent = findViewById(R.id.bootstrap_button_content);
        mImage = findViewById(R.id.bootstrap_button_image);
        mText = findViewById(R.id.bootstrap_button_text);
    }

    private void transferClicks() {
        mWrapper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VERSION.SDK_INT >= 15) {
                    BootstrapLargeButton.this.callOnClick();
                } else {
                    BootstrapLargeButton.this.performClick();
                }
            }
        });
    }

    @Override
    protected View getWrapper() {
        return mWrapper;
    }
}
