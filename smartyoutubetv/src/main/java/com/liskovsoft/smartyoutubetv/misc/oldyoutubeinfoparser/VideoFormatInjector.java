package com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.events.VideoFormatEvent;
import com.liskovsoft.smartyoutubetv.injectors.ResourceInjectorBase;
import com.squareup.otto.Subscribe;

import java.util.Set;
import java.util.TreeSet;

public class VideoFormatInjector extends ResourceInjectorBase {
    private final Context mContext;

    public VideoFormatInjector(Context context) {
        this(context, null);
    }

    public VideoFormatInjector(Context context, WebView webView) {
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

        injectJSContentUnicode("if(fireEvent){fireEvent(" + jsonFormatList + ", 'videoformats')}");
    }

    private Set<VideoFormat> excludeFormats(Set<VideoFormat> formats) {
        // remain no more than four position
        // formats to remove: 240p, 480p

        // get rid off java.util.Collections$UnmodifiableCollection
        TreeSet<VideoFormat> sortedSet = new TreeSet<>(formats);
        sortedSet.add(VideoFormat._Auto_);

        sortedSet.remove(VideoFormat._720p60_);
        sortedSet.remove(VideoFormat._1080p60_);
        sortedSet.remove(VideoFormat._1440p_);
        sortedSet.remove(VideoFormat._1440p60_);
        sortedSet.remove(VideoFormat._2160p);
        sortedSet.remove(VideoFormat._2160p60);
        if (sortedSet.size() > 5) {
            sortedSet.remove(VideoFormat._240p_);
        }
        if (sortedSet.size() > 5) {
            sortedSet.remove(VideoFormat._480p_);
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
