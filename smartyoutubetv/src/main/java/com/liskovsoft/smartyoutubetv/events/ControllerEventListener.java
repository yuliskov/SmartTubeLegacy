package com.liskovsoft.smartyoutubetv.events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.DecipherRoutineInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector;
import com.liskovsoft.smartyoutubetv.injectors.WebViewJavaScriptInterface;
import com.liskovsoft.smartyoutubetv.interceptors.MainRequestInterceptor;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.KeysTranslator;
import com.liskovsoft.smartyoutubetv.misc.MainApkUpdater;
import com.liskovsoft.smartyoutubetv.misc.MyCookieSaver;
import com.liskovsoft.smartyoutubetv.misc.StateUpdater;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.VideoFormatInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerEventListener implements Controller.EventListener, Tab.EventListener {
    private static final Logger logger = LoggerFactory.getLogger(ControllerEventListener.class);
    private static final String JS_INTERFACE_NAME = "app";
    private final Context mContext;
    private final KeysTranslator mTranslator;
    private final LoadingManager mLoadingManager;
    private final WebViewJavaScriptInterface mJSInterface;
    private final VideoFormatInjector mFormatInjector;
    private final DecipherRoutineInjector mDecipherRoutineInjector;
    private final GenericEventResourceInjector mEventResourceInjector;
    private final StateUpdater mStateUpdater;
    private final MainApkUpdater mApkUpdater;
    private final Controller mController;
    private final RequestInterceptor mInterceptor;

    public ControllerEventListener(Context context, Controller controller, KeysTranslator translator) {
        mContext = context;
        mController = controller;
        mTranslator = translator;
        mStateUpdater = new StateUpdater(null, context);
        mLoadingManager = new LoadingManager(context);
        mApkUpdater = new MainApkUpdater(context);

        mFormatInjector = new VideoFormatInjector(mContext);
        mDecipherRoutineInjector = new DecipherRoutineInjector(mContext);
        mEventResourceInjector = new GenericEventResourceInjector(mContext);
        mJSInterface = new WebViewJavaScriptInterface(mContext);
        mInterceptor = new MainRequestInterceptor(mContext);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(Tab tab, WebResourceRequest request) {
        String url = request.getUrl().toString();
        return processRequest(url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(Tab tab, String url) {
        return processRequest(url);
    }

    private WebResourceResponse processRequest(String url) {
        if (mInterceptor.test(url)) {
            return mInterceptor.intercept(url);
        }

        return null;
    }

    private void syncCookies(Tab tab) {
        MyCookieSaver.saveCookie(tab.getWebView());
    }

    @Override
    public void onPageStarted(Tab tab, Bitmap favicon) {
        // js must be added before page fully loaded???
        addJSInterface(tab);
    }

    /**
     * inject here custom styles and scripts
     */
    @Override
    public void onPageFinished(Tab tab, String url) {
        bindTabToInjectors(tab);
        syncCookies(tab);
    }

    /**
     * CAUTION: do all your error stuff here. Why, see below.
     * <br/>
     * In other places {@link WebView#getUrl WebView.getUrl} may return <code>null</code> because page not done loading.
     * <br/>
     * I've got a mistake. I tried to wait {@link #onPageFinished(Tab, String) onPageFinished} event. DO NOT DO THIS.
     * <br/>
     * <a href="https://stackoverflow.com/questions/13773037/webview-geturl-returns-null-because-page-not-done-loading">More info</a>
     * @param tab tab
     */
    @Override
    public void onReceiveError(Tab tab) {
        logger.info("onReceiveError called");
        tab.reload();
    }

    @Override
    public void onLoadSuccess(Tab tab) {
        mTranslator.enable();
        mLoadingManager.hide(tab);
        mApkUpdater.start();
    }

    @Override
    public void onTabCreated(Tab tab) {
        tab.setListener(this);
        mLoadingManager.show(tab);
    }

    @Override
    public void onControllerStart() {
        // if you need to disable auto-save webview state:
        // mController.getCrashRecoveryHandler().pauseState();
    }

    @Override
    public void onSaveControllerState(Bundle state) {
    }

    @Override
    public void onRestoreControllerState(Bundle state) {
        mStateUpdater.updateState(state);
    }

    @SuppressLint("NewApi")
    private void addJSInterface(Tab tab) {
        mJSInterface.add(tab);

        WebView webView = tab.getWebView();
        webView.addJavascriptInterface(mJSInterface, JS_INTERFACE_NAME);
    }

    private void bindTabToInjectors(Tab tab) {
        WebView w = tab.getWebView();
        mFormatInjector.add(w);
        mDecipherRoutineInjector.add(w);
        mEventResourceInjector.add(w);
    }

    private class LoadingManager {
        private final View mLoadingWidget;

        public LoadingManager(Context ctx) {
            LayoutInflater li = LayoutInflater.from(ctx);
            mLoadingWidget = li.inflate(R.layout.loading_main, null);
        }

        private void showHideLoading(Tab tab, boolean show) {
            FrameLayout wrapper = getWrapper(tab);
            if (wrapper == null) {
                return;
            }

            boolean hasNoParent = mLoadingWidget.getParent() == null;
            if (show && hasNoParent) {
                wrapper.addView(mLoadingWidget);
            } else {
                wrapper.removeView(mLoadingWidget);
            }

        }

        private FrameLayout getWrapper(Tab tab) {
            if (tab == null) {
                return null;
            }
            View container = tab.getViewContainer();
            if (container == null) {
                return null;
            }
            return container.findViewById(com.liskovsoft.browser.R.id.webview_wrapper);
        }

        public void show(Tab tab) {
            showHideLoading(tab, true);
        }

        public void hide(final Tab tab) {
            new Handler(mContext.getMainLooper())
                    .postDelayed(new Runnable() {
                @Override
                public void run() {
                    showHideLoading(tab, false);
                }
            }, 500);
        }
    }
}
