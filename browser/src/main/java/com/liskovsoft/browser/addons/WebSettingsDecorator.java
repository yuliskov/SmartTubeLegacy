package com.liskovsoft.browser.addons;

import android.content.Context;
import android.webkit.WebSettings;

/**
 * TODO: deal with low API in WebSettings methods
 */
public abstract class WebSettingsDecorator extends WebSettings {
    protected WebSettings mWebSettings;

    public WebSettingsDecorator(WebSettings settings) {
        if (settings == null) throw new IllegalArgumentException("You need to set WebSettings");
        mWebSettings = settings;
    }

    /**
     * Decorated methods
     */

    @Override
    public void setSafeBrowsingEnabled(boolean b) {
        mWebSettings.setSafeBrowsingEnabled(b);
    }

    @Override
    public boolean getSafeBrowsingEnabled() {
        return mWebSettings.getSafeBrowsingEnabled();
    }

    @Override
    public void setSupportZoom(boolean support) {
        mWebSettings.setSupportZoom(support);
    }

    @Override
    public boolean supportZoom() {
        return mWebSettings.supportZoom();
    }

    @Override
    public void setMediaPlaybackRequiresUserGesture(boolean require) {
        mWebSettings.setMediaPlaybackRequiresUserGesture(require);
    }

    @Override
    public boolean getMediaPlaybackRequiresUserGesture() {
        return mWebSettings.getMediaPlaybackRequiresUserGesture();
    }

    @Override
    public void setBuiltInZoomControls(boolean enabled) {
        mWebSettings.setBuiltInZoomControls(enabled);
    }

    @Override
    public boolean getBuiltInZoomControls() {
        return mWebSettings.getBuiltInZoomControls();
    }

    @Override
    public void setDisplayZoomControls(boolean enabled) {
        mWebSettings.setDisplayZoomControls(enabled);
    }

    @Override
    public boolean getDisplayZoomControls() {
        return mWebSettings.getDisplayZoomControls();
    }

    @Override
    public void setAllowFileAccess(boolean allow) {
        mWebSettings.setAllowFileAccess(allow);
    }

    @Override
    public boolean getAllowFileAccess() {
        return mWebSettings.getAllowFileAccess();
    }

    @Override
    public void setAllowContentAccess(boolean allow) {
        mWebSettings.setAllowContentAccess(allow);
    }

    @Override
    public boolean getAllowContentAccess() {
        return mWebSettings.getAllowContentAccess();
    }

    @Override
    public void setLoadWithOverviewMode(boolean overview) {
        mWebSettings.setLoadWithOverviewMode(overview);
    }

    @Override
    public boolean getLoadWithOverviewMode() {
        return mWebSettings.getLoadWithOverviewMode();
    }

    @Override
    @Deprecated
    public void setEnableSmoothTransition(boolean enable) {
        mWebSettings.setEnableSmoothTransition(enable);
    }

    @Override
    @Deprecated
    public boolean enableSmoothTransition() {
        return mWebSettings.enableSmoothTransition();
    }

    @Override
    public void setSaveFormData(boolean save) {
        mWebSettings.setSaveFormData(save);
    }

    @Override
    public boolean getSaveFormData() {
        return mWebSettings.getSaveFormData();
    }

    @Override
    @Deprecated
    public void setSavePassword(boolean save) {
        mWebSettings.setSavePassword(save);
    }

    @Override
    @Deprecated
    public boolean getSavePassword() {
        return mWebSettings.getSavePassword();
    }

    @Override
    public void setTextZoom(int textZoom) {
        mWebSettings.setTextZoom(textZoom);
    }

    @Override
    public int getTextZoom() {
        return mWebSettings.getTextZoom();
    }

    @Override
    @Deprecated
    public void setTextSize(TextSize t) {
        mWebSettings.setTextSize(t);
    }

    @Override
    @Deprecated
    public TextSize getTextSize() {
        return mWebSettings.getTextSize();
    }

    @Override
    @Deprecated
    public void setDefaultZoom(ZoomDensity zoom) {
        mWebSettings.setDefaultZoom(zoom);
    }

    @Override
    @Deprecated
    public ZoomDensity getDefaultZoom() {
        return mWebSettings.getDefaultZoom();
    }

    @Override
    @Deprecated
    public void setLightTouchEnabled(boolean enabled) {
        mWebSettings.setLightTouchEnabled(enabled);
    }

    @Override
    @Deprecated
    public boolean getLightTouchEnabled() {
        return mWebSettings.getLightTouchEnabled();
    }

    @Override
    public void setUseWideViewPort(boolean use) {
        mWebSettings.setUseWideViewPort(use);
    }

    @Override
    public boolean getUseWideViewPort() {
        return mWebSettings.getUseWideViewPort();
    }

    @Override
    public void setSupportMultipleWindows(boolean support) {
        mWebSettings.setSupportMultipleWindows(support);
    }

    @Override
    public boolean supportMultipleWindows() {
        return mWebSettings.supportMultipleWindows();
    }

    @Override
    public void setLayoutAlgorithm(LayoutAlgorithm l) {
        mWebSettings.setLayoutAlgorithm(l);
    }

    @Override
    public LayoutAlgorithm getLayoutAlgorithm() {
        return mWebSettings.getLayoutAlgorithm();
    }

    @Override
    public void setStandardFontFamily(String font) {
        mWebSettings.setStandardFontFamily(font);
    }

    @Override
    public String getStandardFontFamily() {
        return mWebSettings.getStandardFontFamily();
    }

    @Override
    public void setFixedFontFamily(String font) {
        mWebSettings.setFixedFontFamily(font);
    }

    @Override
    public String getFixedFontFamily() {
        return mWebSettings.getFixedFontFamily();
    }

    @Override
    public void setSansSerifFontFamily(String font) {
        mWebSettings.setSansSerifFontFamily(font);
    }

    @Override
    public String getSansSerifFontFamily() {
        return mWebSettings.getSansSerifFontFamily();
    }

    @Override
    public void setSerifFontFamily(String font) {
        mWebSettings.setSerifFontFamily(font);
    }

    @Override
    public String getSerifFontFamily() {
        return mWebSettings.getSerifFontFamily();
    }

    @Override
    public void setCursiveFontFamily(String font) {
        mWebSettings.setCursiveFontFamily(font);
    }

    @Override
    public String getCursiveFontFamily() {
        return mWebSettings.getCursiveFontFamily();
    }

    @Override
    public void setFantasyFontFamily(String font) {
        mWebSettings.setFantasyFontFamily(font);
    }

    @Override
    public String getFantasyFontFamily() {
        return mWebSettings.getFantasyFontFamily();
    }

    @Override
    public void setMinimumFontSize(int size) {
        mWebSettings.setMinimumFontSize(size);
    }

    @Override
    public int getMinimumFontSize() {
        return mWebSettings.getMinimumFontSize();
    }

    @Override
    public void setMinimumLogicalFontSize(int size) {
        mWebSettings.setMinimumLogicalFontSize(size);
    }

    @Override
    public int getMinimumLogicalFontSize() {
        return mWebSettings.getMinimumLogicalFontSize();
    }

    @Override
    public void setDefaultFontSize(int size) {
        mWebSettings.setDefaultFontSize(size);
    }

    @Override
    public int getDefaultFontSize() {
        return mWebSettings.getDefaultFontSize();
    }

    @Override
    public void setDefaultFixedFontSize(int size) {
        mWebSettings.setDefaultFixedFontSize(size);
    }

    @Override
    public int getDefaultFixedFontSize() {
        return mWebSettings.getDefaultFixedFontSize();
    }

    @Override
    public void setLoadsImagesAutomatically(boolean flag) {
        mWebSettings.setLoadsImagesAutomatically(flag);
    }

    @Override
    public boolean getLoadsImagesAutomatically() {
        return mWebSettings.getLoadsImagesAutomatically();
    }

    @Override
    public void setBlockNetworkImage(boolean flag) {
        mWebSettings.setBlockNetworkImage(flag);
    }

    @Override
    public boolean getBlockNetworkImage() {
        return mWebSettings.getBlockNetworkImage();
    }

    @Override
    public void setBlockNetworkLoads(boolean flag) {
        mWebSettings.setBlockNetworkLoads(flag);
    }

    @Override
    public boolean getBlockNetworkLoads() {
        return mWebSettings.getBlockNetworkLoads();
    }

    @Override
    public void setJavaScriptEnabled(boolean flag) {
        mWebSettings.setJavaScriptEnabled(flag);
    }

    @Override
    public void setAllowUniversalAccessFromFileURLs(boolean flag) {
        mWebSettings.setAllowUniversalAccessFromFileURLs(flag);
    }

    @Override
    public void setAllowFileAccessFromFileURLs(boolean flag) {
        mWebSettings.setAllowFileAccessFromFileURLs(flag);
    }

    @Override
    @Deprecated
    public void setPluginState(PluginState state) {
        mWebSettings.setPluginState(state);
    }

    @Override
    @Deprecated
    public void setDatabasePath(String databasePath) {
        mWebSettings.setDatabasePath(databasePath);
    }

    @Override
    @Deprecated
    public void setGeolocationDatabasePath(String databasePath) {
        mWebSettings.setGeolocationDatabasePath(databasePath);
    }

    @Override
    public void setAppCacheEnabled(boolean flag) {
        mWebSettings.setAppCacheEnabled(flag);
    }

    @Override
    public void setAppCachePath(String appCachePath) {
        mWebSettings.setAppCachePath(appCachePath);
    }

    @Override
    @Deprecated
    public void setAppCacheMaxSize(long appCacheMaxSize) {
        mWebSettings.setAppCacheMaxSize(appCacheMaxSize);
    }

    @Override
    public void setDatabaseEnabled(boolean flag) {
        mWebSettings.setDatabaseEnabled(flag);
    }

    @Override
    public void setDomStorageEnabled(boolean flag) {
        mWebSettings.setDomStorageEnabled(flag);
    }

    @Override
    public boolean getDomStorageEnabled() {
        return mWebSettings.getDomStorageEnabled();
    }

    @Override
    @Deprecated
    public String getDatabasePath() {
        return mWebSettings.getDatabasePath();
    }

    @Override
    public boolean getDatabaseEnabled() {
        return mWebSettings.getDatabaseEnabled();
    }

    @Override
    public void setGeolocationEnabled(boolean flag) {
        mWebSettings.setGeolocationEnabled(flag);
    }

    @Override
    public boolean getJavaScriptEnabled() {
        return mWebSettings.getJavaScriptEnabled();
    }

    @Override
    public boolean getAllowUniversalAccessFromFileURLs() {
        return mWebSettings.getAllowUniversalAccessFromFileURLs();
    }

    @Override
    public boolean getAllowFileAccessFromFileURLs() {
        return mWebSettings.getAllowFileAccessFromFileURLs();
    }

    @Override
    @Deprecated
    public PluginState getPluginState() {
        return mWebSettings.getPluginState();
    }

    @Override
    public void setJavaScriptCanOpenWindowsAutomatically(boolean flag) {
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(flag);
    }

    @Override
    public boolean getJavaScriptCanOpenWindowsAutomatically() {
        return mWebSettings.getJavaScriptCanOpenWindowsAutomatically();
    }

    @Override
    public void setDefaultTextEncodingName(String encoding) {
        mWebSettings.setDefaultTextEncodingName(encoding);
    }

    @Override
    public String getDefaultTextEncodingName() {
        return mWebSettings.getDefaultTextEncodingName();
    }

    @Override
    public void setUserAgentString(String ua) {
        mWebSettings.setUserAgentString(ua);
    }

    @Override
    public String getUserAgentString() {
        return mWebSettings.getUserAgentString();
    }

    public static String getDefaultUserAgent(Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }

    @Override
    public void setNeedInitialFocus(boolean flag) {
        mWebSettings.setNeedInitialFocus(flag);
    }

    @Override
    @Deprecated
    public void setRenderPriority(RenderPriority priority) {
        mWebSettings.setRenderPriority(priority);
    }

    @Override
    public void setCacheMode(int mode) {
        mWebSettings.setCacheMode(mode);
    }

    @Override
    public int getCacheMode() {
        return mWebSettings.getCacheMode();
    }

    @Override
    public void setMixedContentMode(int mode) {
        mWebSettings.setMixedContentMode(mode);
    }

    @Override
    public int getMixedContentMode() {
        return mWebSettings.getMixedContentMode();
    }

    @Override
    public void setOffscreenPreRaster(boolean enabled) {
        mWebSettings.setOffscreenPreRaster(enabled);
    }

    @Override
    public boolean getOffscreenPreRaster() {
        return mWebSettings.getOffscreenPreRaster();
    }

    @Override
    public void setDisabledActionModeMenuItems(int menuItems) {
        mWebSettings.setDisabledActionModeMenuItems(menuItems);
    }

    @Override
    public int getDisabledActionModeMenuItems() {
        return mWebSettings.getDisabledActionModeMenuItems();
    }
}
