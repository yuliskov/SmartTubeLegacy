package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainScriptManager implements ScriptManager {
    private final Context mContext;
    private final List<ScriptManager> mManagers = new ArrayList<>();

    public MainScriptManager(Context context) {
        mContext = context;
        mManagers.add(new CommonScriptManager(context));
        mManagers.add(new AddonsScriptManager(context));
        mManagers.add(new ExoScriptManager(context));
        mManagers.add(new EndCardsScriptManager(context));
    }

    @Override
    public InputStream getOnInitScripts() {
        InputStream is = null;
        for (ScriptManager manager : mManagers) {
            is = Helpers.appendStream(is, manager.getOnInitScripts());
        }
        return is;
    }

    @Override
    public InputStream getOnLoadScripts() {
        InputStream is = null;
        for (ScriptManager manager : mManagers) {
            is = Helpers.appendStream(is, manager.getOnLoadScripts());
        }
        return is;
    }

    @Override
    public InputStream getStyles() {
        InputStream is = null;
        for (ScriptManager manager : mManagers) {
            is = Helpers.appendStream(is, manager.getStyles());
        }
        return is;
    }
}
