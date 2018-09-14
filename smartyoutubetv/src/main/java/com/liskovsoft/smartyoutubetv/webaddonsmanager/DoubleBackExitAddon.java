package com.liskovsoft.smartyoutubetv.webaddonsmanager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DoubleBackExitAddon implements WebAddon {
    private final String[] mJSAddons = {
            "addons/double_back_exit.js"
    };
    @Override
    public List<String> getCSSList() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getJSList() {
        return Arrays.asList(mJSAddons);
    }
}
