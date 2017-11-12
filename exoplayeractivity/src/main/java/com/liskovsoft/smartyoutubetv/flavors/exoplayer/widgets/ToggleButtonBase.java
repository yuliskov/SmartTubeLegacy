package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liskovsoft.exoplayeractivity.R;

public abstract class ToggleButtonBase extends LinearLayout {
    private int mBindToId;
    protected Drawable mImage;
    protected Drawable mImageOn;
    protected Drawable mImageOff;
    protected String mDescText;
    protected TextView mDescView;
    protected ImageButton mImageButton;
    protected boolean mIsChecked;
    protected String mTextOn;
    protected String mTextOff;
    protected Button mTextButton;
    protected LinearLayout mToggleButtonWrapper;
    private OnCheckedChangeListener mCheckedListener;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(ToggleButtonBase button, boolean isChecked);
    }

    public ToggleButtonBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ToggleButtonBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ToggleButtonBase(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ToggleButtonBase,
                0, 0);

        try {
            mImage = a.getDrawable(R.styleable.ToggleButtonBase_image);
            mImageOn = a.getDrawable(R.styleable.ToggleButtonBase_imageOn);
            mImageOff = a.getDrawable(R.styleable.ToggleButtonBase_imageOff);
            mTextOn = a.getString(R.styleable.ToggleButtonBase_textOn);
            mTextOff = a.getString(R.styleable.ToggleButtonBase_textOff);
            mDescText = a.getString(R.styleable.ToggleButtonBase_desc);
            mBindToId = a.getResourceId(R.styleable.ToggleButtonBase_bindTo, 0);
        } finally {
            a.recycle();
        }

        init();
    }

    public ToggleButtonBase(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate();
        applyCommonProps();
        setOnFocus();
        setOnClick();
        initElems();
        makeUnfocused();
    }

    private void setOnClick() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
    }

    private void setOnFocus() {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    makeFocused();
                } else {
                    makeUnfocused();
                }
            }
        });
    }

    private void inflate() {
        inflate(getContext(), R.layout.image_toggle_button, this);
        mDescView = (TextView) findViewById(R.id.description);
        mImageButton = (ImageButton) findViewById(R.id.image_button);
        mTextButton = (Button) findViewById(R.id.text_button);
        mToggleButtonWrapper = (LinearLayout) findViewById(R.id.toggle_button_wrapper);
    }

    private void applyCommonProps() {
        setOrientation(LinearLayout.VERTICAL);
        setClickable(true);
        setFocusable(true);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        // NOTE: disable background when using style attribute
        setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
    }

    private void makeUnfocused() {
        mDescView.setText("");
        onButtonUnfocused();
        //mImageButton.setBackgroundResource(R.color.transparent);
    }

    protected abstract void onButtonUnfocused();

    private void makeFocused() {
        mDescView.setText(mDescText);
        onButtonFocused();
        //mImageButton.setBackgroundResource(R.color.white_50);
    }

    protected abstract void onButtonFocused();

    private void initElems() {
        if (isChecked()) {
            onButtonChecked();
            callCheckedListener(true);
            uncheckBindToView();
        } else {
            onButtonUnchecked();
            callCheckedListener(false);
        }
    }

    private void uncheckBindToView() {
        View bindToView = getRootView().findViewById(mBindToId);
        if (bindToView != null)
            ((ToggleButtonBase) bindToView).uncheck();
    }

    private void uncheck() {
        mIsChecked = false;
        initElems();
    }

    private void callCheckedListener(boolean isChecked) {
        if (mCheckedListener == null) {
            return;
        }
        mCheckedListener.onCheckedChanged(this, isChecked);
    }

    protected abstract void onButtonUnchecked();
    protected abstract void onButtonChecked();

    protected boolean isChecked() {
        return mIsChecked;
    }
    
    private void toggle() {
        mIsChecked = !mIsChecked;
        initElems();
    }

    public void resetState() {
        mIsChecked = false;
        initElems();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mCheckedListener = listener;
    }

    private void applyDefaultDimens() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            float width = getResources().getDimension(R.dimen.exo_media_button_width);
            float height = getResources().getDimension(R.dimen.exo_media_button_height);
            int realWidth = Utils.convertDpToPixel(width, getContext());
            int realHeight = Utils.convertDpToPixel(height, getContext());
            layoutParams = new ViewGroup.LayoutParams(new LayoutParams(realWidth, realHeight));
            setLayoutParams(layoutParams);
            return;
        }
    }
}
