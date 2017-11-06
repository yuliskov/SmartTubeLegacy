package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.liskovsoft.exoplayeractivity.R;

public class ExoToggleButton extends LinearLayout {
    private Drawable mImage;
    private Drawable mOnImage;
    private Drawable mOffImage;
    private String mDesc;

    public ExoToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ExoToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExoToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExoToggleButton,
                0, 0);

        try {
            mImage = a.getDrawable(R.styleable.ExoToggleButton_image);
            mOnImage = a.getDrawable(R.styleable.ExoToggleButton_imageOn);
            mOffImage = a.getDrawable(R.styleable.ExoToggleButton_imageOff);
            mDesc = a.getString(R.styleable.ExoToggleButton_desc);
        } finally {
            a.recycle();
        }

        init();
    }

    public ExoToggleButton(Context context) {
        super(context);
    }

    private void init() {
        inflate();
        applyDefaultAttributes();
        //setOnFocus();
        //calculateTextSize();
        //setDefaultState();
    }

    private void applyDefaultAttributes() {
        setOrientation(LinearLayout.VERTICAL);
    }

    private void inflate() {
        inflate(getContext(), R.layout.exo_toggle_button, this);
    }
}
