package com.liskovsoft.browser.xwalk;

import android.content.Context;
import android.webkit.WebSettings;
import com.liskovsoft.browser.util.HeadersWebSettingsDecorator;
import org.xwalk.core.XWalkSettings;

public class XWalkWebSettingsAdapter extends WebSettings {
    private final XWalkSettings xWalkSettings;

    public XWalkWebSettingsAdapter(XWalkSettings settings) {
        xWalkSettings = settings;
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
        xWalkSettings.setLayoutAlgorithm(newLayoutAlgorithm);
    }

    @Override
    public LayoutAlgorithm getLayoutAlgorithm() {
        XWalkSettings.LayoutAlgorithm layoutAlgorithm = xWalkSettings.getLayoutAlgorithm();
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
    public void setSupportZoom(boolean support) {
        xWalkSettings.setSupportZoom(support);
    }

    @Override
    public boolean supportZoom() {
        return xWalkSettings.supportZoom();
    }

    @Override
    public void setMediaPlaybackRequiresUserGesture(boolean require) {
        xWalkSettings.setMediaPlaybackRequiresUserGesture(require);
    }

    @Override
    public boolean getMediaPlaybackRequiresUserGesture() {
        return xWalkSettings.getMediaPlaybackRequiresUserGesture();
    }

    @Override
    public void setBuiltInZoomControls(boolean enabled) {
        xWalkSettings.setBuiltInZoomControls(enabled);
    }

    @Override
    public boolean getBuiltInZoomControls() {
        return xWalkSettings.getBuiltInZoomControls();
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
        xWalkSettings.setAllowFileAccess(allow);
    }

    @Override
    public boolean getAllowFileAccess() {
        return xWalkSettings.getAllowFileAccess();
    }

    @Override
    public void setAllowContentAccess(boolean allow) {
        xWalkSettings.setAllowContentAccess(allow);
    }

    @Override
    public boolean getAllowContentAccess() {
        return xWalkSettings.getAllowContentAccess();
    }

    @Override
    public void setLoadWithOverviewMode(boolean overview) {
        xWalkSettings.setLoadWithOverviewMode(overview);
    }

    @Override
    public boolean getLoadWithOverviewMode() {
        return xWalkSettings.getLoadWithOverviewMode();
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
        xWalkSettings.setSaveFormData(save);
    }

    @Override
    public boolean getSaveFormData() {
        return xWalkSettings.getSaveFormData();
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
        xWalkSettings.setTextZoom(textZoom);
    }

    @Override
    public int getTextZoom() {
        return xWalkSettings.getTextZoom();
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
        xWalkSettings.setUseWideViewPort(use);
    }

    @Override
    public boolean getUseWideViewPort() {
        return xWalkSettings.getUseWideViewPort();
    }

    @Override
    public void setSupportMultipleWindows(boolean support) {
        xWalkSettings.setSupportMultipleWindows(support);
    }

    @Override
    public boolean supportMultipleWindows() {
        return xWalkSettings.supportMultipleWindows();
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
        xWalkSettings.setDefaultFontSize(size);
    }

    @Override
    public int getDefaultFontSize() {
        return xWalkSettings.getDefaultFontSize();
    }

    @Override
    public void setDefaultFixedFontSize(int size) {
        xWalkSettings.setDefaultFixedFontSize(size);
    }

    @Override
    public int getDefaultFixedFontSize() {
        return xWalkSettings.getDefaultFixedFontSize();
    }

    @Override
    public void setLoadsImagesAutomatically(boolean flag) {
        xWalkSettings.setLoadsImagesAutomatically(flag);
    }

    @Override
    public boolean getLoadsImagesAutomatically() {
        return xWalkSettings.getLoadsImagesAutomatically();
    }

    @Override
    public void setBlockNetworkImage(boolean flag) {
        xWalkSettings.setBlockNetworkImage(flag);
    }

    @Override
    public boolean getBlockNetworkImage() {
        return xWalkSettings.getBlockNetworkImage();
    }

    @Override
    public void setBlockNetworkLoads(boolean flag) {
        xWalkSettings.setBlockNetworkLoads(flag);
    }

    @Override
    public boolean getBlockNetworkLoads() {
        return xWalkSettings.getBlockNetworkLoads();
    }

    @Override
    public void setJavaScriptEnabled(boolean flag) {
        xWalkSettings.setJavaScriptEnabled(flag);
    }

    @Override
    public void setAllowUniversalAccessFromFileURLs(boolean flag) {
        xWalkSettings.setAllowUniversalAccessFromFileURLs(flag);
    }

    @Override
    public void setAllowFileAccessFromFileURLs(boolean flag) {
        xWalkSettings.setAllowFileAccessFromFileURLs(flag);
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
        xWalkSettings.setDatabaseEnabled(flag);
    }

    @Override
    public void setDomStorageEnabled(boolean flag) {
        xWalkSettings.setDomStorageEnabled(flag);
    }

    @Override
    public boolean getDomStorageEnabled() {
        return xWalkSettings.getDomStorageEnabled();
    }

    @Override
    public String getDatabasePath() {
        return null;
    }

    @Override
    public boolean getDatabaseEnabled() {
        return xWalkSettings.getDatabaseEnabled();
    }

    @Override
    public void setGeolocationEnabled(boolean flag) {

    }

    @Override
    public boolean getJavaScriptEnabled() {
        return xWalkSettings.getJavaScriptEnabled();
    }

    @Override
    public boolean getAllowUniversalAccessFromFileURLs() {
        return xWalkSettings.getAllowUniversalAccessFromFileURLs();
    }

    @Override
    public boolean getAllowFileAccessFromFileURLs() {
        return xWalkSettings.getAllowFileAccessFromFileURLs();
    }

    @Override
    public PluginState getPluginState() {
        return null;
    }

    @Override
    public void setJavaScriptCanOpenWindowsAutomatically(boolean flag) {
        xWalkSettings.setJavaScriptCanOpenWindowsAutomatically(flag);
    }

    @Override
    public boolean getJavaScriptCanOpenWindowsAutomatically() {
        return xWalkSettings.getJavaScriptCanOpenWindowsAutomatically();
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
        xWalkSettings.setUserAgentString(ua);
    }

    @Override
    public String getUserAgentString() {
        return xWalkSettings.getUserAgentString();
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
        xWalkSettings.setCacheMode(mode);
    }

    @Override
    public int getCacheMode() {
        return xWalkSettings.getCacheMode();
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
