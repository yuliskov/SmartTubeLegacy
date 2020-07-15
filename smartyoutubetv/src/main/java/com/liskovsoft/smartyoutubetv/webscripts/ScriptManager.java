package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;

import java.io.InputStream;

public interface ScriptManager {
    String HEADER_FN_JS = "(function(){\n";
    String FOOTER_FN_JS = "\n})();\n";
    InputStream getOnInitScripts();
    InputStream getOnLoadScripts();
    InputStream getStyles();
    boolean isEnabled();

    static InputStream anonAssetJSFiles(Context context, String dir) {
        InputStream asset = AssetHelper.getAssetJSFilesMerged(context, dir); // code for single addon
        asset = FileHelpers.appendStream(Helpers.toStream(HEADER_FN_JS), asset);
        asset = FileHelpers.appendStream(asset, Helpers.toStream(FOOTER_FN_JS));
        return asset;
    }
}
