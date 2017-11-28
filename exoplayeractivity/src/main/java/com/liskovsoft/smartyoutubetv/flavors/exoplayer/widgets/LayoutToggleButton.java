package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;
import com.liskovsoft.exoplayeractivity.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutToggleButton extends TextToggleButton {
    private int mLayoutId1;
    private int mLayoutId2;
    private View mLayout1;
    private View mLayout2;
    private List<View> mButtons;

    public LayoutToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public LayoutToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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
        } finally {
            a.recycle();
        }

        init();
    }

    public LayoutToggleButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        // setOnVisibilityChangeListener();
    }

    private void setOnVisibilityChangeListener() {
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getVisibility() != View.VISIBLE)
                    resetState();
            }
        });
    }

    public List<View> findViewsByType(ViewGroup root, Class clazz) {
        List<View> allViews = new ArrayList<>();

        final int childCount = root.getChildCount();
        for (int i=0; i<childCount; i++){
            final View childView = root.getChildAt(i);

            if (childView.getClass() == clazz) {
                allViews.add(childView);
            }

            if (childView instanceof ViewGroup){
                allViews.addAll(findViewsByType((ViewGroup)childView, clazz));
            }
        }

        return allViews;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        View rootView = getRootView();
        mLayout1 = rootView.findViewById(mLayoutId1);
        mLayout2 = rootView.findViewById(mLayoutId2);

        mButtons = findViewsByType((ViewGroup) rootView, getClass());
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
        for (View button : mButtons) {
            if (button != this)
                ((LayoutToggleButton) button).disable();
        }
    }

    private void enableOtherToggles() {
        for (View button : mButtons) {
            if (button != this)
                ((LayoutToggleButton) button).enable();
        }
    }
}
