package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.BetaUpdateCheckDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.EndCardsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.FixAspectDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.GlobalAfrFixDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LockLastLauncherDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LogToFileDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OkButtonDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UseExternalPlayerDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenMusicDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenSubscriptionsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenWatchLaterDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.SaveSelectionDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UnplayableVideoFixDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UpdateCheckDialogItem;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.MultiDialogSource;

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
        mItems.add(new BetaUpdateCheckDialogItem(mContext));
        mItems.add(new OkButtonDialogItem(mContext));
        mItems.add(new EndCardsDialogItem(mContext));
        mItems.add(new LogToFileDialogItem(mContext));
        mItems.add(new UnplayableVideoFixDialogItem(mContext));
        mItems.add(new LockLastLauncherDialogItem(mContext));
        mItems.add(new OpenMusicDialogItem(mContext));
        mItems.add(new OpenSubscriptionsDialogItem(mContext));
        mItems.add(new OpenWatchLaterDialogItem(mContext));
        mItems.add(new GlobalAfrFixDialogItem(mContext));
        mItems.add(new UseExternalPlayerDialogItem(mContext));
        mItems.add(new FixAspectDialogItem(mContext));
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
