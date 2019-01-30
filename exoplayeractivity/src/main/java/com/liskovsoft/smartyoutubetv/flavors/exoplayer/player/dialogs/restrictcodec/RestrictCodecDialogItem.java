package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.restrictcodec;

import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;

public class RestrictCodecDialogItem extends DialogItem {
    private final ExoPreferences mPrefs;
    private final String mTag;

    public RestrictCodecDialogItem(String title, String tag, ExoPreferences prefs) {
        super(title, false);

        mTag = tag;
        mPrefs = prefs;
    }

    @Override
    public boolean getChecked() {
        return mTag.equals(mPrefs.getPreferredCodec());
    }

    @Override
    public void setChecked(boolean checked) {
        if (!checked) {
            return;
        }

        mPrefs.setPreferredCodec(mTag);
    }
}
