package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.MultiDialogSource;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.AltPlayerMappingDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.AutoShowPlayerUIDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.BackPressExitDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.BetaUpdateCheckDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ChannelsCloseAppDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LowerPlayerBufferTypeDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.EnableAdBlockDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.FixAspectDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ForceAVCDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ForceAllCodecsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ForceVP9DialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.HideBootTipsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LockLastLauncherDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LogToFileDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OkButtonDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenMusicDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenSubscriptionsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenWatchLaterDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.PlayerUIShowTimeoutDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.SaveSelectionDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.Ugoos50HZFixDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UpdateCheckDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UseExternalPlayer4KDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UseExternalPlayerFHDDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UseExternalPlayerSDDialogItem;

import java.util.ArrayList;
import java.util.List;

public class TweaksDialogSource implements MultiDialogSource {
    private final Context mContext;
    private final List<DialogItem> mItems;

    public TweaksDialogSource(Context context) {
        mContext = context;

        mItems = new ArrayList<>();
        //mItems.add(new UseNewUIDialogItem(mContext));
        mItems.add(new SaveSelectionDialogItem(mContext));
        mItems.add(new UpdateCheckDialogItem(mContext));
        mItems.add(new BetaUpdateCheckDialogItem(mContext));
        mItems.add(new OkButtonDialogItem(mContext));
        //mItems.add(new EndCardsDialogItem(mContext));
        mItems.add(new LogToFileDialogItem(mContext));
        //mItems.add(new UnplayableVideoFixDialogItem(mContext));
        mItems.add(new LockLastLauncherDialogItem(mContext));
        mItems.add(new OpenMusicDialogItem(mContext));
        mItems.add(new OpenSubscriptionsDialogItem(mContext));
        mItems.add(new OpenWatchLaterDialogItem(mContext));
        //mItems.add(new GlobalAfrFixDialogItem(mContext));
        //mItems.add(new AnimatedPreviewsDialogItem(mContext));
        mItems.add(new BackPressExitDialogItem(mContext));
        mItems.add(new ForceAllCodecsDialogItem(mContext));
        mItems.add(new ForceAVCDialogItem(mContext));
        mItems.add(new ForceVP9DialogItem(mContext));
        mItems.add(new HideBootTipsDialogItem(mContext));
        mItems.add(new AutoShowPlayerUIDialogItem(mContext));
        //mItems.add(new UseExternalPlayerKodiDialogItem(mContext));
        mItems.add(new UseExternalPlayer4KDialogItem(mContext));
        mItems.add(new UseExternalPlayerFHDDialogItem(mContext));
        mItems.add(new UseExternalPlayerSDDialogItem(mContext));
        mItems.add(new Ugoos50HZFixDialogItem(mContext));
        mItems.add(new FixAspectDialogItem(mContext));
        mItems.add(new AltPlayerMappingDialogItem(mContext));
        mItems.add(new EnableAdBlockDialogItem(mContext));
        mItems.add(new PlayerUIShowTimeoutDialogItem(mContext));
        mItems.add(new ChannelsCloseAppDialogItem(mContext));
        mItems.add(new LowerPlayerBufferTypeDialogItem(mContext));
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
