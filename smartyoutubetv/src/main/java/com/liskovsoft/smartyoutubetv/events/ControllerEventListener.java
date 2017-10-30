package com.liskovsoft.smartyoutubetv.events;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.injectors.MyJsCssTweaksInjector;
import com.liskovsoft.smartyoutubetv.injectors.MyWebViewClientDecorator;
import com.liskovsoft.smartyoutubetv.injectors.WebViewJavaScriptInterface;
import com.liskovsoft.smartyoutubetv.misc.KeysTranslator;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.misc.StateUpdater;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser.VideoFormatInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.injectors.DecipherRoutineInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.injectors.GenericEventResourceInjector;
import edu.mit.mobile.android.appupdater.AppUpdateChecker;
import edu.mit.mobile.android.appupdater.OnUpdateDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerEventListener implements Controller.EventListener {
    private final Context mContext;
    private final KeysTranslator mTranslator;
    private final LoadingManager mLoadingManager;
    private WebViewJavaScriptInterface mJS;
    private MyJsCssTweaksInjector mInjector;
    private VideoFormatInjector mNotification;
    private DecipherRoutineInjector mDecipherRoutineInjector;
    private static final Logger logger = LoggerFactory.getLogger(ControllerEventListener.class);
    private GenericEventResourceInjector mEventResourceInjector;
    private final LangUpdater mLangUpdater;
    private final StateUpdater mStateUpdater;

    public ControllerEventListener(Context context, KeysTranslator translator) {
        mContext = context;
        mTranslator = translator;
        mLangUpdater = new LangUpdater(mContext);
        mStateUpdater = new StateUpdater(null);
        mLoadingManager = new LoadingManager(context);
    }

    @Override
    public WebViewClient onSetWebViewClient(Tab tab, WebViewClient client) {
        return new MyWebViewClientDecorator(client, mContext);
    }

    @Override
    public WebChromeClient onSetWebChromeClient(Tab tab, WebChromeClient client) {
        return null;
    }

    @Override
    public void onPageFinished(Tab tab) {
        logger.info("onPageFinished called");
        WebView w = tab.getWebView();
        injectWebFiles(w);
    }

    @Override
    public void onPageStarted(Tab tab) {
        logger.info("onPageStarted called");
        // js must be added before page fully loaded
        addJSInterface(tab);
    }

    @Override
    public void onReceiveError(Tab tab) {
        logger.info("onReceiveError called");
    }

    @Override
    public void onLoadSuccess(Tab tab) {
        mTranslator.enable();
        mLoadingManager.hide();
        checkForUpdatesAfterDelay();
    }

    @Override
    public void onTabCreated(Tab tab) {
        mLoadingManager.setTab(tab);
        mLoadingManager.show();
    }

    @Override
    public void onControllerStart() {
        mLangUpdater.update();

        // if you need to disable auto-saving webview state:
        // mController.getCrashRecoveryHandler().pauseState();
    }

    @Override
    public void onSaveControllerState(Bundle state) {
        // mStateUpdater.fixPlaylistUrl(state);
    }

    @Override
    public void onRestoreControllerState(Bundle state) {
        mStateUpdater.updateState(state);
    }

    private void addJSInterface(Tab tab) {
        if (mJS != null) {
            return;
        }

        mJS = new WebViewJavaScriptInterface(mContext, tab);
        WebView webView = tab.getWebView();
        webView.addJavascriptInterface(mJS, "app");
    }

    private void injectWebFiles(WebView w) {
        if (mInjector == null)
            mInjector = new MyJsCssTweaksInjector(mContext, w);
        if (mNotification == null)
            mNotification = new VideoFormatInjector(mContext, w);
        if (mDecipherRoutineInjector == null)
            mDecipherRoutineInjector = new DecipherRoutineInjector(mContext, w);
        if (mEventResourceInjector == null)
            mEventResourceInjector = new GenericEventResourceInjector(mContext, w);

        mInjector.inject();
    }

    private void checkForUpdatesAfterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkForUpdates();
            }
        }, 3000);
    }

    private void checkForUpdates() {
        final String sUpdateUrl = "https://drive.google.com/uc?id=0ByORA7yiJiQXSGFqUURSUTlmVWc";
        OnUpdateDialog dialog = new OnUpdateDialog(mContext, mContext.getString(R.string.app_name));
        AppUpdateChecker updateChecker = new AppUpdateChecker(mContext, sUpdateUrl, dialog);
        updateChecker.forceCheckForUpdates();
    }
    private class LoadingManager {

        private final View mLoadingWidget;
        private FrameLayout mWrapper;

        public LoadingManager(Context ctx) {
            LayoutInflater li = LayoutInflater.from(ctx);
            mLoadingWidget = li.inflate(R.layout.loading_main, null);
        }

        public void setTab(Tab tab) {
            View container = tab.getViewContainer();
            if (container == null) {
                return;
            }
            mWrapper = (FrameLayout) container.findViewById(com.liskovsoft.browser.R.id.webview_wrapper);
            mWrapper.removeView(mLoadingWidget);
            mWrapper.addView(mLoadingWidget);
        }

        public void show() {
            mLoadingWidget.setVisibility(View.VISIBLE);
        }

        public void hide() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoadingWidget.setVisibility(View.GONE);
                }
            }, 500);
        }
    }
}
