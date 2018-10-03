package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class CommonScriptManager implements ScriptManager {
    private final Context mContext;
    private final List<String> mOnInitScripts;

    public CommonScriptManager(Context context) {
        mContext = context;
        mOnInitScripts = Helpers.getAssetJSFiles(mContext, CORE_COMMON_DIR);
    }

    @Override
    public InputStream getOnInitScripts() {
        return Helpers.getAssetMerged(mContext, mOnInitScripts);
    }

    @Override
    public InputStream getOnLoadScripts() {
        return null;
    }

    @Override
    public InputStream getStyles() {
        return null;
    }
}
