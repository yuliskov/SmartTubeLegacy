package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.afr;

import android.content.Context;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.CombinedDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

import java.util.ArrayList;
import java.util.List;

public class AfrDialogSource implements CombinedDialogSource {
    private final Context mContext;
    private final SimpleExoPlayer mPlayer;
    private final ArrayList<DialogItem> mItems;
    private final ExoPreferences mPrefs;

    public AfrDialogSource(ExoPlayerFragment playerFragment) {
        mContext = playerFragment.getActivity();
        mPlayer = playerFragment.getPlayer();
        mPrefs = ExoPreferences.instance(mContext);

        mItems = new ArrayList<>();
        mItems.add(new EnableAfrDialogItem(playerFragment));
        mItems.add(new EnablePauseAfterSwitchDialogItem(playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("0.5 sec", 500, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("1 sec", 1_000, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("1.5 sec", 1_500, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("2 sec", 2_000, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("2.5 sec", 2_500, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("3 sec", 3_000, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("3.5 sec", 3_500, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("4 sec", 4_000, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("4.5 sec", 4_500, playerFragment));
        mItems.add(new PauseAfterSwitchDialogItem("5 sec", 5_000, playerFragment));
    }

    @Override
    public List<DialogItem> getItems() {
        return mItems;
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.enable_autoframerate);
    }
}
