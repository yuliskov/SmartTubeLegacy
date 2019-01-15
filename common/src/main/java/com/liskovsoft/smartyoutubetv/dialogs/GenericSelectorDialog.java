package com.liskovsoft.smartyoutubetv.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.common.R;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericSelectorDialog implements OnClickListener {
    private final Context mActivity;
    protected AlertDialog mAlertDialog;
    private final DialogSourceBase mDialogSource;
    private ArrayList<CheckedTextView> mDialogItems;

    public interface DialogSourceBase {
        class DialogItem {
            private final String mTitle;
            private boolean mChecked;

            public DialogItem(String title, boolean checked) {
                mTitle = title;
                mChecked = checked;
            }

            public static DialogItem create(String title, boolean checked) {
                return new DialogItem(title, checked);
            }

            public String getTitle() {
                return mTitle;
            }

            public boolean getChecked() {
                return mChecked;
            }

            public void setChecked(boolean checked) {
                mChecked = checked;
            }
        }

        /**
         * Your data
         * @return pairs that consist of item text and tag
         */
        List<DialogItem> getItems();

        /**
         * Get dialog main title
         * @return dialog title
         */
        String getTitle();
    }

    public interface SingleDialogSource extends DialogSourceBase {
    }

    public interface MultiDialogSource extends DialogSourceBase {
    }

    public GenericSelectorDialog(Context activity, DialogSourceBase dialogSource) {
        mActivity = activity;
        mDialogSource = dialogSource;
    }

    public void run() {
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.AppDialog);
        View title = createCustomTitle(builder.getContext());
        mAlertDialog = builder.setCustomTitle(title).setView(buildView(builder.getContext())).create();
        mAlertDialog.show();
        updateViews(getRoot());
    }

    protected View getRoot() {
        return mAlertDialog.findViewById(R.id.root);
    }

    private View createCustomTitle(Context context) {
        String title = mDialogSource.getTitle();
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleView = inflater.inflate(R.layout.dialog_custom_title, null);
        TextView textView = titleView.findViewById(R.id.title);
        textView.setText(title);
        return titleView;
    }

    @SuppressLint("InflateParams")
    private View buildView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.generic_selection_dialog, null);
        ViewGroup root = view.findViewById(R.id.root);

        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
        attributeArray.recycle();

        mDialogItems = new ArrayList<>();

        for (DialogItem item : mDialogSource.getItems()) {
            CheckedTextView dialogItem = createDialogItem(inflater, root);
            dialogItem.setBackgroundResource(selectableItemBackgroundResourceId);
            dialogItem.setText(item.getTitle());

            dialogItem.setFocusable(true);
            dialogItem.setTag(item);
            dialogItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.dialog_text_size));
            dialogItem.setOnClickListener(this);
            mDialogItems.add(dialogItem);
            root.addView(dialogItem);
        }

        return view;
    }

    protected void updateViews(View root) {
        List<DialogItem> items = mDialogSource.getItems();
        for (DialogItem item : items) {
            CheckedTextView view = root.findViewWithTag(item);

            if (view != null) {
                view.setChecked(item.getChecked());
            }
        }
    }

    protected abstract CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root);
}
