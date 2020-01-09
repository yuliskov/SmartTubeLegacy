package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class UseExternalPlayerKodiDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public UseExternalPlayerKodiDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_open_externally) + " (KODI)", false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.USE_EXTERNAL_PLAYER_KODI.equals(mPrefs.getUseExternalPlayer());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setUseExternalPlayer(checked ? SmartPreferences.USE_EXTERNAL_PLAYER_KODI : SmartPreferences.USE_EXTERNAL_PLAYER_NONE);
    }
}
