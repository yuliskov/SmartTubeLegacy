package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import android.content.res.Resources;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.MultiDialogSource;

import java.util.ArrayList;
import java.util.List;

public class TweaksDialogSource implements MultiDialogSource {
    private final Context mContext;
    private final List<DialogItem> mItems;

    public TweaksDialogSource(Context context) {
        mContext = context;

        mItems = new ArrayList<>();
        mItems.add(new SaveSelectionDialogItem(mContext));
        mItems.add(new UpdateCheckDialogItem(mContext));
        mItems.add(new OkButtonDialogItem(mContext));
        mItems.add(new EndCardsDialogItem(mContext));
        mItems.add(new LogToFileDialogItem(mContext));
    }

    @Override
    public List<DialogItem> getItems() {
        return mItems;
    }

    @Override
    public String getTitle() {
        return mContext.getResources().getString(R.string.tweaks);
    }
}
