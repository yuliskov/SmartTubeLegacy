package com.liskovsoft.smartyoutubetv.common.prefs;

import android.content.Context;
import com.liskovsoft.common.configparser.AssetPropertyParser;

public class CommonParams {
    private final Context mContext;
    private final AssetPropertyParser mParser;

    public CommonParams(Context context) {
        mContext = context;
        mParser = new AssetPropertyParser(context, "ads.properties");
    }

    public String[] getAdUrls() {
        return mParser.getArray("ads.url");
    }
}
