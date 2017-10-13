package com.liskovsoft.smartyoutubetv.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.R;

import java.util.ArrayList;
import java.util.List;

public class BootstrapButton extends LinearLayout {
    private Drawable mMainIcon;
    private String mTitleText;
    private LinearLayout wrapper;
    private LinearLayout content;
    private ImageView image;
    private TextView text;
    private List<OnClickListener> mOnClickListeners;
    private final int PADDING = convertDpToPixel(15, getContext());

    public BootstrapButton(Context context) {
        super(context);
        init();
    }

    public BootstrapButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BootstrapButton,
                0, 0);

        try {
            mMainIcon = a.getDrawable(R.styleable.BootstrapButton_mainIcon);
            mTitleText = a.getString(R.styleable.BootstrapButton_titleText);
        } finally {
            a.recycle();
        }
        
        init();
    }

    public BootstrapButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BootstrapButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (mOnClickListeners == null) {
            mOnClickListeners = new ArrayList<>();
        }
        mOnClickListeners.add(l);
    }

    private void init() {
        inflate();
        applyAttributes();
        transferClicks();
        setOnFocus();
        makeUnfocused();
    }

    private void applyAttributes() {
        if (mMainIcon != null) {
            image.setImageDrawable(mMainIcon);
        }
        if (mTitleText != null) {
            text.setText(mTitleText);
        }
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
        text.setTextColor(Color.GRAY);
        int semitransparentBlack = Color.argb(70, 0, 0, 0);
        content.setBackgroundColor(semitransparentBlack);
        wrapper.setPadding(PADDING, PADDING, PADDING, PADDING);
        applyGreyScaleFilter();
    }

    private void makeFocused() {
        //text.setTextAppearance(BootstrapButton.this.getContext(), R.style.BootstrapButtonTextFocused);
        text.setTextColor(Color.BLACK);
        content.setBackgroundColor(Color.WHITE);
        wrapper.setPadding(0, 0, 0, 0);
        resetGreyScaleFilter();
    }

    private void resetGreyScaleFilter() {
        image.setColorFilter(null); // reset Tint
    }

    private void applyGreyScaleFilter() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        image.setColorFilter(filter); // greyscale
    }

    private void transferClicks() {
        for (OnClickListener l : mOnClickListeners) {
            wrapper.setOnClickListener(l);
        }
    }

    private void inflate() {
        inflate(getContext(), R.layout.bootstrap_button, this);
        wrapper = (LinearLayout) findViewById(R.id.bootstrap_button_wrapper);
        content = (LinearLayout) findViewById(R.id.bootstrap_button_content);
        image = (ImageView) findViewById(R.id.bootstrap_button_image);
        text = (TextView) findViewById(R.id.bootstrap_button_text);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
