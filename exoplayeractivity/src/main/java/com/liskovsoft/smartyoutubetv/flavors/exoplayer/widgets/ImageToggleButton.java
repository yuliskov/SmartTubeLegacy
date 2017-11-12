package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.liskovsoft.exoplayeractivity.R;

public class ImageToggleButton extends ToggleButtonBase {
    public ImageToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ImageToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageToggleButton(Context context) {
        super(context);
    }

    private void init() {
        mImageButton.setVisibility(View.VISIBLE);
        mTextButton.setVisibility(View.GONE);
    }

    @Override
    protected void onButtonUnfocused() {
        mImageButton.setBackgroundResource(R.color.transparent);
    }

    @Override
    protected void onButtonFocused() {
        mImageButton.setBackgroundResource(R.color.white_50);
    }

    @Override
    protected void onButtonChecked() {
        mImageButton.setImageDrawable(mImageOn);
    }

    @Override
    protected void onButtonUnchecked() {
        mImageButton.setImageDrawable(mImageOff);
    }
}
