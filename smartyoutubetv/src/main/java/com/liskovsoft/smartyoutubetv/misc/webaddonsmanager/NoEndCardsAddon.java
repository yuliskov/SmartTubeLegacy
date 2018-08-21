package com.liskovsoft.smartyoutubetv.misc.webaddonsmanager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoEndCardsAddon implements WebAddon {
    private final String[] mCSSAddons = {
            "addons/no_endcards.css"
    };
    @Override
    public List<String> getCSSList() {
        return Arrays.asList(mCSSAddons);
    }

    @Override
    public List<String> getJSList() {
        return Collections.emptyList();
    }
}
