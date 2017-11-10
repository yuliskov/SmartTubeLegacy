package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.liskovsoft.exoplayeractivity.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutToggleButton extends TextToggleButton {
    private static final int LAYOUT_CHECKED = 11;
    private static final int LAYOUT_TOGGLE_BUTTON = 12;
    private int mLayoutId1;
    private int mLayoutId2;
    private View mLayout1;
    private View mLayout2;
    private List<View> mButtons;

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
        } finally {
            a.recycle();
        }

        init();
    }

    public LayoutToggleButton(Context context) {
        super(context);
    }

    private void init() {
        setTag(LAYOUT_TOGGLE_BUTTON, true);
        //findLayouts();
    }

    /**
     * Get all the views which matches the given Tag recursively
     * @param root parent view. for e.g. Layouts
     * @param tagId tag id to look for
     * @return List of views
     */
    public static List<View> findAllViewsWithTagId(ViewGroup root, int tagId){
        List<View> allViews = new ArrayList<>();

        final int childCount = root.getChildCount();
        for(int i=0; i<childCount; i++){
            final View childView = root.getChildAt(i);

            if(childView instanceof ViewGroup){
                allViews.addAll(findAllViewsWithTagId((ViewGroup)childView, tagId));
            }
            else{
                final Object tagView = childView.getTag(tagId);
                if(tagView != null)
                    allViews.add(childView);
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

        mButtons = findAllViewsWithTagId((ViewGroup) rootView, LAYOUT_TOGGLE_BUTTON);
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

    private void disable() {
        Toast.makeText(getContext(), "disable LayoutToggleButton", Toast.LENGTH_LONG).show();
    }

    private void enable() {
        Toast.makeText(getContext(), "enable LayoutToggleButton", Toast.LENGTH_LONG).show();
    }

    //private void disableOtherToggles() {
    //    if (mOtherToggle == null) {
    //        return;
    //    }
    //
    //    mOtherToggle.setFocusable(false);
    //    mOtherToggle.setClickable(false);
    //}
    //
    //private void enableOtherToggles() {
    //    if (mOtherToggle == null) {
    //        return;
    //    }
    //
    //    mOtherToggle.setFocusable(true);
    //    mOtherToggle.setClickable(true);
    //}

    //private void setGlobalChecked() {
    //    View rootView = getRootView();
    //    rootView.setTag(LAYOUT_CHECKED, true);
    //}
    //
    //private void setGlobalUnchecked() {
    //    View rootView = getRootView();
    //    rootView.setTag(LAYOUT_CHECKED, null);
    //}
    //
    //private boolean getGlobalChecked() {
    //    View rootView = getRootView();
    //    return rootView.getTag(LAYOUT_CHECKED) != null;
    //}
}
