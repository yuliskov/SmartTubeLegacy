package com.liskovsoft.smartyoutubetv.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.common.R;

import java.util.List;

public class MultiChoiceSelectorDialog extends GenericSelectorDialog {
    private final MultiDialogSource mDialogSource;

    public MultiChoiceSelectorDialog(Context activity, MultiDialogSource dialogSource) {
        super(activity, dialogSource);

        mDialogSource = dialogSource;
    }

    public static void create(Context ctx, MultiDialogSource dataSource) {
        GenericSelectorDialog dialog = new MultiChoiceSelectorDialog(ctx, dataSource);
        dialog.run();
    }

    protected CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root) {
        return (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_multi, root, false);
    }

    @Override
    protected void updateViews(View root) {
        List<Object> tags = mDialogSource.getSelectedItemsTags();
        for (Object tag : tags) {
            CheckedTextView view = root.findViewWithTag(tag);

            if (view != null) {
                view.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        CheckedTextView textView = (CheckedTextView) view;

        if (textView.isChecked()) {
            mDialogSource.setUnselectedItemByTag(tag);
        } else {
            mDialogSource.setSelectedItemByTag(tag);
        }
    }
}
