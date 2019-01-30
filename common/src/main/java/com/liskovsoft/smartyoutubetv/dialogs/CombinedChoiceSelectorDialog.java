package com.liskovsoft.smartyoutubetv.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.common.R;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

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
    protected CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root) {
        return (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_single, root, false);
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
