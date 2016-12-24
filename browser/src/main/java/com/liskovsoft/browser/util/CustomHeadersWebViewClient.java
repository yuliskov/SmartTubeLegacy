package com.liskovsoft.browser.util;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CustomHeadersWebViewClient extends WebViewClient {
    private static final Logger logger = LoggerFactory.getLogger(CustomHeadersWebViewClient.class);
    private PageData mPageDefaults;
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (mPageDefaults == null || mPageDefaults.getHeaders() == null)
            super.shouldInterceptRequest(view, url);
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            for (Map.Entry<String, String> entry : mPageDefaults.getHeaders().entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            builder.url(url.trim());
            Request request = builder.build();

            Response response = httpClient.newCall(request).execute();

            return new WebResourceResponse(
                    response.header("content-type", response.body().contentType().type()), // You can set something other as default content-type
                    response.header("content-encoding", "utf-8"),  // Again, you can set another encoding as default
                    response.body().byteStream()
            );
        } catch (Exception e) {
            logger.warn("Cant fetch web page", url);
            //return null to tell WebView we failed to fetch it WebView should try again.
            return null;
        }
    }

    public void setPageDefaults(PageData pageDefaults) {
        this.mPageDefaults = pageDefaults;
    }
}
