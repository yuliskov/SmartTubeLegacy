package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.sharedutils.helpers.Helpers;

import java.io.InputStream;

public class AddonsScriptManager implements ScriptManager {
    private final Context mContext;

    public AddonsScriptManager(Context context) {
        mContext = context;
    }

    @Override
    public InputStream getOnInitScripts() {
        return AssetHelper.getAssetJSFilesMerged(mContext, ADDONS_INIT_DIR);
    }

    @Override
    public InputStream getOnLoadScripts() {
        return AssetHelper.getAssetJSFilesMerged(mContext, ADDONS_LOAD_DIR);
    }

    @Override
    public InputStream getStyles() {
        InputStream initStyles = AssetHelper.getAssetCSSFilesMerged(mContext, ADDONS_INIT_DIR);
        InputStream loadStyles = AssetHelper.getAssetCSSFilesMerged(mContext, ADDONS_LOAD_DIR);
        return Helpers.appendStream(initStyles, loadStyles);
    }
}
