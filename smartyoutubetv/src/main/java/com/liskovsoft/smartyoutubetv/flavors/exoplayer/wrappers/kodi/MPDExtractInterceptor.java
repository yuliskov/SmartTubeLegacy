package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.kodi;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.SimpleYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public abstract class MPDExtractInterceptor extends RequestInterceptor {
    private final Context mContext;
    private static final Logger sLogger = LoggerFactory.getLogger(MPDExtractInterceptor.class);
    private InputStream mResponseStream30Fps;
    private InputStream mResponseStream60Fps;
    private String mCurrentUrl;

    public MPDExtractInterceptor(Context context) {
        super(context);
        
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        mCurrentUrl = url;
        prepareResponseStream(url);
        parseAndOpenExoPlayer();
        return null;
    }

    // We also try looking in get_video_info since it may contain different dashmpd
    // URL that points to a DASH manifest with possibly different itag set (some itags
    // are missing from DASH manifest pointed by webpage's dashmpd, some - from DASH
    // manifest pointed by get_video_info's dashmpd).
    // The general idea is to take a union of itags of both DASH manifests (for example
    // video with such 'manifest behavior' see https://github.com/rg3/youtube-dl/issues/6093)
    private void prepareResponseStream(String url) {
        Response response30Fps = OkHttpHelpers.doOkHttpRequest(unlockRegularFormats(url));
        Response response60Fps = OkHttpHelpers.doOkHttpRequest(unlock60FpsFormats(url));
        mResponseStream30Fps = response30Fps == null ? null : response30Fps.body().byteStream();
        mResponseStream60Fps = response60Fps == null ? null : response60Fps.body().byteStream();
    }

    private void parseAndOpenExoPlayer() {
        final YouTubeInfoParser dataParser = new SimpleYouTubeInfoParser(mResponseStream60Fps, mResponseStream30Fps);
        dataParser.parse(new OnMediaFoundCallback() {
            private Uri mHlsUrl;
            private InputStream mMpdContent;
            private GenericInfo mInfo;
            @Override
            public void onDashMPDFound(final InputStream mpdContent) {
                mMpdContent = mpdContent;
            }
            @Override
            public void onHLSFound(final Uri hlsUrl) {
                mHlsUrl = hlsUrl;
            }

            @Override
            public void onInfoFound(GenericInfo info) {
                mInfo = info;
            }

            @Override
            public void onDone() {
                if (mMpdContent != null) {
                    MPDExtractInterceptor.this.onDashMPDFound(mMpdContent);
                } else if (mHlsUrl != null) {
                    MPDExtractInterceptor.this.onLiveUrlFound(mHlsUrl);
                }
            }
        });
    }

    protected abstract void onDashMPDFound(InputStream mpdContent);

    protected abstract void onLiveUrlFound(Uri hlsUrl);

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     * @param url
     * @return
     */
    protected String unlockRegularFormats(String url) {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);
        
        query.set("c", "HTML5");

        return query.toString();
    }

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     * @param url
     * @return
     */
    protected String unlock60FpsFormats(String url) {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);

        query.set("el", "info"); // unlock dashmpd url
        query.set("ps", "default"); // unlock 60fps formats

        return query.toString();
    }

    /**
     * Unlocking most of 4K mp4 formats.
     * It is done by removing c=TVHTML5 query param.
     * @param url
     * @return
     */
    protected String unlock30FpsFormats(String url) {
        MyUrlEncodedQueryString query = MyUrlEncodedQueryString.parse(url);

        query.set("el", "info"); // unlock dashmpd url

        return query.toString();
    }
}
