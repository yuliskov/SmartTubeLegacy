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
        mTextButton.setVisibility(View.GONE);
        mImageButton.setVisibility(View.VISIBLE);
        mDescView.setVisibility(View.VISIBLE);
    }
}
