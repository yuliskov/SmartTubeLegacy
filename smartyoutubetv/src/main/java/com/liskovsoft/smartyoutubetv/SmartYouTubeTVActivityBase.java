package com.liskovsoft.smartyoutubetv;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.addons.MainBrowserActivity;
import com.liskovsoft.browser.addons.SimpleUIController;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;
import com.liskovsoft.smartyoutubetv.events.ControllerEventListener;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.liskovsoft.smartyoutubetv.misc.KeysTranslator;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import edu.mit.mobile.android.appupdater.addons.PermissionManager;

import java.util.HashMap;
import java.util.Map;

public class SmartYouTubeTVActivityBase extends MainBrowserActivity {
    private static final String TAG = SmartYouTubeTVActivityBase.class.getSimpleName();
    private Controller mController;
    private String mServiceUrl; // youtube url here
    private Map<String, String> mHeaders;
    private KeysTranslator mTranslator;
    private final static String DIAL_EXTRA = "com.amazon.extra.DIAL_PARAM";
    private final static String mLGSmartTVUserAgent = "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)";
    private final static String TEMPLATE_URL = "https://www.youtube.com/tv#?%s";

    @Override
    protected void onCreate(Bundle icicle) {
        setupLang();
        super.onCreate(icicle);

        initRemoteUrl();
        initKeys();
        initPermissions();

        // clearCache();

        createController(icicle);

        makeActivityFullscreen();
        makeActivityHorizontal();
    }

    private void initPermissions() {
        PermissionManager.verifyStoragePermissions(this);
    }

    private void initKeys() {
        mTranslator = new KeysTranslator();
    }

    private void initRemoteUrl() {
        mServiceUrl = getString(R.string.service_url);
    }

    private void setupLang() {
        new LangUpdater(this).update();
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
        mHeaders.put("user-agent", mLGSmartTVUserAgent);

        mController = new SimpleUIController(this);
        mController.setEventListener(new ControllerEventListener(this, mTranslator));
        mController.setDefaultUrl(Uri.parse(mServiceUrl));
        mController.setDefaultHeaders(mHeaders);
        Intent intent = (icicle == null) ? transformIntentData(getIntent()) : null;
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
        super.onNewIntent(transformIntentData(intent));
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
        intent.putExtra(BootstrapActivity.SKIP_RESTORE, true);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionManager.REQUEST_EXTERNAL_STORAGE) {
            // Check if the only required permission has been granted
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Toast.makeText(this, "REQUEST_EXTERNAL_STORAGE permission has been granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Unable to grant REQUEST_EXTERNAL_STORAGE permission", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    ///////////////////////// Begin Youtube filter /////////////////////


    private Intent transformIntentData(Intent intent) {
        if (intent == null)
            return null;

        transformRegularIntentData(intent);
        transformAmazonIntentData(intent);

        return intent;
    }

    private void transformRegularIntentData(Intent intent) {
        Uri data = intent.getData();
        if (data == null) {
            return;
        }

        intent.setData(transformUri(data));
    }

    // see Amazon's youtube apk: "org.chromium.youtube_apk.YouTubeActivity.loadStartPage(dialParam)"
    private void transformAmazonIntentData(Intent intent) {
        String dialParam = intent.getStringExtra(DIAL_EXTRA);
        if (dialParam == null) {
            return;
        }

        String uriString = String.format(TEMPLATE_URL, dialParam);
        intent.setData(Uri.parse(uriString));
    }

    /**
     * Extracts video params e.g. <code>v=xtx33RuFCik</code> from url
     * <br/>
     * Examples of the input/output url:
     * <pre>
     * origin video: https://www.youtube.com/watch?v=xtx33RuFCik
     * needed video: https://www.youtube.com/tv#/watch/video/control?v=xtx33RuFCik
     * needed video: https://www.youtube.com/tv?gl=us&hl=en-us&v=xtx33RuFCik
     * needed video: https://www.youtube.com/tv?v=xtx33RuFCik
     *
     * origin playlist: https://www.youtube.com/playlist?list=PLbl01QFpbBY1XGwNb8SBmoA3hshpK1pZj
     * needed playlist: https://www.youtube.com/tv#/watch/video/control?list=PLbl01QFpbBY1XGwNb8SBmoA3hshpK1pZj&resume
     * </pre>
     * @param url desktop url (see manifest file for the patterns)
     * @return video params
     */
    private String extractVideoParamsFromUrl(String url) {
        String[] patterns = {"list=\\w*", "v=\\w*", "youtu.be/\\w*"};
        String res = Helpers.runMultiMatcher(url, patterns);
        if (res == null) {
            Log.w(TAG, "Url not supported: " + url);
            // Uncomment next section to debug
            // Toast.makeText(this, "Url not supported: " + url, Toast.LENGTH_LONG).show();
            return null;
        }
        return res.replace("youtu.be/", "v=");
    }

    private Uri transformUri(final Uri uri) {
        if (uri == null)
            return null;
        String url = uri.toString();
        String videoParam = extractVideoParamsFromUrl(url);
        if (videoParam == null) {
            return Uri.parse(mServiceUrl);
        }
        String format = String.format(TEMPLATE_URL, videoParam);
        return Uri.parse(format);
    }

    ///////////////////////// End Youtube filter /////////////////////
}
