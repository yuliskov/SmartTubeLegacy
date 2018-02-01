package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapSelectorDialog.DataSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;

import java.util.LinkedHashMap;
import java.util.Map;

public class CodecDataSource implements DataSource {
    private final ExoPreferences mPrefs;

    public CodecDataSource(Context ctx) {
        mPrefs = ExoPreferences.instance(ctx);
    }

    @Override
    public Map<String, String> getDialogItems() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Default", "");
        map.put("AVC", "avc");
        map.put("VP9", "vp9");
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
        return "Select Codec";
    }
}
