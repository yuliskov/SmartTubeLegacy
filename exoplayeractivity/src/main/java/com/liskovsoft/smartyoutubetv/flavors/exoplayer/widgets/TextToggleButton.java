package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.liskovsoft.exoplayeractivity.R;

public class TextToggleButton extends ToggleButtonBase {
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
        mDescView.setVisibility(View.GONE);
        mTextButton.setVisibility(View.VISIBLE);
    }
}
