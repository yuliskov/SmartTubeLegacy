package com.liskovsoft.common.configparser;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.AssetHelper;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

import java.io.InputStream;

public class AssetPropertyParser implements ConfigParser {
    private static final String TAG = AssetPropertyParser.class.getSimpleName();
    private final Context mContext;
    private String mAssetName;
    private final InputStream mAssetStream;
    private FileBasedConfiguration mConfig;

    public AssetPropertyParser(Context context, InputStream assetStream) {
        mContext = context;
        mAssetStream = assetStream;

        initProps();
    }

    public AssetPropertyParser(Context context, String assetName) {
        this(context, AssetHelper.getAsset(context, assetName));
    }

    private void initProps() {
        try {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(
                                    params.properties()
                                    .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                            );
            mConfig = builder.getConfiguration();
            FileHandler fileHandler = new FileHandler(mConfig);
            fileHandler.load(mAssetStream);
        } catch (ConfigurationException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String get(String key) {
        if (mConfig == null) {
            return null;
        }

        return mConfig.getString(key);
    }

    @Override
    public String[] getArray(String key) {
        if (mConfig == null) {
            return null;
        }

        return mConfig.getStringArray(key);
    }
}
