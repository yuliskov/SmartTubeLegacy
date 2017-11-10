package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liskovsoft.exoplayeractivity.R;

public class LayoutToggleButton extends TextToggleButton {
    private static final int LAYOUT_CHECKED = 11;
    private int mOtherToggleId;
    private int mLayoutId1;
    private int mLayoutId2;
    private View mLayout1;
    private View mLayout2;
    private View mOtherToggle;

    public LayoutToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public LayoutToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LayoutToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LayoutToggleButton,
                0, 0);

        try {
            mLayoutId1 = a.getResourceId(R.styleable.LayoutToggleButton_layout1, 0);
            mLayoutId2 = a.getResourceId(R.styleable.LayoutToggleButton_layout2, 0);
            mOtherToggleId = a.getResourceId(R.styleable.LayoutToggleButton_other_toggle, 0);
        } finally {
            a.recycle();
        }

        init();
    }

    public LayoutToggleButton(Context context) {
        super(context);
    }

    private void init() {
        //findLayouts();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        View rootView = getRootView();
        mLayout1 = rootView.findViewById(mLayoutId1);
        mLayout2 = rootView.findViewById(mLayoutId2);
        mOtherToggle = rootView.findViewById(mOtherToggleId);
    }

    @Override
    protected void onButtonChecked() {
        super.onButtonChecked();
        if (mLayout1 == null) {
            return;
        }
        mLayout1.setVisibility(GONE);
        mLayout2.setVisibility(VISIBLE);

        disableOtherToggles();
    }

    @Override
    protected void onButtonUnchecked() {
        super.onButtonUnchecked();
        if (mLayout1 == null) {
            return;
        }
        mLayout1.setVisibility(VISIBLE);
        mLayout2.setVisibility(GONE);

        enableOtherToggles();
    }

    private void disableOtherToggles() {
        if (mOtherToggle == null) {
            return;
        }

        mOtherToggle.setFocusable(false);
        mOtherToggle.setClickable(false);
    }

    private void enableOtherToggles() {
        if (mOtherToggle == null) {
            return;
        }

        mOtherToggle.setFocusable(true);
        mOtherToggle.setClickable(true);
    }

    private void setGlobalChecked() {
        View rootView = getRootView();
        rootView.setTag(LAYOUT_CHECKED, true);
    }

    private void setGlobalUnchecked() {
        View rootView = getRootView();
        rootView.setTag(LAYOUT_CHECKED, null);
    }

    private boolean getGlobalChecked() {
        View rootView = getRootView();
        return rootView.getTag(LAYOUT_CHECKED) != null;
    }
}
