package com.liskovsoft.common.configparser;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.AssetHelper;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AssetPropertyParser2 implements ConfigParser {
    private static final String TAG = AssetPropertyParser2.class.getSimpleName();
    private final Context mContext;
    private final InputStream mAssetStream;
    private Properties mProperties;

    public AssetPropertyParser2(Context context, InputStream assetStream) {
        mContext = context;
        mAssetStream = assetStream;

        initProperties();
    }

    public AssetPropertyParser2(Context context, String assetName) {
        this(context, AssetHelper.getAsset(context, assetName));
    }

    private void initProperties() {
        if (mProperties != null) {
            return;
        }
        try {
            mProperties = new Properties();
            mProperties.load(mAssetStream);
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String get(String key) {
        if (mProperties == null) {
            return null;
        }

        return mProperties.getProperty(key);
    }

    @Override
    public String[] getArray(String key) {
        if (mProperties == null) {
            return null;
        }

        String arrProp = mProperties.getProperty(key);
        return arrProp.split(" +");
    }
}
