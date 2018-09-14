package com.liskovsoft.smartyoutubetv.webaddonsmanager;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.SmartPreferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoEndCardsAddon implements WebAddon {
    private final SmartPreferences mPrefs;
    private final String[] mCSSAddons = {
            "addons/no_endcards.css"
    };

    public NoEndCardsAddon(Context ctx) {
        mPrefs = SmartPreferences.instance(ctx);
    }

    @Override
    public List<String> getCSSList() {
        if (mPrefs.getEnableEndCards())
            return Collections.emptyList();
        return Arrays.asList(mCSSAddons);
    }

    @Override
    public List<String> getJSList() {
        return Collections.emptyList();
    }
}
