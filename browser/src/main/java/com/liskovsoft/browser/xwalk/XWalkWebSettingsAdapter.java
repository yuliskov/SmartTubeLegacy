package com.liskovsoft.browser.xwalk;

import android.content.Context;
import android.webkit.WebSettings;
import org.xwalk.core.XWalkSettings;

public class XWalkWebSettingsAdapter extends WebSettings {
    private final XWalkSettings mXWalkSettings;

    public XWalkWebSettingsAdapter(XWalkSettings settings) {
        mXWalkSettings = settings;
    }

    @Override
    public void setLayoutAlgorithm(LayoutAlgorithm l) {
        LayoutAlgorithm layoutAlgorithm = l;
        XWalkSettings.LayoutAlgorithm newLayoutAlgorithm = XWalkSettings.LayoutAlgorithm.NORMAL;
        switch (layoutAlgorithm) {
            case NORMAL:
                newLayoutAlgorithm = XWalkSettings.LayoutAlgorithm.NORMAL;
                break;
            case SINGLE_COLUMN:
                newLayoutAlgorithm = XWalkSettings.LayoutAlgorithm.SINGLE_COLUMN;
                break;
            case NARROW_COLUMNS:
                newLayoutAlgorithm = XWalkSettings.LayoutAlgorithm.NARROW_COLUMNS;
                break;
            case TEXT_AUTOSIZING:
                newLayoutAlgorithm = XWalkSettings.LayoutAlgorithm.TEXT_AUTOSIZING;
                break;
        }
        mXWalkSettings.setLayoutAlgorithm(newLayoutAlgorithm);
    }

    @Override
    public LayoutAlgorithm getLayoutAlgorithm() {
        XWalkSettings.LayoutAlgorithm layoutAlgorithm = mXWalkSettings.getLayoutAlgorithm();
        LayoutAlgorithm newLayoutAlgorithm = LayoutAlgorithm.NORMAL;
        switch (layoutAlgorithm) {
            case NORMAL:
                newLayoutAlgorithm = LayoutAlgorithm.NORMAL;
                break;
            case SINGLE_COLUMN:
                newLayoutAlgorithm = LayoutAlgorithm.SINGLE_COLUMN;
                break;
            case NARROW_COLUMNS:
                newLayoutAlgorithm = LayoutAlgorithm.NARROW_COLUMNS;
                break;
            case TEXT_AUTOSIZING:
                newLayoutAlgorithm = LayoutAlgorithm.TEXT_AUTOSIZING;
                break;
        }
        return newLayoutAlgorithm;
    }

    ////////////////////////////////////////////////////////////////////


    @Override
    public void setSafeBrowsingEnabled(boolean b) {
        /* Not Implemented */
    }

    @Override
    public boolean getSafeBrowsingEnabled() {
        /* Not Implemented */
        return false;
    }

    @Override
    public void setSupportZoom(boolean support) {
        mXWalkSettings.setSupportZoom(support);
    }

    @Override
    public boolean supportZoom() {
        return mXWalkSettings.supportZoom();
    }

    @Override
    public void setMediaPlaybackRequiresUserGesture(boolean require) {
        mXWalkSettings.setMediaPlaybackRequiresUserGesture(require);
    }

    @Override
    public boolean getMediaPlaybackRequiresUserGesture() {
        return mXWalkSettings.getMediaPlaybackRequiresUserGesture();
    }

    @Override
    public void setBuiltInZoomControls(boolean enabled) {
        mXWalkSettings.setBuiltInZoomControls(enabled);
    }

    @Override
    public boolean getBuiltInZoomControls() {
        return mXWalkSettings.getBuiltInZoomControls();
    }

    @Override
    public void setDisplayZoomControls(boolean enabled) {

    }

    @Override
    public boolean getDisplayZoomControls() {
        return false;
    }

    @Override
    public void setAllowFileAccess(boolean allow) {
        mXWalkSettings.setAllowFileAccess(allow);
    }

    @Override
    public boolean getAllowFileAccess() {
        return mXWalkSettings.getAllowFileAccess();
    }

    @Override
    public void setAllowContentAccess(boolean allow) {
        mXWalkSettings.setAllowContentAccess(allow);
    }

    @Override
    public boolean getAllowContentAccess() {
        return mXWalkSettings.getAllowContentAccess();
    }

    @Override
    public void setLoadWithOverviewMode(boolean overview) {
        mXWalkSettings.setLoadWithOverviewMode(overview);
    }

    @Override
    public boolean getLoadWithOverviewMode() {
        return mXWalkSettings.getLoadWithOverviewMode();
    }

    @Override
    public void setEnableSmoothTransition(boolean enable) {

    }

    @Override
    public boolean enableSmoothTransition() {
        return false;
    }

    @Override
    public void setSaveFormData(boolean save) {
        mXWalkSettings.setSaveFormData(save);
    }

    @Override
    public boolean getSaveFormData() {
        return mXWalkSettings.getSaveFormData();
    }

    @Override
    public void setSavePassword(boolean save) {

    }

    @Override
    public boolean getSavePassword() {
        return false;
    }

    @Override
    public void setTextZoom(int textZoom) {
        mXWalkSettings.setTextZoom(textZoom);
    }

    @Override
    public int getTextZoom() {
        return mXWalkSettings.getTextZoom();
    }

    @Override
    public void setDefaultZoom(ZoomDensity zoom) {

    }

    @Override
    public ZoomDensity getDefaultZoom() {
        return null;
    }

    @Override
    public void setLightTouchEnabled(boolean enabled) {

    }

    @Override
    public boolean getLightTouchEnabled() {
        return false;
    }

    @Override
    public void setUseWideViewPort(boolean use) {
        mXWalkSettings.setUseWideViewPort(use);
    }

    @Override
    public boolean getUseWideViewPort() {
        return mXWalkSettings.getUseWideViewPort();
    }

    @Override
    public void setSupportMultipleWindows(boolean support) {
        mXWalkSettings.setSupportMultipleWindows(support);
    }

    @Override
    public boolean supportMultipleWindows() {
        return mXWalkSettings.supportMultipleWindows();
    }

    @Override
    public void setStandardFontFamily(String font) {

    }

    @Override
    public String getStandardFontFamily() {
        return null;
    }

    @Override
    public void setFixedFontFamily(String font) {

    }

    @Override
    public String getFixedFontFamily() {
        return null;
    }

    @Override
    public void setSansSerifFontFamily(String font) {

    }

    @Override
    public String getSansSerifFontFamily() {
        return null;
    }

    @Override
    public void setSerifFontFamily(String font) {

    }

    @Override
    public String getSerifFontFamily() {
        return null;
    }

    @Override
    public void setCursiveFontFamily(String font) {

    }

    @Override
    public String getCursiveFontFamily() {
        return null;
    }

    @Override
    public void setFantasyFontFamily(String font) {

    }

    @Override
    public String getFantasyFontFamily() {
        return null;
    }

    @Override
    public void setMinimumFontSize(int size) {

    }

    @Override
    public int getMinimumFontSize() {
        return 0;
    }

    @Override
    public void setMinimumLogicalFontSize(int size) {

    }

    @Override
    public int getMinimumLogicalFontSize() {
        return 0;
    }

    @Override
    public void setDefaultFontSize(int size) {
        mXWalkSettings.setDefaultFontSize(size);
    }

    @Override
    public int getDefaultFontSize() {
        return mXWalkSettings.getDefaultFontSize();
    }

    @Override
    public void setDefaultFixedFontSize(int size) {
        mXWalkSettings.setDefaultFixedFontSize(size);
    }

    @Override
    public int getDefaultFixedFontSize() {
        return mXWalkSettings.getDefaultFixedFontSize();
    }

    @Override
    public void setLoadsImagesAutomatically(boolean flag) {
        mXWalkSettings.setLoadsImagesAutomatically(flag);
    }

    @Override
    public boolean getLoadsImagesAutomatically() {
        return mXWalkSettings.getLoadsImagesAutomatically();
    }

    @Override
    public void setBlockNetworkImage(boolean flag) {
        mXWalkSettings.setBlockNetworkImage(flag);
    }

    @Override
    public boolean getBlockNetworkImage() {
        return mXWalkSettings.getBlockNetworkImage();
    }

    @Override
    public void setBlockNetworkLoads(boolean flag) {
        mXWalkSettings.setBlockNetworkLoads(flag);
    }

    @Override
    public boolean getBlockNetworkLoads() {
        return mXWalkSettings.getBlockNetworkLoads();
    }

    @Override
    public void setJavaScriptEnabled(boolean flag) {
        mXWalkSettings.setJavaScriptEnabled(flag);
    }

    @Override
    public void setAllowUniversalAccessFromFileURLs(boolean flag) {
        mXWalkSettings.setAllowUniversalAccessFromFileURLs(flag);
    }

    @Override
    public void setAllowFileAccessFromFileURLs(boolean flag) {
        mXWalkSettings.setAllowFileAccessFromFileURLs(flag);
    }

    @Override
    public void setPluginState(PluginState state) {

    }

    @Override
    public void setDatabasePath(String databasePath) {

    }

    @Override
    public void setGeolocationDatabasePath(String databasePath) {

    }

    @Override
    public void setAppCacheEnabled(boolean flag) {

    }

    @Override
    public void setAppCachePath(String appCachePath) {

    }

    @Override
    public void setAppCacheMaxSize(long appCacheMaxSize) {

    }

    @Override
    public void setDatabaseEnabled(boolean flag) {
        mXWalkSettings.setDatabaseEnabled(flag);
    }

    @Override
    public void setDomStorageEnabled(boolean flag) {
        mXWalkSettings.setDomStorageEnabled(flag);
    }

    @Override
    public boolean getDomStorageEnabled() {
        return mXWalkSettings.getDomStorageEnabled();
    }

    @Override
    public String getDatabasePath() {
        return null;
    }

    @Override
    public boolean getDatabaseEnabled() {
        return mXWalkSettings.getDatabaseEnabled();
    }

    @Override
    public void setGeolocationEnabled(boolean flag) {

    }

    @Override
    public boolean getJavaScriptEnabled() {
        return mXWalkSettings.getJavaScriptEnabled();
    }

    @Override
    public boolean getAllowUniversalAccessFromFileURLs() {
        return mXWalkSettings.getAllowUniversalAccessFromFileURLs();
    }

    @Override
    public boolean getAllowFileAccessFromFileURLs() {
        return mXWalkSettings.getAllowFileAccessFromFileURLs();
    }

    @Override
    public PluginState getPluginState() {
        return null;
    }

    @Override
    public void setJavaScriptCanOpenWindowsAutomatically(boolean flag) {
        mXWalkSettings.setJavaScriptCanOpenWindowsAutomatically(flag);
    }

    @Override
    public boolean getJavaScriptCanOpenWindowsAutomatically() {
        return mXWalkSettings.getJavaScriptCanOpenWindowsAutomatically();
    }

    @Override
    public void setDefaultTextEncodingName(String encoding) {

    }

    @Override
    public String getDefaultTextEncodingName() {
        return null;
    }

    @Override
    public void setUserAgentString(String ua) {
        mXWalkSettings.setUserAgentString(ua);
    }

    @Override
    public String getUserAgentString() {
        return mXWalkSettings.getUserAgentString();
    }

    public static String getDefaultUserAgent(Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }

    @Override
    public void setNeedInitialFocus(boolean flag) {

    }

    @Override
    public void setRenderPriority(RenderPriority priority) {

    }

    @Override
    public void setCacheMode(int mode) {
        mXWalkSettings.setCacheMode(mode);
    }

    @Override
    public int getCacheMode() {
        return mXWalkSettings.getCacheMode();
    }

    @Override
    public void setMixedContentMode(int mode) {

    }

    @Override
    public int getMixedContentMode() {
        return 0;
    }

    @Override
    public void setOffscreenPreRaster(boolean enabled) {

    }

    @Override
    public boolean getOffscreenPreRaster() {
        return false;
    }

    @Override
    public void setDisabledActionModeMenuItems(int menuItems) {

    }

    @Override
    public int getDisabledActionModeMenuItems() {
        return 0;
    }
}
