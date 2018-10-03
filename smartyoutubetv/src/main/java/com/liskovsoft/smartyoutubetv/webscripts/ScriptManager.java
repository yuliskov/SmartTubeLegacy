package com.liskovsoft.smartyoutubetv.webscripts;

import java.io.InputStream;

public interface ScriptManager {
    String ADDONS_ROOT_DIR = "addons";
    String ADDONS_INIT_DIR = ADDONS_ROOT_DIR + "/" + "on_init";
    String ADDONS_LOAD_DIR = ADDONS_ROOT_DIR + "/" + "on_load";
    String CORE_ROOT_DIR = "core";
    String CORE_COMMON_DIR = CORE_ROOT_DIR + "/" + "common";
    String CORE_EXO_DIR = CORE_ROOT_DIR + "/" + "exoplayer";
    String CORE_ENDCARDS_DIR = CORE_ROOT_DIR + "/" + "endcards";
    InputStream getOnInitScripts();
    InputStream getOnLoadScripts();
    InputStream getStyles();
}
