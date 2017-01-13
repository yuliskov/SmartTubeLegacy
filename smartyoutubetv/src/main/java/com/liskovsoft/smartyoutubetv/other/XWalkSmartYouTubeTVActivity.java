package com.liskovsoft.smartyoutubetv.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.xwalk.XWalkBrowserActivity_TEMP;

public class XWalkSmartYouTubeTVActivity extends XWalkBrowserActivity_TEMP {
    private SmartYouTubeTVController mController;

    public XWalkSmartYouTubeTVActivity() {
        mController = new SmartYouTubeTVController(this);
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
    }

    @Override
    protected Controller createAndInitController(Bundle icicle) {
        return mController.createAndInitController(icicle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event = mController.doTranslateKeys(event);
        keyCode = mController.doTranslateKeys(keyCode);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        event = mController.doTranslateKeys(event);
        keyCode = mController.doTranslateKeys(keyCode);
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Intent newIntent = mController.transformIntent(intent);
        super.onNewIntent(newIntent);
    }
}
