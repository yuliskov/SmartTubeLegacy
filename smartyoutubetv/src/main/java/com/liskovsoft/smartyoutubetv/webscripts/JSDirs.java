package com.liskovsoft.smartyoutubetv.webscripts;

import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager.UAVersion;

public class JSDirs {
    public static String ADDONS_ROOT_DIR;
    public static String CORE_ROOT_DIR;
    public static String ADDONS_INIT_DIR;
    public static String ADDONS_LOAD_DIR;
    public static String CORE_COMMON_DIR;
    public static String CORE_EXO_DIR;
    public static String CORE_ENDCARDS_DIR;

    static {
        UAVersion uaVersion = new UserAgentManager().getUAVersion();
        String prefix = "";

        if (uaVersion == UAVersion.V1) {
            prefix = "V1/";
        } else if (uaVersion == UAVersion.V2) {
            prefix = "V2/";
        }

        ADDONS_ROOT_DIR = prefix + "addons";
        CORE_ROOT_DIR = prefix + "core";
        ADDONS_INIT_DIR = ADDONS_ROOT_DIR + "/" + "on_init";
        ADDONS_LOAD_DIR = ADDONS_ROOT_DIR + "/" + "on_load";
        CORE_COMMON_DIR = CORE_ROOT_DIR + "/" + "common";
        CORE_EXO_DIR = CORE_ROOT_DIR + "/" + "exoplayer";
        CORE_ENDCARDS_DIR = CORE_ROOT_DIR + "/" + "endcards";
    }
}
