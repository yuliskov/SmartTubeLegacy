package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import android.content.Context;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DataSource;

import java.util.LinkedHashMap;
import java.util.Map;

public class RestrictCodecDataSource implements DataSource {
    private final ExoPreferences mPrefs;
    private final Context mContext;

    public RestrictCodecDataSource(Context ctx) {
        mContext = ctx;
        mPrefs = ExoPreferences.instance(ctx);
    }

    @Override
    public Map<String, String> getDialogItems() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(mContext.getString(R.string.no_restrictions), "");
        map.put("4K     60fps    vp9", "2160|60|vp9");
        map.put("4K     30fps    vp9", "2160|30|vp9");
        map.put("FHD    60fps    avc", "1080|60|avc");
        map.put("FHD    60fps    vp9", "1080|60|vp9");
        map.put("FHD    30fps    avc", "1080|30|avc");
        map.put("FHD    30fps    vp9", "1080|30|vp9");
        map.put("FHD    60fps    avc+vp9", "1080|60|");
        map.put("FHD    30fps    avc+vp9", "1080|30|");
        return map;
    }

    @Override
    public String getSelected() {
        return mPrefs.getPreferredCodec();
    }

    @Override
    public void setSelected(String codec) {
        mPrefs.setPreferredCodec(codec);
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.select_preferred_codec);
    }
}
