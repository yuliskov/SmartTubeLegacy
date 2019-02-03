package com.liskovsoft.smartyoutubetv.common.prefs;

import android.content.Context;
import com.liskovsoft.common.configparser.AssetPropertyParser2;
import com.liskovsoft.common.configparser.ConfigParser;

public class CommonParams {
    private final Context mContext;
    private final ConfigParser mParser;

    public CommonParams(Context context) {
        mContext = context;
        mParser = new AssetPropertyParser2(context, "ads2.properties");
    }

    public String[] getAdUrls() {
        return mParser.getArray("ads.urls");
    }
}
