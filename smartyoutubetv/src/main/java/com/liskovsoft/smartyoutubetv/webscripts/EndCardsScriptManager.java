package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

public class EndCardsScriptManager implements ScriptManager {
    private final Context mContext;
    private final boolean isEnabled;

    public EndCardsScriptManager(Context context) {
        mContext = context;
        SmartPreferences mPrefs = SmartPreferences.instance(context);
        isEnabled = mPrefs.getEnableEndCards();
    }

    @Override
    public InputStream getOnInitScripts() {
        return null;
    }

    @Override
    public InputStream getOnLoadScripts() {
        return null;
    }

    @Override
    public InputStream getStyles() {
        if (!isEnabled) {
            return null;
        }
        return AssetHelper.getAssetCSSFilesMerged(mContext, CORE_ENDCARDS_DIR);
    }
}
