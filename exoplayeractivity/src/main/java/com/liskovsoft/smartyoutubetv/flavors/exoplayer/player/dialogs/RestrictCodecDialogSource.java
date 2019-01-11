package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import android.content.Context;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.SingleDialogSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestrictCodecDialogSource implements SingleDialogSource {
    private final ExoPreferences mPrefs;
    private final Context mContext;

    public RestrictCodecDialogSource(Context ctx) {
        mContext = ctx;
        mPrefs = ExoPreferences.instance(ctx);
    }

    @Override
    public List<DialogItem> getItems() {
        List<DialogItem> map = new ArrayList<>();
        map.add(DialogItem.create(mContext.getString(R.string.no_restrictions), ""));
        map.add(DialogItem.create("4K     60fps    vp9", "2160|60|vp9"));
        map.add(DialogItem.create("4K     30fps    vp9", "2160|30|vp9"));
        map.add(DialogItem.create("FHD    60fps    avc", "1080|60|avc"));
        map.add(DialogItem.create("FHD    60fps    vp9", "1080|60|vp9"));
        map.add(DialogItem.create("FHD    30fps    avc", "1080|30|avc"));
        map.add(DialogItem.create("FHD    30fps    vp9", "1080|30|vp9"));
        map.add(DialogItem.create("FHD    60fps    avc+vp9", "1080|60|"));
        map.add(DialogItem.create("FHD    30fps    avc+vp9", "1080|30|"));
        return map;
    }

    @Override
    public Object getSelectedItemTag() {
        return mPrefs.getPreferredCodec();
    }

    @Override
    public void setSelectedItemByTag(Object codec) {
        mPrefs.setPreferredCodec((String) codec);
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.select_preferred_codec);
    }
}
