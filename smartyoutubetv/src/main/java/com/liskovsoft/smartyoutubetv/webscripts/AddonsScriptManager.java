package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class AddonsScriptManager implements ScriptManager {
    private final Context mContext;
    private static final String HEADER_FN_JS = "(function(){\n";
    private static final String FOOTER_FN_JS = "\n})();";

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
            InputStream asset = AssetHelper.getAssetJSFilesMerged(mContext, dir); // code for single addon
            asset = FileHelpers.appendStream(Helpers.toStream(HEADER_FN_JS), asset);
            asset = FileHelpers.appendStream(asset, Helpers.toStream(FOOTER_FN_JS));

            result = result == null ? asset : FileHelpers.appendStream(result, asset);
        }

        return result;
    }
}
