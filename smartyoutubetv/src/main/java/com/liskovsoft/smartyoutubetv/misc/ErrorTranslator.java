package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.webkit.WebViewClient;
import com.liskovsoft.smartyoutubetv.R;

import java.util.HashMap;

public class ErrorTranslator {
    private final Context mContext;
    private HashMap<Integer, Integer> mErrorMessages;
    private HashMap<Integer, String> mErrorIds;

    public ErrorTranslator(Context context) {
        mContext = context;
        setupErrorMapping();
    }

    private void setupErrorMapping() {
        mErrorMessages = new HashMap<>();
        mErrorMessages.put(WebViewClient.ERROR_HOST_LOOKUP, R.string.error_host_lookup);
        mErrorMessages.put(WebViewClient.ERROR_CONNECT, R.string.error_host_lookup);
        mErrorMessages.put(WebViewClient.ERROR_TIMEOUT, R.string.error_host_lookup);

        mErrorIds = new HashMap<>();
        mErrorIds.put(WebViewClient.ERROR_HOST_LOOKUP, "ERROR_HOST_LOOKUP");
        mErrorIds.put(WebViewClient.ERROR_CONNECT, "ERROR_CONNECT");
        mErrorIds.put(WebViewClient.ERROR_TIMEOUT, "ERROR_TIMEOUT");
        mErrorIds.put(WebViewClient.ERROR_BAD_URL, "ERROR_BAD_URL");
        mErrorIds.put(WebViewClient.ERROR_AUTHENTICATION, "ERROR_AUTHENTICATION");
        mErrorIds.put(WebViewClient.ERROR_UNSUPPORTED_AUTH_SCHEME, "ERROR_UNSUPPORTED_AUTH_SCHEME");
        mErrorIds.put(WebViewClient.ERROR_TOO_MANY_REQUESTS, "ERROR_TOO_MANY_REQUESTS");
        mErrorIds.put(WebViewClient.ERROR_FAILED_SSL_HANDSHAKE, "ERROR_FAILED_SSL_HANDSHAKE");
        mErrorIds.put(WebViewClient.ERROR_UNKNOWN, "ERROR_UNKNOWN");
    }

    public String translate(int errorCode) {
        Integer resId = mErrorMessages.get(errorCode);
        String errorId = mErrorIds.get(errorCode);
        if (errorId == null) {
            errorId = String.valueOf(errorCode);
        }
        if (resId != null) {
            return mContext.getResources().getString(resId, errorId);
        }
        return mContext.getResources().getString(R.string.error_unknown, errorId);
    }
}
