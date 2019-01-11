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
            private final String itemName;
            private final Object itemTag;

            private DialogItem(String itemName, Object itemTag) {
                this.itemName = itemName;
                this.itemTag = itemTag;
            }

            public static DialogItem create(String itemName, Object itemTag) {
                return new DialogItem(itemName, itemTag);
            }
        }

        /**
         * Your data
         * @return pairs that consist of item text and tag
         */
        List<DialogItem> getItems();

        /**
         * Notify about that item has been selected
         * @param itemTag selected item's tag
         */
        void setSelectedItemByTag(Object itemTag);

        /**
         * Get dialog main title
         * @return dialog title
         */
        String getTitle();
    }

    public interface SingleDialogSource extends DialogSourceBase {
        /**
         * Get selected tag
         * @return selected tag
         */
        Object getSelectedItemTag();
    }

    public interface MultiDialogSource extends DialogSourceBase {
        /**
         * Get selected tags
         * @return selected tags
         */
        List<Object> getSelectedItemsTags();

        /**
         * Notify about that item has been unselected
         * @param itemTag unselected item's tag
         */
        void setUnselectedItemByTag(Object itemTag);
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
        updateViews(mAlertDialog.findViewById(R.id.root));
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
            dialogItem.setText(item.itemName);

            dialogItem.setFocusable(true);
            dialogItem.setTag(item.itemTag);
            dialogItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.dialog_text_size));
            dialogItem.setOnClickListener(this);
            mDialogItems.add(dialogItem);
            root.addView(dialogItem);
        }

        return view;
    }

    protected abstract void updateViews(View root);

    protected abstract CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root);
}
