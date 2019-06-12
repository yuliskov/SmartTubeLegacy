package com.liskovsoft.browser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import com.liskovsoft.browser.SuggestionsAdapter.CompletionListener;

/**
 * TODO: not implemented
 */
public class UrlInputView extends AppCompatAutoCompleteTextView implements OnEditorActionListener, CompletionListener, OnItemClickListener, TextWatcher {

    private UrlInputListener mListener;
    private InputMethodManager mInputManager;
    private SuggestionsAdapter mAdapter;
    private boolean mNeedsUpdate;
    private int mState;

    static interface StateListener {
        static final int STATE_NORMAL = 0;
        static final int STATE_HIGHLIGHTED = 1;
        static final int STATE_EDITED = 2;

        public void onStateChanged(int state);
    }

    public UrlInputView(Context context) {
        this(context, null);
    }

    public UrlInputView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autoCompleteTextViewStyle);
    }

    public UrlInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        mInputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        setOnEditorActionListener(this);
        mAdapter = new SuggestionsAdapter(ctx, this);
        setAdapter(mAdapter);
        setSelectAllOnFocus(true);
        onConfigurationChanged(ctx.getResources().getConfiguration());
        setThreshold(1);
        setOnItemClickListener(this);
        mNeedsUpdate = false;
        addTextChangedListener(this);
        setDropDownAnchor(com.liskovsoft.browser.R.id.taburlbar);
        mState = StateListener.STATE_NORMAL;
    }

    /**
     * check if focus change requires a title bar update
     */
    boolean needsUpdate() {
        return mNeedsUpdate;
    }

    /**
     * clear the focus change needs title bar update flag
     */
    void clearNeedsUpdate() {
        mNeedsUpdate = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onSearch(String txt) {

    }

    @Override
    public void onSelect(String txt, int type, String extraData) {

    }

    void hideIME() {
        mInputManager.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    void showIME() {
        // TODO: focusIn not found in IM
        //mInputManager.focusIn(this);
        mInputManager.showSoftInput(this, 0);
    }

    public void setUrlInputListener(UrlInputListener listener) {
        mListener = listener;
    }

    void setController(UiController controller) {
        UrlSelectionActionMode urlSelectionMode = new UrlSelectionActionMode(controller);
        setCustomSelectionActionModeCallback(urlSelectionMode);
    }
}
