package com.liskovsoft.smartyoutubetv.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MainRequestInterceptor extends RequestInterceptor {
    private Context mContext;
    private final ArrayList<RequestInterceptor> mInterceptors;

    public MainRequestInterceptor(Context context) {
        mContext = context;
        mInterceptors = new ArrayList<>();
        mInterceptors.add(new VideoQualityInterceptor());
        //mInterceptors.add(new ResolutionInterceptor(mContext));
    }

    @Override
    public boolean test(String url) {
        for (RequestInterceptor interceptor : mInterceptors) {
            if (interceptor.test(url)){
                return true;
            }
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        for (RequestInterceptor interceptor : mInterceptors) {
            if (interceptor.test(url)){
                return interceptor.intercept(url);
            }
        }
        return null;
    }
}
