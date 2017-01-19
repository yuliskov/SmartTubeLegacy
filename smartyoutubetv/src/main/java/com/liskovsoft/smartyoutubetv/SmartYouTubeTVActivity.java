package com.liskovsoft.smartyoutubetv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import com.crashlytics.android.Crashlytics;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.util.PageDefaults;
import com.liskovsoft.browser.util.SimpleUIController;
import com.liskovsoft.browser.util.PageLoadHandler;
import com.liskovsoft.browser.util.WebViewBrowserActivity;
import com.liskovsoft.browser.xwalk.XWalkBrowserActivity;
import com.liskovsoft.smartyoutubetv.helpers.LangDetector;
import com.liskovsoft.smartyoutubetv.injectors.MyPageLoadHandler;
import io.fabric.sdk.android.Fabric;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartYouTubeTVActivity extends XWalkBrowserActivity {
    private Controller mController;
    private final String mYouTubeTVUrl = "https://youtube.com/tv";
    private final String mLGSmartTVUserAgent = "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)";
    private Map<String, String> mHeaders;
    private PageLoadHandler mPageLoadHandler;
    private PageDefaults mPageDefaults;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Fabric.with(this, new Crashlytics());

        setTheme(com.liskovsoft.browser.R.style.SimpleUITheme);
    }

    @Override
    protected void initController(Bundle icicle) {
        mHeaders = new HashMap<>();
        mPageLoadHandler = new MyPageLoadHandler(this);
        mHeaders.put("user-agent", mLGSmartTVUserAgent);

        mController = new SimpleUIController(this, new LangDetector());
        Intent intent = (icicle == null) ? transformIntent(getIntent()) : null;
        mPageDefaults = new PageDefaults(mYouTubeTVUrl, mHeaders, mPageLoadHandler);
        mController.start(intent, mPageDefaults);
        setController(mController);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        event = doTranslateKeys(event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(transformIntent(intent));
    }

    private boolean mDownFired;
    private boolean isEventIgnored(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mDownFired = true;
            return false;
        }
        if (event.getAction() == KeyEvent.ACTION_UP && mDownFired) {
            mDownFired = false;
            return false;
        }

        return true;
    }

    private KeyEvent doTranslateKeys(KeyEvent event) {
        if (isEventIgnored(event)) {
            return new KeyEvent(0, 0);
        }

        event = translateBackToEscape(event);
        event = translateMenuToGuide(event);
        return event;
    }

    private KeyEvent translateBackToEscape(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_ESCAPE);
        }
        return event;
    }

    private KeyEvent translateMenuToGuide(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_G);
        }
        return event;
    }

    ///////////////////////// Begin Youtube filter /////////////////////


    private Intent transformIntent(Intent intent) {
        if (intent == null)
            return null;
        Uri data = intent.getData();
        intent.setData(transformUri(data));
        return intent;
    }

    private String runMultiMatcher(String input, String[] patterns) {
        Pattern regex;
        Matcher matcher;
        String result = null;
        for (String pattern : patterns) {
            regex = Pattern.compile(pattern);
            matcher = regex.matcher(input);

            if (matcher.find()) {
                result = matcher.group(1);
                break;
            }
        }

        return result;
    }

    private String extractVideoIdFromUrl(String url) {
        // https://www.youtube.com/watch?v=xtx33RuFCik
        String[] patterns = {"v=(\\w*)", "/(\\w*)$"};
        return runMultiMatcher(url, patterns);
    }

    private Uri transformUri(Uri uri) {
        if (uri == null)
            return null;
        String url = uri.toString();
        String videoId = extractVideoIdFromUrl(url);
        String videoUrlTemplate = "https://www.youtube.com/tv#/watch/video/control?v=%s";
        String format = String.format(videoUrlTemplate, videoId);
        return Uri.parse(format);
    }

    ///////////////////////// End Youtube filter /////////////////////
}
