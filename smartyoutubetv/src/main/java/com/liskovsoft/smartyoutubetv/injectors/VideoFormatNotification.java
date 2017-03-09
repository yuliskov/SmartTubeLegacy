package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.os.Handler;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.VideoFormatEvent;
import com.liskovsoft.smartyoutubetv.helpers.VideoFormat;
import com.squareup.otto.Subscribe;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class VideoFormatNotification extends ResourceInjectorBase {
    private final Context mContext;

    public VideoFormatNotification(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
        mContext = context;
    }

    @Subscribe
    public void onReceiveVideoFormats(VideoFormatEvent event) {
        Set<VideoFormat> formats = event.getSupportedFormats();
        VideoFormat selected = event.getSelectedFormat();
        formats = excludeFormats(formats);
        
        if (selected == null || !formats.contains(selected)) {
            selected = VideoFormat._Auto_;
        }
        final String jsonFormatList = toJSON(formats, selected);

        Handler mainHandler = new Handler(mContext.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                injectJSContent("fireEvent(" + jsonFormatList + ", 'videoformats')");
            }
        });
    }

    private Set<VideoFormat> excludeFormats(Set<VideoFormat> formats) {
        // remain no more than four position
        // formats to remove: 240p, 480p

        // get rid off java.util.Collections$UnmodifiableCollection
        TreeSet<VideoFormat> sortedSet = new TreeSet<>(formats);
        sortedSet.add(VideoFormat._Auto_);

        sortedSet.remove(VideoFormat._240p_);
        sortedSet.remove(VideoFormat._480p_);
        if (sortedSet.size() > 5) {
            sortedSet.remove(VideoFormat._144p_);
        }
        if (sortedSet.size() > 5) {
            sortedSet.remove(VideoFormat._360p_);
        }
        return sortedSet;
    }

    private String toJSON(Set<VideoFormat> formats, VideoFormat selected) {
        String result = "[";
        for (VideoFormat format : formats) {
            result += "{";
            result += "name: '" + format.toString() + "'";
            if (format == selected) {
                result += ", selected: true";
            }
            result += "}";

            result += ", ";
        }
        result += "]";
        return result;
    }
}
