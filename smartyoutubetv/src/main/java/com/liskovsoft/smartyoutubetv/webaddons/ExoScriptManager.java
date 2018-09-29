package com.liskovsoft.smartyoutubetv.webaddons;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.io.InputStream;
import java.util.List;

public class ExoScriptManager implements ScriptManager {
    private static final String CORE_FOLDER = "core";
    private final Context mContext;
    private List<String> mOnLoadScripts;
    private List<String> mStyles;

    public ExoScriptManager(Context context) {
        mContext = context;

        if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase) {
            mOnLoadScripts = Helpers.listAssetFiles(context, CORE_FOLDER, "exoplayer.js");
            mStyles = Helpers.listAssetFiles(context, CORE_FOLDER, "exoplayer.css");
        }
    }

    @Override
    public InputStream getOnInitScripts() {
        return null;
    }

    @Override
    public InputStream getOnLoadScripts() {
        return Helpers.getSequenceResourceStream(mContext, mOnLoadScripts);
    }

    @Override
    public InputStream getStyles() {
        return Helpers.getSequenceResourceStream(mContext, mStyles);
    }
}
