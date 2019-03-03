package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.zoom;

import android.content.Context;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.SingleDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.VideoZoomManager;

import java.util.ArrayList;
import java.util.List;

public class VideoZoomDialogSource implements SingleDialogSource {
    private final VideoZoomManager mManager;
    private final Context mContext;
    private final List<DialogItem> mItems;

    public VideoZoomDialogSource(Context ctx, VideoZoomManager manager) {
        mContext = ctx;
        mManager = manager;

        mItems = new ArrayList<>();
        mItems.add(new VideoZoomDialogItem(mContext.getString(R.string.option_zoom_default), VideoZoomManager.MODE_DEFAULT, mManager));
        mItems.add(new VideoZoomDialogItem(mContext.getString(R.string.option_zoom_fit_width), VideoZoomManager.MODE_FIT_WIDTH, mManager));
        mItems.add(new VideoZoomDialogItem(mContext.getString(R.string.option_zoom_fit_height), VideoZoomManager.MODE_FIT_HEIGHT, mManager));
        mItems.add(new VideoZoomDialogItem(mContext.getString(R.string.option_zoom_fit_both), VideoZoomManager.MODE_FIT_BOTH, mManager));
        mItems.add(new VideoZoomDialogItem(mContext.getString(R.string.option_zoom_stretch), VideoZoomManager.MODE_STRETCH, mManager));
    }

    @Override
    public List<DialogItem> getItems() {
        return mItems;
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.dialog_video_zoom);
    }
}
