package edu.mit.mobile.android.appupdater.helpers;

import android.content.Context;
import android.util.Log;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        if (lastEx != null) {
            Helpers.showLongMessage(sContext, TAG, lastEx.getMessage());
        }

        return okHttpResponse;
    }

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return MyCookieLoader.loadCookie(url);
                    }
                })
                .connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
                .build();
    }
}
