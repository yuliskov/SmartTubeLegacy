package com.liskovsoft.smartyoutubetv.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.custom.ControllerEventHandler;
import com.liskovsoft.browser.custom.PageDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LangDetector implements ControllerEventHandler {
    private Context mContext;
    private Controller mController;
    private String[] rusPackages = {"dkc.androidtv.tree", "dkc.video.fsbox", "dkc.video.hdbox", "dkc.video.uatv"};

    public LangDetector(Controller controller) {
        mController = controller;
        mContext = controller.getContext();
    }

    @Override
    public void afterStart() {
        if (updateHeaders(mController.getPageDefaults())){
            // to keep russian (non system lang) we must not keep a state
            mController.getCrashRecoveryHandler().pauseState();
        }
    }

    private boolean updateHeaders(PageDefaults pageDefaults) {
        if (pageDefaults == null) {
            return false;
        }
        List<String> installedPackages = getListInstalledPackages();
        for (String pkgName : installedPackages) {
            if (isRussianPackage(pkgName)) {
                addRussianHeaders(pageDefaults);
                return true;
            }
        }
        return false;
    }

    private void addRussianHeaders(PageDefaults pageDefaults) {
        Map<String, String> headers = pageDefaults.getHeaders();
        headers.put("Accept-Language", "ru,en-US;q=0.8,en;q=0.6");
    }

    private boolean isRussianPackage(String pkgName) {
        for (String rusPackage : rusPackages) {
            if (rusPackage.equals(pkgName)){
                return true;
            }
        }
        return false;
    }

    private List<String> getListInstalledPackages() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = mContext.getPackageManager().queryIntentActivities( mainIntent, 0);
        List<String> result = new ArrayList<>();
        for (ResolveInfo info : pkgAppsList) {
            result.add(info.activityInfo.packageName);
        }
        return result;
    }
}
