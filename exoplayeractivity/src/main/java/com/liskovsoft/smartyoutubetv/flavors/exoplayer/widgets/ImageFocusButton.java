package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;

public class ImageFocusButton extends ImageToggleButton {
    public ImageFocusButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ImageFocusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageFocusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageFocusButton(Context context) {
        super(context);
    }

    @Override
    protected void onButtonFocused() {
        setChecked(true); // always true, like on regular button
    }
}
