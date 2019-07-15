package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;

import java.io.InputStream;

public class CommonScriptManager implements ScriptManager {
    private final Context mContext;

    public CommonScriptManager(Context context) {
        mContext = context;
    }

    @Override
    public InputStream getOnInitScripts() {
        return AssetHelper.getAssetJSFilesMerged(mContext, CORE_COMMON_DIR);
    }

    @Override
    public InputStream getOnLoadScripts() {
        return null;
    }

    @Override
    public InputStream getStyles() {
        return AssetHelper.getAssetCSSFilesMerged(mContext, CORE_COMMON_DIR);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
