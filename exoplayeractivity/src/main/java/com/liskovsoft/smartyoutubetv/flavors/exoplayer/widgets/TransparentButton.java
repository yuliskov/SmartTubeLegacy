package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;

public class TransparentButton extends TextToggleButton {
    public TransparentButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TransparentButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TransparentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransparentButton(Context context) {
        super(context);
    }

    @Override
    protected void onButtonFocused() {
        // do nothing - be transparent
    }
}
