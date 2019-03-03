package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

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
        return SmartPreferences.MUSIC_PAGE.equals(mPrefs.getBootPage());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setBootPage(checked ? SmartPreferences.MUSIC_PAGE : SmartPreferences.DEFAULT_PAGE);
    }
}
