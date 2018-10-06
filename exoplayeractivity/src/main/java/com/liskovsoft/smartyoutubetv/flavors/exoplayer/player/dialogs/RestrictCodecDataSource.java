package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import android.content.Context;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.GenericSelectorDialog.DataSource;

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
        map.put("AVC, 4K", "avc|2160");
        map.put("AVC, 1080p", "avc|1080");
        map.put("VP9, 4K", "vp9|2160");
        map.put("VP9, 1080p", "vp9|1080");
        map.put("AVC+VP9, 4K", "|2160");
        map.put("AVC+VP9, 1080p", "|1080");
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
