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
import java.util.LinkedHashMap;
import java.util.Map;

public class LanguageSelector implements OnClickListener {
    private final BootstrapActivity mActivity;
    private AlertDialog alertDialog;
    private HashMap<String, String> mLangMap;
    private ArrayList<CheckedTextView> mLangViews;
    private static final int TEXT_SIZE_DP = 15;
    private String mCurrentLang = "ru";
    private LangUpdater mLangUpdater;

    public LanguageSelector(BootstrapActivity activity) {
        mActivity = activity;
        initLangMap();
        initCurrentLang();
    }

    private void initCurrentLang() {
        mLangUpdater = new LangUpdater(mActivity);
        mCurrentLang = mLangUpdater.getLocale();
    }

    private void initLangMap() {
        mLangMap = new LinkedHashMap<>();
        mLangMap.put(mActivity.getString(R.string.english_lang), "en");
        mLangMap.put(mActivity.getString(R.string.chinese_lang), "zh");
        mLangMap.put(mActivity.getString(R.string.ukrainian_lang), "uk");
        mLangMap.put(mActivity.getString(R.string.russian_lang), "ru");
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
        ViewGroup root = (ViewGroup) view.findViewById(R.id.root);

        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
        attributeArray.recycle();

        mLangViews = new ArrayList<>();

        for (Map.Entry<String, String> entry : mLangMap.entrySet()) {
            CheckedTextView langView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_single_choice, root, false);
            langView.setBackgroundResource(selectableItemBackgroundResourceId);
            langView.setText(entry.getKey());

            langView.setFocusable(true);
            langView.setTag(entry.getValue());
            langView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DP);
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
            mLangUpdater.setLocale(newLang);
            mActivity.restart();
            // close dialog
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
