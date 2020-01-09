package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import android.content.Context;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;

public class ApplicationUtil {
    private static final UserAgentManager USER_AGENT_MANAGER = new UserAgentManager();

    public static DataSource.Factory buildDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(context, bandwidthMeter, buildHttpDataSourceFactory(context, bandwidthMeter));
    }

    //public static HttpDataSource.Factory buildHttpDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
    //    return new OkHttpDataSourceFactory(OkHttpHelpers.getOkHttpClient(), USER_AGENT_MANAGER.getUA(), bandwidthMeter);
    //}

    public static HttpDataSource.Factory buildHttpDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(USER_AGENT_MANAGER.getUA(), bandwidthMeter);
    }
}
