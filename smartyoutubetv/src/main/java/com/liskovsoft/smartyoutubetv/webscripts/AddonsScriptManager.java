package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class AddonsScriptManager implements ScriptManager {
    private final Context mContext;

    public AddonsScriptManager(Context context) {
        mContext = context;
    }

    @Override
    public InputStream getOnInitScripts() {
        return mergeJSAddons(ADDONS_INIT_DIR);
    }

    @Override
    public InputStream getOnLoadScripts() {
        return mergeJSAddons(ADDONS_LOAD_DIR);
    }

    @Override
    public InputStream getStyles() {
        InputStream initStyles = AssetHelper.getAssetCSSFilesMerged(mContext, ADDONS_INIT_DIR);
        InputStream loadStyles = AssetHelper.getAssetCSSFilesMerged(mContext, ADDONS_LOAD_DIR);
        return Helpers.appendStream(initStyles, loadStyles);
    }

    /**
     * Wraps every addon with anonymous function<br/>
     * This could help to isolate addon's code.
     * @param root dir with addons
     * @return merged addons
     */
    private InputStream mergeJSAddons(String root) {
        List<String> dirs = AssetHelper.getAssetDirs(mContext, root);

        InputStream result = null;

        for (String dir : dirs) {
            InputStream asset = ScriptManager.anonAssetJSFiles(mContext, dir);
            result = result == null ? asset : FileHelpers.appendStream(result, asset);
        }

        return result;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
