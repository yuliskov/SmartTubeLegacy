package com.liskovsoft.common.propertyparser;

import android.content.Context;
import android.content.res.AssetManager;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyParser {
    private final Context mContext;
    private final String mAssetName;
    private Properties mProperties;

    public PropertyParser(String assetName, Context context) {
        mAssetName = assetName;
        mContext = context;

        initProperties();
    }

    private void initProperties() {
        if (mProperties != null) {
            return;
        }

        try {
            mProperties = new Properties();
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open(mAssetName);
            mProperties.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    //private void initProps() {
    //    Parameters params = new Parameters();
    //    FileBasedConfigurationBuilder<Configuration> builder =
    //            new FileBasedConfigurationBuilder<Configuration>(PropertiesConfiguration.class)
    //                    .configure(params.properties()
    //                            .setFileName("usergui.properties")
    //                            .setListDelimiterHandler(new DefaultListDelimiterHandler(','));
    //    Configuration config = builder.getConfiguration();
    //}

    public String getProperty(String key) {
        if (mProperties == null) {
            return null;
        }

        return mProperties.getProperty(key);
    }
}
