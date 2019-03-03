package com.liskovsoft.sharedutils.configparser;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class AssetPropertyParser2 implements ConfigParser {
    private static final String TAG = AssetPropertyParser2.class.getSimpleName();
    private static final String ARRAY_DELIM_REGEX = " +";
    private final Context mContext;
    private final InputStream mAssetStream;
    private Properties mProperties;

    public AssetPropertyParser2(Context context, String... assetName) {
        this(context, AssetHelper.getAssetMerged(context, Arrays.asList(assetName)));
    }

    public AssetPropertyParser2(Context context, InputStream assetStream) {
        mContext = context;
        mAssetStream = assetStream;

        initProperties();
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

        String val = mProperties.getProperty(key);

        if (val == null) {
            Log.e(TAG, key + " not found");
        }

        return val;
    }

    @Override
    public String[] getArray(String key) {
        if (mProperties == null) {
            return null;
        }

        Set<String> names = mProperties.stringPropertyNames();

        List<String> valArr = new ArrayList<>();

        for (String name : names) {
            if (name.startsWith(key) &&
                !name.equals(key)) { // only partial match for arrays
                valArr.add(mProperties.getProperty(name));
            }
        }

        if (valArr.size() != 0) {
            return valArr.toArray(new String[]{});
        }

        String arrProp = mProperties.getProperty(key);

        if (arrProp == null) {
            Log.e(TAG, key + " not found");
            return null;
        }

        return arrProp.split(ARRAY_DELIM_REGEX);
    }
}
