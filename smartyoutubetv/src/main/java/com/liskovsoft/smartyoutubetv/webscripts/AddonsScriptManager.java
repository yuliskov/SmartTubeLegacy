package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class AddonsScriptManager implements ScriptManager {
    private final Context mContext;
    private final List<String> mOnInitScripts;
    private final List<String> mOnLoadScripts;
    private final List<String> mStyles;

    public AddonsScriptManager(Context context) {
        mContext = context;
        mOnInitScripts = Helpers.getAssetFiles(mContext, ADDONS_INIT_DIR, ".js");
        mOnLoadScripts = Helpers.getAssetFiles(mContext, ADDONS_LOAD_DIR, ".js");
        mStyles = Helpers.getAssetFiles(mContext, ADDONS_STYLES_DIR, ".css");
    }

    @Override
    public InputStream getOnInitScripts() {
        return Helpers.getAssetMerged(mContext, mOnInitScripts);
    }

    @Override
    public InputStream getOnLoadScripts() {
        return Helpers.getAssetMerged(mContext, mOnLoadScripts);
    }

    @Override
    public InputStream getStyles() {
        return Helpers.getAssetMerged(mContext, mStyles);
    }
}
