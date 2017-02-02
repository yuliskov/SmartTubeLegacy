package com.liskovsoft.smartyoutubetv.helpers;

import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RequestInterceptor {
    private OkHttpClient client;
    
    public boolean tryIntercept(String url) {
        return false;
    }

    public WebResourceResponse intercept(String url) {
        return injectJS(url, "common.js");
    }

    private WebResourceResponse injectJS(String url, String path) {
        if (client == null) {
            client = new OkHttpClient();
        }

        Request okHttpRequest = new Request.Builder()
                .url(url)
                .build();

        Response okHttpResponse = null;
        try {
            okHttpResponse = client.newCall(okHttpRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebResourceResponse resourceResponse = new WebResourceResponse(
                getMimeType(okHttpResponse.body().contentType()),
                getCharset(okHttpResponse.body().contentType()),
                okHttpResponse.body().byteStream()
                );
        return resourceResponse;
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
