package com.liskovsoft.sharedutils.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.common.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class CombinedChoiceSelectorDialog extends GenericSelectorDialog {
    private final CombinedDialogSource mDialogSource;

    public CombinedChoiceSelectorDialog(Context activity, CombinedDialogSource dataSource) {
        super(activity, dataSource);

        mDialogSource = dataSource;
    }

    public static void create(Context ctx, CombinedDialogSource dataSource) {
        GenericSelectorDialog dialog = new CombinedChoiceSelectorDialog(ctx, dataSource);
        dialog.run();
    }

    @Override
    protected CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root, DialogItem item) {
        switch (getItemType(item)) {
            case SINGLE_CHOICE:
                return (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_single, root, false);
            case MULTI_CHOICE:
                return (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_multi, root, false);
        }

        throw new IllegalStateException("Incorrect DialogItem supplied");
    }

    @Override
    public void onClick(View view) {
        DialogItem item = (DialogItem) view.getTag();
        CheckedTextView textView = (CheckedTextView) view;

        switch (getItemType(item)) {
            case SINGLE_CHOICE:
                textView.setChecked(true);
                break;
            case MULTI_CHOICE:
                textView.setChecked(!textView.isChecked());
                break;
        }

        if (item.getChecked() != textView.isChecked()) { // prevent user for click on same item twice
            item.setChecked(textView.isChecked());

            updateViews(getRoot());
        }
    }
}
