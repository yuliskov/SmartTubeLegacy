package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;

import java.io.InputStream;
import java.util.List;

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
        return Helpers.getAssetCSSFilesMerged(mContext, CORE_ENDCARDS_DIR);
    }
}
