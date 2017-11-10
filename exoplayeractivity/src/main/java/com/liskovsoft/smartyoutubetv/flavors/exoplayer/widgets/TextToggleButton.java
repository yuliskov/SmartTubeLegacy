package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.liskovsoft.exoplayeractivity.R;

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
        mTextButton.setBackgroundResource(R.color.transparent);
    }

    @Override
    protected void onButtonFocused() {
        mTextButton.setBackgroundResource(R.color.white_50);
    }

    @Override
    protected void onButtonUnchecked() {
        mTextButton.setText(mTextOff);
    }

    @Override
    protected void onButtonChecked() {
        mTextButton.setText(mTextOn);
    }

    public void setText(int label) {
        mTextButton.setText(label);
    }

    public CharSequence getText() {
        return mTextButton.getText();
    }
}
