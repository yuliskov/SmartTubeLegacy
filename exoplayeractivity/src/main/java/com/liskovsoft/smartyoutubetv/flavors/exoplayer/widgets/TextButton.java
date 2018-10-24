package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;

public class TextButton extends TextToggleButton {
    public TextButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextButton(Context context) {
        super(context);
    }

    @Override
    protected void toggle() {
        setChecked(true); // always true, like on regular button
    }
}
