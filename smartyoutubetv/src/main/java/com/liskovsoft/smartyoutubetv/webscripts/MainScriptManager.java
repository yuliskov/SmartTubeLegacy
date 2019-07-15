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

        ScriptManager[] managers = {
                new CommonScriptManager(context),
                new AddonsScriptManager(context),
                new ExoScriptManager(context),
                new EndCardsScriptManager(context)
        };

        for (ScriptManager manager : managers) {
            if (manager.isEnabled()) {
                mManagers.add(manager);
            }
        }
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

    @Override
    public boolean isEnabled() {
        return true;
    }

    protected List<ScriptManager> getManagers() {
        return mManagers;
    }
}
