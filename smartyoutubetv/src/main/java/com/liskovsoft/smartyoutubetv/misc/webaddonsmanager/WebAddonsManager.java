package com.liskovsoft.smartyoutubetv.misc.webaddonsmanager;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.misc.SmartPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebAddonsManager {
    private final List<WebAddon> mAddons = new ArrayList<>();
    private final SmartPreferences mPrefs;

    public WebAddonsManager(Context ctx) {
        mPrefs = SmartPreferences.instance(ctx);
        mAddons.add(new NoEndCardsAddon());
    }

    public List<String> getCSSAddons() {
        if (mPrefs.getEnableEndCards())
            return Collections.emptyList();

        List<String> result = new ArrayList<>();
        for (WebAddon addon : mAddons) {
            result.addAll(addon.getCSSList());
        }

        return result;
    }

    public List<String> getJSAddons() {
        if (mPrefs.getEnableEndCards())
            return Collections.emptyList();

        List<String> result = new ArrayList<>();
        for (WebAddon addon : mAddons) {
            result.addAll(addon.getJSList());
        }

        return result;
    }
}
