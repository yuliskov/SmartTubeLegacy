package com.liskovsoft.smartyoutubetv.bootstrap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageSelectorDialog implements OnClickListener {
    private final BootstrapActivity mActivity;
    private AlertDialog alertDialog;
    private ArrayList<CheckedTextView> mLangViews;
    private String mCurrentLang;
    private LangUpdater mLangUpdater;

    public LanguageSelectorDialog(BootstrapActivity activity) {
        mActivity = activity;
        initLangUpdater();
    }

    private void initLangUpdater() {
        mLangUpdater = new LangUpdater(mActivity);
        mCurrentLang = mLangUpdater.getPreferredLocale();
    }

    public void run() {
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        alertDialog = builder.setTitle(R.string.language_dialog_title).setView(buildView(builder.getContext())).create();
        alertDialog.show();
    }

    @SuppressLint("InflateParams")
    private View buildView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lang_selection_dialog, null);
        ViewGroup root = view.findViewById(R.id.root);

        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
        attributeArray.recycle();

        mLangViews = new ArrayList<>();

        for (Map.Entry<String, String> entry : mLangUpdater.getSupportedLocales().entrySet()) {
            CheckedTextView langView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_single_choice, root, false);
            langView.setBackgroundResource(selectableItemBackgroundResourceId);
            langView.setText(entry.getKey());

            langView.setFocusable(true);
            langView.setTag(entry.getValue());
            langView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.text_size_dp));
            langView.setOnClickListener(this);
            mLangViews.add(langView);
            root.addView(langView);
        }

        updateViews();

        return view;
    }

    private void updateViews() {
        for (CheckedTextView view : mLangViews) {
            if (view.getTag().equals(mCurrentLang)) {
                view.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        String newLang = (String) view.getTag();
        if (!newLang.equals(mCurrentLang)) {
            mLangUpdater.setPreferredLocale(newLang);

            // close dialog
            alertDialog.dismiss();
            alertDialog = null;

            mActivity.restart();
        }
    }
}
