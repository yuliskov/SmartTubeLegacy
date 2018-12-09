package com.liskovsoft.smartyoutubetv.common.okhttp;

import android.content.Context;
import android.util.Log;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpHelpers {
    private static final int NUM_TRIES = 10;
    private static final long CONNECT_TIMEOUT_S = 20;
    private static final String TAG = OkHttpHelpers.class.getSimpleName();
    private static OkHttpClient mClient;
    private static Context sContext;

    public static void setContext(Context ctx) {
        sContext = ctx;
    }

    public static Response doOkHttpRequest(String url) {
        if (mClient == null)
            mClient = createOkHttpClient();

        Request okHttpRequest = new Request.Builder()
                .url(url)
                .build();

        Response okHttpResponse = null;
        Exception lastEx = null;
        for (int tries = NUM_TRIES; tries > 0; tries--) {
            try {
                okHttpResponse = mClient.newCall(okHttpRequest).execute();
                if (!okHttpResponse.isSuccessful()) {
                    throw new IllegalStateException("Unexpected code " + okHttpResponse);
                }

                break; // no exception is thrown - job is done
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage(), ex); // network error, just return null
                okHttpResponse = null;
                lastEx = ex;
            }
        }

        if (lastEx != null && okHttpResponse == null) {
            MessageHelpers.showLongMessage(sContext, TAG, lastEx.getMessage());
        }

        return okHttpResponse;
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return MyCookieLoader.loadCookie(url);
                    }
                });

        return setupBuilder(builder).build();
    }

    public static OkHttpClient.Builder setupBuilder(OkHttpClient.Builder builder) {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                .build();

        builder
            .connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
            .readTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
            .writeTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
            .connectionSpecs(Collections.singletonList(spec));

        return enableTls12OnPreLollipop(builder);
    }

    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder builder) {
        //if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
        //    return custom builder;
        //}

        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, null, null);
            builder.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

            ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build();

            List<ConnectionSpec> specs = new ArrayList<>();
            specs.add(cs);
            specs.add(ConnectionSpec.COMPATIBLE_TLS);
            specs.add(ConnectionSpec.CLEARTEXT);

            builder.connectionSpecs(specs);
        } catch (Exception exc) {
            Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
        }

        return builder;
    }
}
