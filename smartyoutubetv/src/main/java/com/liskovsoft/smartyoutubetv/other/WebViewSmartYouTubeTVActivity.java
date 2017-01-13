package com.liskovsoft.smartyoutubetv.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.liskovsoft.browser.BrowserActivity;
import com.liskovsoft.browser.Controller;

public class WebViewSmartYouTubeTVActivity extends BrowserActivity {
    private SmartYouTubeTVController mController;

    public WebViewSmartYouTubeTVActivity() {
        mController = new SmartYouTubeTVController(this);
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
    }

    @Override
    protected void initController(Bundle icicle) {
        initController(icicle);
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
