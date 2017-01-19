package com.liskovsoft.browser.xwalk;

import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import okhttp3.*;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class XWalkResourceClientAdapter extends XWalkResourceClient {
    private final WebViewClient mWebViewClient;
    private final WebView mWebView;
    private OkHttpClient client;

    public XWalkResourceClientAdapter(WebViewClient client, WebView webView, XWalkView view) {
        super(view);
        mWebViewClient = client;
        mWebView = webView;
    }

    public WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    @Override
    public void onLoadStarted(XWalkView view, String url) {
        mWebViewClient.onPageStarted(mWebView, url, null);
    }

    @Override
    public void onLoadFinished(XWalkView view, String url) {
        mWebViewClient.onPageFinished(mWebView, url);
    }

    @Override
    public WebResourceResponse shouldInterceptLoadRequest(XWalkView view, String url) {
        return mWebViewClient.shouldInterceptRequest(mWebView, url);
    }

    // shouldOverrideUrlLoading called one time fore session, use shouldInterceptLoadRequest instead
    //@Override
    //public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
    //    return mWebViewClient.shouldOverrideUrlLoading(mWebView, url);
    //}

    public XWalkWebResourceResponse shouldInterceptLoadRequest2(XWalkView view, XWalkWebResourceRequest request) {
        if (client == null) {
            client = new OkHttpClient();
        }

        Map<String, String> headers = request.getRequestHeaders();
        headers.put("Accept-Language", "ru,en-US;q=0.8,en;q=0.6");

        Request okHttpRequest = new Request.Builder()
                .url(request.getUrl().toString())
                .headers(Headers.of(headers))
                .build();

        Response okHttpResponse = null;
        try {
            okHttpResponse = client.newCall(okHttpRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (okHttpResponse == null) {
            return createEmptyXWalkWebResourceResponse();
        }

        XWalkWebResourceResponse resourceResponse = createXWalkWebResourceResponse(
                getMimeType(okHttpResponse.body().contentType()),
                getCharset(okHttpResponse.body().contentType()),
                okHttpResponse.body().byteStream(),
                okHttpResponse.code(),
                okHttpResponse.message(),
                toRegularMap(okHttpResponse.headers().toMultimap()));
        return resourceResponse;
    }

    private XWalkWebResourceResponse createEmptyXWalkWebResourceResponse() {
        return createXWalkWebResourceResponse(null, null, null, 0, null, null);
    }

    private WebResourceResponse createEmptyWebResourceResponse() {
        return new WebResourceResponse(null, null, null);
    }

    private String getMimeType(MediaType contentType) {
        String type = contentType.type();
        String subtype = contentType.subtype();
        return String.format("%s/%s", type, subtype);
    }

    private String getCharset(MediaType contentType) {
        if (contentType.charset() == null) {
            return null;
        }
        return contentType.charset().name();
    }

    private Map<String, String> toRegularMap(Map<String, List<String>> multimap) {
        Map<String, String> resultMap = new HashMap<>();
        for (Entry<String, List<String>> entry : multimap.entrySet()) {
            resultMap.put(entry.getKey(), TextUtils.join(",", entry.getValue()));
        }
        return resultMap;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
