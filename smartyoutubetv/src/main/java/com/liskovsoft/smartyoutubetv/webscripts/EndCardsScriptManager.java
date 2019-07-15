package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

public class EndCardsScriptManager implements ScriptManager {
    private final Context mContext;
    private final boolean mIsEnabled;

    public EndCardsScriptManager(Context context) {
        mContext = context;
        mIsEnabled = CommonApplication.getPreferences().getEnableEndCards();
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
        return AssetHelper.getAssetCSSFilesMerged(mContext, CORE_ENDCARDS_DIR);
    }

    @Override
    public boolean isEnabled() {
        return mIsEnabled;
    }
}
