package com.liskovsoft.smartyoutubetv.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.common.R;

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
    protected CheckedTextView createDialogItem(LayoutInflater inflater, ViewGroup root) {
        return (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_single, root, false);
    }

    @Override
    protected void updateViews(View root) {
        CheckedTextView view = root.findViewWithTag(mDialogSource.getSelectedItemTag());

        if (view != null) {
            view.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (!tag.equals(mDialogSource.getSelectedItemTag())) {
            mDialogSource.setSelectedItemByTag(tag);

            // close dialog
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }
}
