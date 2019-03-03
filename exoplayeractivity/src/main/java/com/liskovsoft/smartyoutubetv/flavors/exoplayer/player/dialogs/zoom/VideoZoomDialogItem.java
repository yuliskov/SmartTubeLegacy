package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.zoom;

import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.VideoZoomManager;

public class VideoZoomDialogItem extends DialogItem {
    private final int mZoomMode;
    private final VideoZoomManager mManager;

    public VideoZoomDialogItem(String title, int zoomMode, VideoZoomManager manager) {
        super(title, false);

        mZoomMode = zoomMode;
        mManager = manager;
    }

    @Override
    public boolean getChecked() {
        return mZoomMode == mManager.getZoomMode();
    }

    @Override
    public void setChecked(boolean checked) {
        if (!checked) {
            return;
        }

        mManager.setZoomMode(mZoomMode);
    }
}
