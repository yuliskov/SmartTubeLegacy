package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class AnimatedPreviewsDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public AnimatedPreviewsDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_animated_previews), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableAnimatedPreviews();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableAnimatedPreviews(checked);
    }
}
