package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.sharedutils.prefs.GlobalPreferences;
import com.liskovsoft.smartyoutubetv.R;

public class ATVPublishRecommendedDialogItem extends DialogItem {
    private final GlobalPreferences mPrefs;
    private final Context mContext;

    public ATVPublishRecommendedDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_publish_recommended_playlist), false);

        mContext = context;
        mPrefs = GlobalPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return GlobalPreferences.PLAYLIST_TYPE_RECOMMENDATIONS.equals(mPrefs.getRecommendedPlaylistType());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setRecommendedPlaylistType(GlobalPreferences.PLAYLIST_TYPE_RECOMMENDATIONS);
        } else {
            mPrefs.setRecommendedPlaylistType(GlobalPreferences.PLAYLIST_TYPE_NONE);
        }
    }
}
