package com.liskovsoft.smartyoutubetv;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.custom.MainBrowserActivity;
import com.liskovsoft.browser.custom.SimpleUIController;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;
import com.liskovsoft.smartyoutubetv.events.ControllerEventListener;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.liskovsoft.smartyoutubetv.misc.KeysTranslator;
import edu.mit.mobile.android.appupdater.AppUpdateChecker;
import edu.mit.mobile.android.appupdater.OnUpdateDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartYouTubeTVActivityBase extends MainBrowserActivity {
    private Controller mController;
    private final String mYouTubeTVUrl = "https://youtube.com/tv";
    private final String mLGSmartTVUserAgent = "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)";
    private Map<String, String> mHeaders;
    private KeysTranslator mTranslator;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        mTranslator = new KeysTranslator();

        // clearCache();

        createController(icicle);

        makeActivityFullscreen();
        makeActivityHorizontal();
    }

    private void checkForUpdates() {
        final String sUpdateUrl = "https://drive.google.com/uc?id=0ByORA7yiJiQXSGFqUURSUTlmVWc";
        OnUpdateDialog dialog = new OnUpdateDialog(this, getString(R.string.app_name));
        AppUpdateChecker updateChecker = new AppUpdateChecker(this, sUpdateUrl, dialog);
        updateChecker.forceCheckForUpdates();
    }

    /**
     * WebView likes to cache js. So this is prevents my changes from applying.
     */
    private void clearCache() {
        deleteDatabase("webview.db");
        deleteDatabase("webviewCache.db");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void createController(Bundle icicle) {
        mHeaders = new HashMap<>();
        //TODO: remove
        //mPageLoadHandler = new MyPageLoadHandler(this);
        mHeaders.put("user-agent", mLGSmartTVUserAgent);

        mController = new SimpleUIController(this);
        mController.setEventListener(new ControllerEventListener(this, mTranslator));
        mController.setDefaultUrl(Uri.parse(mYouTubeTVUrl));
        mController.setDefaultHeaders(mHeaders);
        Intent intent = (icicle == null) ? transformIntent(getIntent()) : null;
        // TODO: remove
        //mPageDefaults = new PageDefaults(mYouTubeTVUrl, mHeaders, mPageLoadHandler, new MyControllerEventHandler(mController));
        mController.start(intent);
        setController(mController);
    }

    private void makeActivityFullscreen() {
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        if (VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void makeActivityHorizontal() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        event = mTranslator.doTranslateKeys(event);
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return translateMouseWheelToArrowKeys(event);
    }

    private boolean translateMouseWheelToArrowKeys(MotionEvent event) {
        if (0 != (event.getSource() & InputDevice.SOURCE_CLASS_POINTER)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_SCROLL:
                    fakeHorizontalScroll(event);
                    fakeVerticalScroll(event);
                    return false;
            }
        }
        return super.dispatchGenericMotionEvent(event);
    }

    private void fakeVerticalScroll(MotionEvent event) {
        if (Helpers.floatEquals(event.getAxisValue(MotionEvent.AXIS_VSCROLL), 0.0f)) {
            return;
        }
        KeyEvent keyEvent = null;
        if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f)
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN);
        else
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP);
        dispatchKeyEvent(keyEvent);
    }

    private void fakeHorizontalScroll(MotionEvent event) {
        if (Helpers.floatEquals(event.getAxisValue(MotionEvent.AXIS_HSCROLL), 0.0f)) {
            return;
        }
        KeyEvent keyEvent = null;
        if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) < 0.0f)
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT);
        else
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT);
        dispatchKeyEvent(keyEvent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(transformIntent(intent));
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        returnToLaunchersDialog();
        super.onBackPressed();
    }

    private void returnToLaunchersDialog() {
        Intent intent = new Intent();
        intent.setClass(this, BootstrapActivity.class);
        intent.putExtra(BootstrapActivity.DO_NOT_RESTORE, true);
        startActivity(intent);
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
