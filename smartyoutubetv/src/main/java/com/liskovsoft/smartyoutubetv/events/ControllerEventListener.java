package com.liskovsoft.smartyoutubetv.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
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
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff.injectors.DecipherRoutineInjector;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff.injectors.GenericEventResourceInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerEventListener implements Controller.EventListener {
    private final Context mContext;
    private final KeysTranslator mTranslator;
    //private final LoadingManager mLoadingManager;
    private WebViewJavaScriptInterface mJS;
    private MyJsCssTweaksInjector mInjector;
    private VideoFormatInjector mNotification;
    private DecipherRoutineInjector mDecipherRoutineInjector;
    private static final Logger logger = LoggerFactory.getLogger(ControllerEventListener.class);
    private GenericEventResourceInjector mEventResourceInjector;
    private final LangUpdater mLangUpdater;
    private final StateUpdater mStateUpdater;
    private int mCounter1;
    private int mCounter2;
    private int mCounter3;

    public ControllerEventListener(Context context, KeysTranslator translator) {
        mContext = context;
        mTranslator = translator;
        mLangUpdater = new LangUpdater(mContext);
        mStateUpdater = new StateUpdater(null);
        //mLoadingManager = new LoadingManager(context);
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
        Toast.makeText(mContext, "onReceiveError" + mCounter1++, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadSuccess(Tab tab) {
        mTranslator.enable();
        Toast.makeText(mContext, "onLoadSuccess" + mCounter2++, Toast.LENGTH_LONG).show();

        //mLoadingManager.hide();
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

    @Override
    public void onTabCreated(Tab tab) {
        //mLoadingManager.setTab(tab);
        //mLoadingManager.show();

        Toast.makeText(mContext, "onTabCreated" + mCounter3++, Toast.LENGTH_LONG).show();
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

    private class LoadingManager {
        private final View mLoadingWidget;
        private FrameLayout mWrapper;

        public LoadingManager(Context ctx) {
            LayoutInflater li = LayoutInflater.from(ctx);
            mLoadingWidget = li.inflate(R.layout.loading_main, null);
        }

        public void setTab(Tab tab) {
            View container = tab.getViewContainer();
            mWrapper = (FrameLayout) container.findViewById(com.liskovsoft.browser.R.id.webview_wrapper);
        }

        public void show() {
            if (mWrapper == null) {
                return;
            }

            mWrapper.addView(mLoadingWidget, 0);
        }

        public void hide() {
            if (mWrapper == null) {
                return;
            }

            mWrapper.removeView(mLoadingWidget);
        }
    }
}
