package com.liskovsoft.sharedutils.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.common.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class SingleChoiceSelectorDialog extends GenericSelectorDialog {
    private final SingleDialogSource mDialogSource;

    public SingleChoiceSelectorDialog(Context activity, SingleDialogSource dataSource) {
        super(activity, dataSource);

        mDialogSource = dataSource;
    }

    public static void create(Context ctx, SingleDialogSource dataSource) {
        GenericSelectorDialog dialog = new SingleChoiceSelectorDialog(ctx, dataSource);
        dialog.run();
    }

    @Override
    protected CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root, DialogItem item) {
        return (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_single, root, false);
    }

    @Override
    public void onClick(View view) {
        DialogItem item = (DialogItem) view.getTag();
        CheckedTextView textView = (CheckedTextView) view;

        textView.setChecked(true);

        if (item.getChecked() != textView.isChecked()) { // prevent user for click on same item twice
            item.setChecked(textView.isChecked());

            updateViews(getRoot());
        }
    }
}
