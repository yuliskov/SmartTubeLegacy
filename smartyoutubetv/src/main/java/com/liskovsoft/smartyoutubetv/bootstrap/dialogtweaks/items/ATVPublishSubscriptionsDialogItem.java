package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.sharedutils.prefs.GlobalPreferences;
import com.liskovsoft.smartyoutubetv.R;

public class ATVPublishSubscriptionsDialogItem extends DialogItem {
    private final GlobalPreferences mPrefs;
    private final Context mContext;

    public ATVPublishSubscriptionsDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_publish_subscriptions_playlist), false);

        mContext = context;
        mPrefs = GlobalPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return GlobalPreferences.PLAYLIST_TYPE_SUBSCRIPTIONS.equals(mPrefs.getRecommendedPlaylistType());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setRecommendedPlaylistType(GlobalPreferences.PLAYLIST_TYPE_SUBSCRIPTIONS);
        } else {
            mPrefs.setRecommendedPlaylistType(GlobalPreferences.PLAYLIST_TYPE_NONE);
        }
    }
}
