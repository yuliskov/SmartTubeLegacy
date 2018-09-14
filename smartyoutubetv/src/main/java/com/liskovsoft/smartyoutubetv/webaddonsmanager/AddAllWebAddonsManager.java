package com.liskovsoft.smartyoutubetv.webaddonsmanager;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto import all addons from 'addons' folder
 */
public class AddAllWebAddonsManager implements WebAddonsManager {
    private final Context mContext;
    private final static String ADDON_FOLDER = "addons";
    private final List<String> mList;
    private final List<String> mCssList;
    private final List<String> mJsList;

    public AddAllWebAddonsManager(Context context) {
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
    public List<String> getCSSAddons() {
        return mCssList;
    }

    @Override
    public List<String> getJSAddons() {
        return mJsList;
    }
}
