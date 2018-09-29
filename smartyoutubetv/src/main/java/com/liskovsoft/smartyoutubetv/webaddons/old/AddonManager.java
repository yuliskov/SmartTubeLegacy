package com.liskovsoft.smartyoutubetv.webaddons.old;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AddonManager implements WebAddon {
    private final List<WebAddon> mAddons = new ArrayList<>();
    private final ScriptsAddon mScripts;

    public AddonManager(Context ctx) {
        mAddons.add(new NoEndCardsAddon(ctx));
        mScripts = new ScriptsAddon(ctx);
    }

    public List<String> getCSSList() {
        List<String> result = new ArrayList<>();
        for (WebAddon addon : mAddons) {
            result.addAll(addon.getCSSList());
        }

        result.addAll(mScripts.getCSSList());

        return result;
    }

    public List<String> getJSList() {
        List<String> result = new ArrayList<>();
        for (WebAddon addon : mAddons) {
            result.addAll(addon.getJSList());
        }

        result.addAll(mScripts.getJSList());

        return result;
    }
}
