package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class OpenMusicDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public OpenMusicDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_open_music), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getOpenMusic();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setOpenMusic(checked);
    }
}
