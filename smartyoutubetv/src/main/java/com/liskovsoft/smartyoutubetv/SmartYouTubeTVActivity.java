package com.liskovsoft.smartyoutubetv;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.liskovsoft.browser.BrowserActivity;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.util.PageDefaults;
import com.liskovsoft.browser.util.SimpleUIController;
import com.liskovsoft.browser.util.PageLoadHandler;

import java.util.HashMap;
import java.util.Map;

public class SmartYouTubeTVActivity extends BrowserActivity {
    private Controller mController;
    private final String mYouTubeTVUrl = "https://youtube.com/tv";
    private final String mLGSmartTVUserAgent = "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)";
    private Map<String, String> mHeaders;
    private PageLoadHandler mPageLoadHandler;
    private PageDefaults mPageDefaults;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
    }

    @Override
    protected Controller createAndInitController(Bundle icicle) {
        mHeaders = new HashMap<>();
        mPageLoadHandler = new MyPageLoadHandler(this);
        mHeaders.put("user-agent", mLGSmartTVUserAgent);

        mController = new SimpleUIController(this);
        Intent intent = (icicle == null) ? getIntent() : null;
        mPageDefaults = new PageDefaults(mYouTubeTVUrl, mHeaders, mPageLoadHandler);
        mController.start(intent, mPageDefaults);
        //mController.loadUrl(mYouTubeTVUrl, mHeaders, mPageLoadHandler);
        return mController;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event = doTranslateKeys(event);
        return super.onKeyDown(event.getKeyCode(), event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        event = doTranslateKeys(event);
        return super.onKeyUp(event.getKeyCode(), event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private KeyEvent doTranslateKeys(KeyEvent event) {
        event = translateBackToEscape(event);
        event = translateMenuToGuide(event);
        return event;
    }

    private KeyEvent translateBackToEscape(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ESCAPE);
        }
        return event;
    }

    private KeyEvent translateMenuToGuide(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_G);
        }
        return event;
    }
}
