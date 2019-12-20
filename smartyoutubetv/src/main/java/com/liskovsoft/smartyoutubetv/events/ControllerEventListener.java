package com.liskovsoft.smartyoutubetv.events;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.DecipherRoutineInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.fragments.TwoFragmentManager;
import com.liskovsoft.smartyoutubetv.injectors.WebViewJavaScriptInterface;
import com.liskovsoft.smartyoutubetv.interceptors.MainRequestInterceptor;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.ErrorTranslator;
import com.liskovsoft.smartyoutubetv.misc.MyCookieSaver;
import com.liskovsoft.smartyoutubetv.misc.StateUpdater;

public class ControllerEventListener implements Controller.EventListener, Tab.EventListener {
    private static final String TAG = ControllerEventListener.class.getSimpleName();
    private static final String JS_INTERFACE_NAME = "app";
    private final Context mContext;
    private final WebViewJavaScriptInterface mJSInterface;
    // private final VideoFormatInjector mFormatInjector;
    private final DecipherRoutineInjector mDecipherInjector;
    private final GenericEventResourceInjector mGenericInjector;
    private final StateUpdater mStateUpdater;
    private final Controller mController;
    private final RequestInterceptor mMainInterceptor;
    private final ErrorTranslator mErrorTranslator;
    private final MyCookieSaver mCookieSaver;

    public ControllerEventListener(Activity context, Controller controller) {
        mContext = context;
        mController = controller;
        mStateUpdater = new StateUpdater(null, context);

        // mFormatInjector = new VideoFormatInjector(mContext);
        mDecipherInjector = new DecipherRoutineInjector(mContext);
        mGenericInjector = new GenericEventResourceInjector(mContext);
        mJSInterface = new WebViewJavaScriptInterface(mContext);
        mMainInterceptor = new MainRequestInterceptor(mContext);
        mErrorTranslator = new ErrorTranslator(mContext);
        mCookieSaver = new MyCookieSaver(mContext);
    }

    private FrameLayout getRootView() {
        return ((AppCompatActivity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(Tab tab, WebResourceRequest request) {
        if (VERSION.SDK_INT >= 21) {
            String url = request.getUrl().toString();
            return processRequest(url);
        }

        return null;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(Tab tab, String url) {
        return processRequest(url);
    }

    private WebResourceResponse processRequest(String url) {
        if (mMainInterceptor.test(url)) {
            return mMainInterceptor.intercept(url);
        }

        return null;
    }

    @Override
    public void onPageStarted(Tab tab, Bitmap favicon) {
        // js must be added before page fully loaded???
        // addJSInterface(tab);
    }

    /**
     * inject here custom styles and scripts
     */
    @Override
    public void onPageFinished(Tab tab, String url) {
        bindTabToInjectors(tab);
        // syncCookies(tab);
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
     * @param errorCode see {@link android.webkit.WebViewClient} for details
     */
    @Override
    public void onReceiveError(Tab tab, int errorCode) {
        Log.w(TAG, "onReceiveError called: errorCode: " + errorCode);

        if (((FragmentManager) mContext).isAppLoaded()) {
            Log.w(TAG, "error in the middle of the work... ignoring...");
            return;
        }

        Log.w(TAG, "onReceiveError called: reloading tab...");
        ((FragmentManager) mContext).getLoadingManager().showMessage(mErrorTranslator.translate(errorCode));
        tab.reload();
    }

    @Override
    public void onLoadSuccess(Tab tab) {
        // onAppLoaded also called inside JavaScriptMessageHandler class
        if (mContext instanceof TwoFragmentManager) {
            ((TwoFragmentManager) mContext).onBrowserLoaded();
        }

        mCookieSaver.saveCookie();
    }

    @Override
    public void onTabCreated(Tab tab) {
        addJSInterface(tab);
        tab.setListener(this);
        // mLoadingManager.show();
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
        Log.i(TAG, "ControllerEventListener::on addJSInterface");

        mJSInterface.add(tab);

        WebView webView = tab.getWebView();
        webView.addJavascriptInterface(mJSInterface, JS_INTERFACE_NAME);
    }

    private void bindTabToInjectors(Tab tab) {
        WebView w = tab.getWebView();
        mDecipherInjector.add(w);
        mGenericInjector.add(w);
    }
}
