package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.GenericSelectorDialog.DataSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;

import java.util.LinkedHashMap;
import java.util.Map;

public class CodecDataSource implements DataSource {
    private final ExoPreferences mPrefs;
    private final Context mContext;

    public CodecDataSource(Context ctx) {
        mContext = ctx;
        mPrefs = ExoPreferences.instance(ctx);
    }

    @Override
    public Map<String, String> getDialogItems() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(mContext.getString(R.string.no_restrictions), "");
        map.put("AVC, 2160p", "avc|2160");
        map.put("AVC, 1440p", "avc|1440");
        map.put("AVC, 1080p", "avc|1080");
        map.put("AVC, 720p", "avc|720");
        map.put("VP9, 2160p", "vp9|2160");
        map.put("VP9, 1440p", "vp9|1440");
        map.put("VP9, 1080p", "vp9|1080");
        map.put("VP9, 720p", "vp9|720");
        map.put("AVC, VP9, 2160p", "|2160");
        map.put("AVC, VP9, 1440p", "|1440");
        map.put("AVC, VP9, 1080p", "|1080");
        map.put("AVC, VP9, 720p", "|720");
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
    public void apply() {
        // NOP
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.select_preferred_codec);
    }
}
