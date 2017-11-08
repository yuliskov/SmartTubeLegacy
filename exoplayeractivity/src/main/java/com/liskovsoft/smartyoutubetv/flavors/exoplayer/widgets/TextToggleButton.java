package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class TextToggleButton extends ImageToggleButton {
    public TextToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TextToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextToggleButton(Context context) {
        super(context);
    }

    private void init() {
        mImageButton.setVisibility(View.GONE);
        mTextButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onButtonUnfocused() {

    }

    @Override
    protected void onButtonFocused() {

    }

    @Override
    protected void onButtonOn() {

    }

    @Override
    protected void onButtonOff() {

    }
}
