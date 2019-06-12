package com.liskovsoft.smartyoutubetv.widgets;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.CompoundButtonCompat;
import com.liskovsoft.smartyoutubetv.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BootstrapCheckButton extends BootstrapButtonBase {
    private String mTitleText;
    private LinearLayout mWrapper;
    private LinearLayout mContent;
    private CheckBox mChkbox;
    private final int PADDING = Utils.convertDpToPixel(5, getContext());
    private float mNormalTextSize;
    private float mZoomedTextSize;
    private List<OnCheckedChangeListener> mCheckedListeners = new ArrayList<>();

    public BootstrapCheckButton(Context context) {
        super(context);
        init();
    }

    public BootstrapCheckButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BootstrapCheckButton,
                0, 0);

        try {
            mTitleText = a.getString(R.styleable.BootstrapCheckButton_titleText);
            String handlerName = a.getString(R.styleable.BootstrapCheckButton_onCheckedChanged);
            if (handlerName != null) {
                setOnCheckedChangeListener(new DeclaredOnCheckedChangeListener(this, handlerName));
            }
        } finally {
            a.recycle();
        }

        init();
    }

    public BootstrapCheckButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BootstrapCheckButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate();
        applyDefaultAttributes();
        transferClicks();
        setOnFocus();
        calculateTextSize();
        setDefaultState();
    }

    private void inflate() {
        inflate(getContext(), R.layout.bootstrap_check_button, this);
        mWrapper = findViewById(R.id.bootstrap_checkbox_wrapper);
        mContent = findViewById(R.id.bootstrap_checkbox_content);
        mChkbox = findViewById(R.id.bootstrap_checkbox);
    }

    private void calculateTextSize() {
        mNormalTextSize = Utils.convertPixelsToDp(mChkbox.getTextSize(), this.getContext());
        mZoomedTextSize = mNormalTextSize * 1.3f;
    }

    private void setDefaultState() {
        makeUnfocused();
    }

    private void applyDefaultAttributes() {
        if (mTitleText != null) {
            mChkbox.setText(mTitleText);
        }
        initCheckBox();
        setFocusable(false);
        setClickable(false);
    }

    private void initCheckBox() {
        int states[][] = {{android.R.attr.state_focused}, {}};
        int colors[] = {Color.TRANSPARENT, Color.DKGRAY};
        CompoundButtonCompat.setButtonTintList(mChkbox, new ColorStateList(states, colors));
    }

    private void setOnFocus() {
        mWrapper.setOnFocusChangeListener(new OnFocusChangeListener() {
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

    protected void makeFocused() {
        super.makeFocused();
        mChkbox.setTextColor(Color.BLACK);
        mChkbox.setTextSize(mZoomedTextSize);
        mContent.setBackgroundColor(Color.WHITE);
        mWrapper.setPadding(0, 0, 0, 0);
    }

    protected void makeUnfocused() {
        super.makeUnfocused();
        mChkbox.setTextColor(Color.DKGRAY);
        mChkbox.setTextSize(mNormalTextSize);
        int semitransparentBlack = Color.argb(70, 0, 0, 0);
        mContent.setBackgroundColor(semitransparentBlack);
        mWrapper.setPadding(PADDING, PADDING, PADDING, PADDING);
    }

    private void transferClicks() {
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VERSION.SDK_INT >= 15) {
                    BootstrapCheckButton.this.callOnClick();
                } else {
                    BootstrapCheckButton.this.performClick();
                }
                mChkbox.setChecked(!mChkbox.isChecked());
                callCheckedListener(mChkbox.isChecked());
            }
        };
        mWrapper.setOnClickListener(clickListener);
    }

    public boolean isChecked() {
        return mChkbox.isChecked();
    }

    public void setChecked(boolean isChecked) {
        mChkbox.setChecked(isChecked);
    }

    private void callCheckedListener(boolean isChecked) {
        for (OnCheckedChangeListener listener : mCheckedListeners) {
            if (listener != null)
                listener.onCheckedChanged(this, isChecked);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mCheckedListeners.add(listener);
    }

    @Override
    protected View getWrapper() {
        return mWrapper;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(BootstrapCheckButton button, boolean isChecked);
    }

    /**
     * An implementation of Listener that attempts to lazily load a
     * named handling method from a parent or ancestor context.
     */
    private class DeclaredOnCheckedChangeListener implements OnCheckedChangeListener {
        private final View mHostView;
        private final String mMethodName;

        private Method mResolvedMethod;
        private Context mResolvedContext;

        public DeclaredOnCheckedChangeListener(@NonNull View hostView, @NonNull String methodName) {
            mHostView = hostView;
            mMethodName = methodName;
        }

        @Override
        public void onCheckedChanged(@NonNull BootstrapCheckButton compoundButton, boolean b) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView.getContext(), mMethodName);
            }

            try {
                mResolvedMethod.invoke(mResolvedContext, compoundButton, b);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for app:onCheckedChanged", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for app:onCheckedChanged", e);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        final Method method = context.getClass().getMethod(mMethodName, BootstrapCheckButton.class, boolean.class);
                        if (method != null) {
                            mResolvedMethod = method;
                            mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    // Failed to find method, keep searching up the hierarchy.
                }

                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }

            final int id = mHostView.getId();
            final String idText = id == NO_ID ? "" : " with id '"
                    + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + "(BootstrapCheckBox, boolean) in a parent or ancestor Context for app:onCheckedChanged "
                    + "attribute defined on view " + mHostView.getClass() + idText);
        }
    }
}
