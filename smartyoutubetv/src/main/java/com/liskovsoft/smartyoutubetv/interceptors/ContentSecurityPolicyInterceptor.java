package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Fix 'Refused to execute inline script because it violates the following Content Security Policy directive'<br/>
 * Details: https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy
 */
public class ContentSecurityPolicyInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final String YT_ROOT_PAGE = "https://www.youtube.com/tv";

    public ContentSecurityPolicyInterceptor(Context context) {
        super();
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        if (url.contains(YT_ROOT_PAGE)) {
            return true;
        }

        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", new UserAgentManager().getUA());
        Response response = OkHttpHelpers.doGetOkHttpRequest(url, headers);

        if (response.isSuccessful()) {
            return createResponse(response.body().contentType(), response.body().byteStream());
        }

        return null;
    }
}
