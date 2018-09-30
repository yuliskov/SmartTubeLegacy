package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class UserScriptManager implements ScriptManager {
    private final Context mContext;
    private final List<String> mOnInitScripts;
    private final List<String> mOnLoadScripts;
    private final List<String> mOnInitStyles;
    private final List<String> mOnLoadStyles;

    public UserScriptManager(Context context) {
        mContext = context;
        mOnInitScripts = Helpers.getAssetFiles(mContext, ON_INIT_DIR, ".js");
        mOnLoadScripts = Helpers.getAssetFiles(mContext, ON_LOAD_DIR, ".js");
        mOnInitStyles = Helpers.getAssetFiles(mContext, ON_INIT_DIR, ".css");
        mOnLoadStyles = Helpers.getAssetFiles(mContext, ON_LOAD_DIR, ".css");
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
        InputStream initStyles = Helpers.getAssetMerged(mContext, mOnInitStyles);
        InputStream loadStyles = Helpers.getAssetMerged(mContext, mOnLoadStyles);
        return Helpers.appendStream(initStyles, loadStyles);
    }
}
