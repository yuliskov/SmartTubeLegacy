package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class UserScriptManager implements ScriptManager {
    private final Context mContext;
    private final List<String> mOnInitScripts;
    private final List<String> mOnLoadScripts;
    private final List<String> mStyles;

    public UserScriptManager(Context context) {
        mContext = context;
        mOnInitScripts = Helpers.getAssetFiles(mContext, ON_INIT_DIR, ".js");
        mOnLoadScripts = Helpers.getAssetFiles(mContext, ON_LOAD_DIR, ".js");
        mStyles = Helpers.getAssetFiles(mContext, ROOT_DIR, ".css");
    }

    @Override
    public InputStream getOnInitScripts() {
        return Helpers.getAsset(mContext, mOnInitScripts);
    }

    @Override
    public InputStream getOnLoadScripts() {
        return Helpers.getAsset(mContext, mOnLoadScripts);
    }

    @Override
    public InputStream getStyles() {
        return Helpers.getAsset(mContext, mStyles);
    }
}
