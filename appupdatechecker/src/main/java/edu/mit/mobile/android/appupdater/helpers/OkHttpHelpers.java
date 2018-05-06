package edu.mit.mobile.android.appupdater.helpers;

import android.util.Log;
import edu.mit.mobile.android.appupdater.downloadmanager.GoogleResolver;
import okhttp3.Dns;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpHelpers {
    private static final int NUM_TRIES = 5;
    private static final long CONNECT_TIMEOUT_S = 20;
    private static final String TAG = OkHttpHelpers.class.getSimpleName();
    private static OkHttpClient mClient;

    public static Response doOkHttpRequest(String url) {
        if (mClient == null)
            mClient = createOkHttpClient();

        Request okHttpRequest = new Request.Builder()
                .url(url)
                .build();

        Response okHttpResponse = null;
        for (int tries = NUM_TRIES; tries > 0; tries--) {
            try {
                okHttpResponse = mClient.newCall(okHttpRequest).execute();
                if (!okHttpResponse.isSuccessful()) throw new IllegalStateException("Unexpected code " + okHttpResponse);

                break; // no exception is thrown - job is done
            } catch (IOException ex) {
                Log.e(TAG, ex.getMessage(), ex); // network error, just return null
            }
        }

        return okHttpResponse;
    }

    private static OkHttpClient createOkHttpClient() {
        final GoogleResolver resolver = new GoogleResolver();
        return new OkHttpClient.Builder()
                .dns(new Dns() {
                    @Override
                    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
                        List<InetAddress> hosts = resolver.resolve(hostname);
                        return hosts.isEmpty() ? Dns.SYSTEM.lookup(hostname) : hosts; // use system dns as a fallback
                    }
                })
                .connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
                .build();
    }
}
