package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import android.content.Context;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource.BaseFactory;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.misc.HeaderManager;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ApplicationUtil {
    private static final UserAgentManager USER_AGENT_MANAGER = new UserAgentManager();
    private static final List<String> EXO_HEADERS = Arrays.asList("Origin", "Referer", "User-Agent", "Accept-Language", "Accept", "X-Client-Data");

    public static DataSource.Factory buildDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(context, bandwidthMeter, buildHttpDataSourceFactory(context, bandwidthMeter));
    }

    //public static HttpDataSource.Factory buildHttpDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
    //    OkHttpDataSourceFactory dataSourceFactory = new OkHttpDataSourceFactory(OkHttpHelpers.getOkHttpClient(), USER_AGENT_MANAGER.getUA(),
    //            bandwidthMeter);
    //    addCommonHeaders(context, dataSourceFactory);
    //    return dataSourceFactory;
    //}

    public static HttpDataSource.Factory buildHttpDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(USER_AGENT_MANAGER.getUA(), bandwidthMeter);
        addCommonHeaders(context, dataSourceFactory); // cause troubles for some users
        return dataSourceFactory;
    }

    private static void addCommonHeaders(Context context, BaseFactory dataSourceFactory) {
        HeaderManager headerManager = new HeaderManager(context);
        HashMap<String, String> headers = headerManager.getHeaders();

        // NOTE: "Accept-Encoding" should set to "identity" or not present

        for (String header : headers.keySet()) {
            if (EXO_HEADERS.contains(header)) {
                dataSourceFactory.getDefaultRequestProperties().set(header, headers.get(header));
            }
        }
    }
}
