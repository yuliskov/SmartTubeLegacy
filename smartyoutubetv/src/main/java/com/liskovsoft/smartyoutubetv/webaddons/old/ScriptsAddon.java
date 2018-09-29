package com.liskovsoft.smartyoutubetv.webaddons.old;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.webaddons.old.WebAddon;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto import all addons from 'addons' folder
 */
public class ScriptsAddon implements WebAddon {
    private final Context mContext;
    private final static String ADDON_FOLDER = "scripts";
    private final List<String> mList;
    private final List<String> mCssList;
    private final List<String> mJsList;

    public ScriptsAddon(Context context) {
        mContext = context;
        mList = Helpers.listAssetFiles(mContext, ADDON_FOLDER);
        mCssList = extractCssList();
        mJsList = extractJsList();
    }

    private List<String> extractJsList() {
        List<String> result = new ArrayList<>();
        for (String file : mList) {
            if (file.endsWith(".js")) {
                result.add(file);
            }
        }
        return result;
    }

    private List<String> extractCssList() {
        List<String> result = new ArrayList<>();
        for (String file : mList) {
            if (file.endsWith(".css")) {
                result.add(file);
            }
        }
        return result;
    }

    @Override
    public List<String> getCSSList() {
        return mCssList;
    }

    @Override
    public List<String> getJSList() {
        return mJsList;
    }
}
