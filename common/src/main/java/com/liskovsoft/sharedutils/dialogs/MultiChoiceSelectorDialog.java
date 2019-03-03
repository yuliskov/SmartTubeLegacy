package com.liskovsoft.sharedutils.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.common.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

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

    protected CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root, DialogItem item) {
        return (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_multi, root, false);
    }

    @Override
    public void onClick(View view) {
        DialogItem item = (DialogItem) view.getTag();
        CheckedTextView textView = (CheckedTextView) view;

        textView.setChecked(!textView.isChecked());

        if (item.getChecked() != textView.isChecked()) {
            item.setChecked(textView.isChecked());

            updateViews(getRoot());
        }
    }
}
