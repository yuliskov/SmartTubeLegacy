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

        Resources res = mContext.getResources();
        SmartPreferences prefs = SmartPreferences.instance(mContext);

        mItems = new ArrayList<>();
        mItems.add(new SaveSelectionDialogItem(res.getString(R.string.tweak_remember_launcher), prefs));
        mItems.add(new UpdateCheckDialogItem(res.getString(R.string.tweak_update_check), prefs));
        mItems.add(new OkButtonDialogItem(res.getString(R.string.tweak_ok_button_fix), prefs));
        mItems.add(new EndCardsDialogItem(res.getString(R.string.tweak_end_cards), prefs));
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
