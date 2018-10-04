package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class AddonsScriptManager implements ScriptManager {
    private final Context mContext;

    public AddonsScriptManager(Context context) {
        mContext = context;
    }

    @Override
    public InputStream getOnInitScripts() {
        return Helpers.getAssetJSFilesMerged(mContext, ADDONS_INIT_DIR);
    }

    @Override
    public InputStream getOnLoadScripts() {
        return Helpers.getAssetJSFilesMerged(mContext, ADDONS_LOAD_DIR);
    }

    @Override
    public InputStream getStyles() {
        InputStream initStyles = Helpers.getAssetCSSFilesMerged(mContext, ADDONS_INIT_DIR);
        InputStream loadStyles = Helpers.getAssetCSSFilesMerged(mContext, ADDONS_LOAD_DIR);
        return Helpers.appendStream(initStyles, loadStyles);
    }
}
