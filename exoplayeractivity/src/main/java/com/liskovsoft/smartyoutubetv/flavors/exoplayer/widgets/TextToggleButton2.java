package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.liskovsoft.exoplayeractivity.R;

public class TextToggleButton2 extends TextToggleButton {
    private int mId1;
    private int mId2;
    private String mTag1;
    private String mTag2;
    private View mLayout1;
    private View mLayout2;

    public TextToggleButton2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TextToggleButton2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextToggleButton2(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextToggleButton2,
                0, 0);

        try {
            mId1 = a.getResourceId(R.styleable.TextToggleButton2_layout1, 0);
            mId2 = a.getResourceId(R.styleable.TextToggleButton2_layout2, 0);
        } finally {
            a.recycle();
        }

        init();
    }

    public TextToggleButton2(Context context) {
        super(context);
    }

    private void init() {
        //findLayouts();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        
        View rootView = getRootView();
        mLayout1 = rootView.findViewById(mId1);
        mLayout2 = rootView.findViewById(mId2);
    }

    private void setListeners() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onButtonChecked() {
        super.onButtonChecked();
        if (mLayout1 == null) {
            return;
        }
        mLayout1.setVisibility(GONE);
        mLayout1.setVisibility(VISIBLE);
    }

    @Override
    protected void onButtonUnchecked() {
        super.onButtonUnchecked();
        if (mLayout1 == null) {
            return;
        }
        mLayout1.setVisibility(VISIBLE);
        mLayout2.setVisibility(GONE);
    }
}
