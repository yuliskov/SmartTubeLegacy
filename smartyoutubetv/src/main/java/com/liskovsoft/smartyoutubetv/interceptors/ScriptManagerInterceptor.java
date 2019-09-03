package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.webscripts.MainCachedScriptManager;
import com.liskovsoft.smartyoutubetv.webscripts.ScriptManager;
import okhttp3.Response;

import java.io.InputStream;

public abstract class ScriptManagerInterceptor extends RequestInterceptor {
    private static final String TAG = ScriptManagerInterceptor.class.getSimpleName();
    private final Context mContext;
    
    private final ScriptManager mManager;

    public ScriptManagerInterceptor(Context context) {
        super(context);
        
        mContext = context;
        mManager = new MainCachedScriptManager(context);
    }

    public static ScriptManagerInterceptor create(Context context) {
        boolean success = CommonApplication.getPreferences().getBootSucceeded();

        return success ? new MainScriptManagerInterceptor(context) : new AltScriptManagerInterceptor(context);
    }

    @Override
    public boolean test(String url) {
        if (isFirstScript(url)) {
            return true;
        }

        if (isLastScript(url)) {
            return true;
        }

        if (isStyle(url)) {
            return true;
        }

        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Response response = getResponse(url);

        InputStream result = response.body().byteStream();

        if (isFirstScript(url)) {
            Log.d(TAG, "Begin onInitScripts");
            InputStream onInitScripts = mManager.getOnInitScripts();
            Log.d(TAG, "End onInitScripts");
            result = Helpers.appendStream(onInitScripts, result);
        }

        if (isLastScript(url)) {
            Log.d(TAG, "Begin onLoadScript");
            InputStream onLoadScripts = mManager.getOnLoadScripts();
            Log.d(TAG, "End onLoadScript");
            result = Helpers.appendStream(result, onLoadScripts);
        }

        if (isStyle(url)) {
            Log.d(TAG, "Begin onStyles");
            InputStream styles = mManager.getStyles();
            Log.d(TAG, "End onStyles");
            result = Helpers.appendStream(result, styles);
        }

        return createResponse(response.body().contentType(), result);
    }

    protected abstract boolean isFirstScript(String url);

    protected abstract boolean isLastScript(String url);

    protected abstract boolean isStyle(String url);
}
