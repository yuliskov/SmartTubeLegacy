package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.restrictcodec;

import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

public class RestrictFormatDialogItem extends DialogItem {
    private final ExoPreferences mPrefs;
    private final String mTag;

    public RestrictFormatDialogItem(String title, String tag, ExoPreferences prefs) {
        super(title, false);

        mTag = tag;
        mPrefs = prefs;
    }

    @Override
    public boolean getChecked() {
        return mTag.equals(mPrefs.getPreferredFormat());
    }

    @Override
    public void setChecked(boolean checked) {
        if (!checked) {
            return;
        }

        mPrefs.setPreferredFormat(mTag);
    }
}
