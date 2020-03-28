package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;

public class TextFocusButton extends TextToggleButton {
    public TextFocusButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TextFocusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextFocusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextFocusButton(Context context) {
        super(context);
    }

    @Override
    protected void onButtonFocused() {
        setChecked(true); // always true, like on regular button
    }
}
