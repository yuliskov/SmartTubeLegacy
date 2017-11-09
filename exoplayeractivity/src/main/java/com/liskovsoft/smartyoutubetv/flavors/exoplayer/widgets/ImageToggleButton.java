package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liskovsoft.exoplayeractivity.R;

public abstract class ImageToggleButton extends LinearLayout {
    protected Drawable mImage;
    protected Drawable mImageOn;
    protected Drawable mImageOff;
    protected String mDescText;
    protected TextView mDescView;
    protected ImageButton mImageButton;
    protected boolean mIsChecked;
    protected String mTextOn;
    protected String mTextOff;
    protected Button mTextButton;

    public ImageToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ImageToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ImageToggleButton,
                0, 0);

        try {
            mImage = a.getDrawable(R.styleable.ImageToggleButton_image);
            mImageOn = a.getDrawable(R.styleable.ImageToggleButton_imageOn);
            mImageOff = a.getDrawable(R.styleable.ImageToggleButton_imageOff);
            mTextOn = a.getString(R.styleable.ImageToggleButton_textOn);
            mTextOff = a.getString(R.styleable.ImageToggleButton_textOff);
            mDescText = a.getString(R.styleable.ImageToggleButton_desc);
        } finally {
            a.recycle();
        }

        init();
    }

    public ImageToggleButton(Context context) {
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
        mTextButton = (Button) findViewById(R.id.text_button);
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
        onButtonUnfocused();
        //mImageButton.setBackgroundResource(R.color.transparent);
    }

    protected abstract void onButtonUnfocused();

    private void makeFocused() {
        mDescView.setText(mDescText);
        onButtonFocused();
        //mImageButton.setBackgroundResource(R.color.white_50);
    }

    protected abstract void onButtonFocused();

    private void initElems() {
        if (isChecked()) {
            onButtonChecked();
            //mImageButton.setImageDrawable(mImageOn);
        } else {
            onButtonUnchecked();
            //mImageButton.setImageDrawable(mImageOff);
        }
    }

    protected abstract void onButtonUnchecked();
    protected abstract void onButtonChecked();

    protected boolean isChecked() {
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
