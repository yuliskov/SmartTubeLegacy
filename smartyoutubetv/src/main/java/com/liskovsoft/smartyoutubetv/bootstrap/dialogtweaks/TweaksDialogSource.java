package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.MultiDialogSource;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ATVPublishHistoryDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ATVPublishRecommendedDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ATVPublishSubscriptionsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.AnimatedPreviewsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.AnimatedUIDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.HighContrastModeDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LongPressVideoMenuDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProAltPlayerMappingDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProAutoShowPlayerUIDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.BackPressExitDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProOKPauseWithoutUIDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProOpenLinksInSimplePlayerDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProHighPlayerBufferTypeDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.EnableAdBlockDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProFixAspectDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LiteForceAVCDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LiteForceAllCodecsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LiteForceVP9DialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.HideBootTipsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LockLastLauncherDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.LogToFileDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProLowPlayerBufferTypeDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProMediumPlayerBufferTypeDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProOkPauseDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenHistoryDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenMusicDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenSubscriptionsDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.OpenWatchLaterDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProPlayerUIShowTimeoutDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.SaveSelectionDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProUgoos50HZFixDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.UpdateCheckDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProUseExternalPlayer4KDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProUseExternalPlayerFHDDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.ProUseExternalPlayerSDDialogItem;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items.WebProxyDialogItem;

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
        //mItems.add(new EndCardsDialogItem(mContext));
        mItems.add(new LogToFileDialogItem(mContext));
        //mItems.add(new UnplayableVideoFixDialogItem(mContext));
        mItems.add(new EnableAdBlockDialogItem(mContext));
        mItems.add(new AnimatedPreviewsDialogItem(mContext));
        mItems.add(new HighContrastModeDialogItem(mContext));
        mItems.add(new LongPressVideoMenuDialogItem(mContext));
        mItems.add(new AnimatedUIDialogItem(mContext));
        mItems.add(new LockLastLauncherDialogItem(mContext));
        mItems.add(new OpenMusicDialogItem(mContext));
        mItems.add(new OpenSubscriptionsDialogItem(mContext));
        mItems.add(new OpenHistoryDialogItem(mContext));
        mItems.add(new OpenWatchLaterDialogItem(mContext));
        //mItems.add(new GlobalAfrFixDialogItem(mContext));
        //mItems.add(new AnimatedPreviewsDialogItem(mContext));
        mItems.add(new BackPressExitDialogItem(mContext));
        mItems.add(new HideBootTipsDialogItem(mContext));
        //mItems.add(new UseExternalPlayerKodiDialogItem(mContext));
        mItems.add(new ProOpenLinksInSimplePlayerDialogItem(mContext));
        mItems.add(new ProLowPlayerBufferTypeDialogItem(mContext));
        mItems.add(new ProMediumPlayerBufferTypeDialogItem(mContext));
        mItems.add(new ProHighPlayerBufferTypeDialogItem(mContext));
        mItems.add(new ProFixAspectDialogItem(mContext));
        mItems.add(new ProUseExternalPlayer4KDialogItem(mContext));
        mItems.add(new ProUseExternalPlayerFHDDialogItem(mContext));
        mItems.add(new ProUseExternalPlayerSDDialogItem(mContext));
        //mItems.add(new ProUgoos50HZFixDialogItem(mContext));
        mItems.add(new ProAutoShowPlayerUIDialogItem(mContext));
        mItems.add(new ProOkPauseDialogItem(mContext));
        mItems.add(new ProOKPauseWithoutUIDialogItem(mContext));
        mItems.add(new ProPlayerUIShowTimeoutDialogItem(mContext));
        mItems.add(new ProAltPlayerMappingDialogItem(mContext));
        mItems.add(new LiteForceAllCodecsDialogItem(mContext));
        mItems.add(new LiteForceAVCDialogItem(mContext));
        mItems.add(new LiteForceVP9DialogItem(mContext));
        mItems.add(new ATVPublishHistoryDialogItem(mContext));
        mItems.add(new ATVPublishRecommendedDialogItem(mContext));
        mItems.add(new ATVPublishSubscriptionsDialogItem(mContext));
        mItems.add(new WebProxyDialogItem(mContext));
    }

    @Override
    public List<DialogItem> getItems() {
        return mItems;
    }

    @Override
    public String getTitle() {
        return mContext.getResources().getString(R.string.tweaks);
    }

    @Override
    public void onDismiss() {
        // NOP
    }
}
