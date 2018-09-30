package com.liskovsoft.smartyoutubetv.webscripts;

import java.io.InputStream;

public interface ScriptManager {
    String ROOT_DIR = "scripts";
    String ON_INIT_DIR = ROOT_DIR + "/" + "on_init";
    String ON_LOAD_DIR = ROOT_DIR + "/" + "on_load";
    String CORE_DIR = ROOT_DIR + "/" + "core";
    InputStream getOnInitScripts();
    InputStream getOnLoadScripts();
    InputStream getStyles();
}
