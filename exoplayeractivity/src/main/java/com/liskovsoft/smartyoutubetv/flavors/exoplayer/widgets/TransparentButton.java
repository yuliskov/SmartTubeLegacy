package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;

public class TransparentButton extends TextToggleButton implements OnFocusChangeListener {
    public TransparentButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnFocusChangeListener(this);
    }

    public TransparentButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnFocusChangeListener(this);
    }

    public TransparentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnFocusChangeListener(this);
    }

    public TransparentButton(Context context) {
        super(context);
        setOnFocusChangeListener(this);
    }

    @Override
    protected void onButtonFocused() {
        // do nothing - be transparent
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == this) {
            callCheckedListener(hasFocus);
        }
    }
}
