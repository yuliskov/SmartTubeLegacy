package com.liskovsoft.smartyoutubetv.webaddons;

import java.io.InputStream;

public interface ScriptManager {
    InputStream getOnInitScripts();
    InputStream getOnLoadScripts();
    InputStream getStyles();
}
