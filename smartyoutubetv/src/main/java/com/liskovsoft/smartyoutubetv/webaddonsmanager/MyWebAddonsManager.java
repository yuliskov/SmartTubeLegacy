package com.liskovsoft.smartyoutubetv.webaddonsmanager;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.SmartPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyWebAddonsManager implements WebAddonsManager {
    private final List<WebAddon> mAddons = new ArrayList<>();

    public MyWebAddonsManager(Context ctx) {
        mAddons.add(new NoEndCardsAddon(ctx));
        mAddons.add(new DoubleBackExitAddon());
    }

    public List<String> getCSSAddons() {
        List<String> result = new ArrayList<>();
        for (WebAddon addon : mAddons) {
            result.addAll(addon.getCSSList());
        }

        return result;
    }

    public List<String> getJSAddons() {
        List<String> result = new ArrayList<>();
        for (WebAddon addon : mAddons) {
            result.addAll(addon.getJSList());
        }

        return result;
    }
}
