package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liskovsoft.exoplayeractivity.R;

public class ToggleButtonBase extends LinearLayout {
    private Drawable mImage;
    private Drawable mImageOn;
    private Drawable mImageOff;
    private String mDescText;
    private TextView mDescView;
    private ImageButton mImageButton;
    private boolean mIsChecked;

    public ToggleButtonBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ToggleButtonBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToggleButtonBase(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ImageToggleButton,
                0, 0);

        try {
            mImage = a.getDrawable(R.styleable.ImageToggleButton_image);
            mImageOn = a.getDrawable(R.styleable.ImageToggleButton_imageOn);
            mImageOff = a.getDrawable(R.styleable.ImageToggleButton_imageOff);
            mDescText = a.getString(R.styleable.ImageToggleButton_desc);
        } finally {
            a.recycle();
        }

        init();
    }

    public ToggleButtonBase(Context context) {
        super(context);
    }

    private void init() {
        inflate();
        applyCommonProps();
        setOnFocus();
        setOnClick();
        initElems();
        makeUnfocused();
    }

    private void setOnClick() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
    }

    private void setOnFocus() {
        setOnFocusChangeListener(new OnFocusChangeListener() {
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

    private void inflate() {
        inflate(getContext(), R.layout.image_toggle_button, this);
        mDescView = (TextView) findViewById(R.id.description);
        mImageButton = (ImageButton) findViewById(R.id.image_button);
    }

    private void applyCommonProps() {
        setOrientation(LinearLayout.VERTICAL);
        setClickable(true);
        setFocusable(true);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        // NOTE: disable background when using style attribute
        setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
    }

    private void makeUnfocused() {
        mDescView.setText("");
        mImageButton.setBackgroundResource(R.color.transparent);
    }

    private void makeFocused() {
        mDescView.setText(mDescText);
        mImageButton.setBackgroundResource(R.color.white_50);
    }

    private void initElems() {
        if (isChecked()) {
            mImageButton.setImageDrawable(mImageOn);
        } else {
            mImageButton.setImageDrawable(mImageOff);
        }
    }

    private boolean isChecked() {
        return mIsChecked;
    }
    
    private void toggle() {
        mIsChecked = !mIsChecked;
        initElems();
    }

    private void applyDefaultDimens() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            float width = getResources().getDimension(R.dimen.exo_media_button_width);
            float height = getResources().getDimension(R.dimen.exo_media_button_height);
            int realWidth = Utils.convertDpToPixel(width, getContext());
            int realHeight = Utils.convertDpToPixel(height, getContext());
            layoutParams = new ViewGroup.LayoutParams(new LayoutParams(realWidth, realHeight));
            setLayoutParams(layoutParams);
            return;
        }
    }
}
