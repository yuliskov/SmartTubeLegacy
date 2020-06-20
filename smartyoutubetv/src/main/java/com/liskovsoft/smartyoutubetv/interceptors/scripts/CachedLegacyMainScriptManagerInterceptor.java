package com.liskovsoft.smartyoutubetv.interceptors.scripts;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import okhttp3.MediaType;

import java.io.InputStream;
import java.util.Locale;

public class CachedLegacyMainScriptManagerInterceptor extends LegacyMainScriptManagerInterceptor {
    private final Context mContext;
    private final String mLocalizedTvAssetName;

    public CachedLegacyMainScriptManagerInterceptor(Context context) {
        super(context);
        mContext = context;
        Locale defaultLocale = Locale.getDefault();
        String lang = defaultLocale.getLanguage();
        mLocalizedTvAssetName = String.format("files/tv/tv_%s.html", lang);
    }

    @Override
    public boolean test(String url) {
        if (url.endsWith("/tv")) {
            return true;
        }

        return super.test(url);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (url.endsWith("/tv")) {
            InputStream asset = AssetHelper.getAsset(mContext, mLocalizedTvAssetName);

            if (asset == null) {
                asset = AssetHelper.getAsset(mContext, "files/tv/tv.html");
            }

            return createResponse(MediaType.parse("text/html"), asset);
        }

        return super.intercept(url);
    }

    @Override
    protected InputStream getContent(String url) {
        if (isBaseScript(url)) {
            return AssetHelper.getAsset(mContext, "files/js/base.js");
        } else if (isMainScript(url)) {
            return AssetHelper.getAsset(mContext, "files/js/main.js");
        } else if (isPlayerScript(url)) {
            return AssetHelper.getAsset(mContext, "files/js/tv-player-ias.js");
        } else if (isStyle(url)) {
            return AssetHelper.getAsset(mContext, "files/css/content.css");
        }

        return null;
    }
}
