package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.hls.UrlListBuilder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.hls.SimpleUrlListBuilder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd.MPDBuilder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd.SimpleMPDBuilder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeSubParser.Subtitle;
import com.liskovsoft.sharedutils.helpers.Helpers;

import java.io.InputStream;

public class SimpleYouTubeInfoParser implements YouTubeInfoParser {
    private final String[] mContent;

    private class MergeMediaVisitor extends YouTubeInfoVisitor {
        private final OnMediaFoundCallback mMediaFoundCallback;
        private MPDBuilder mMPDBuilder;
        private UrlListBuilder mUrlListBuilder;
        private int mCounter = 1;
        private GenericInfo mInfo;
        private Uri mHlsUrl;
        private Uri mDashUrl;
        private Uri mTrackingUrl;

        public MergeMediaVisitor(OnMediaFoundCallback mediaFoundCallback) {
            mMediaFoundCallback = mediaFoundCallback;
        }

        @Override
        public void onGenericInfo(GenericInfo info) {
            if (mMPDBuilder == null) {
                mMPDBuilder = new SimpleMPDBuilder(info);
            }

            if (mUrlListBuilder == null) {
                mUrlListBuilder = new SimpleUrlListBuilder();
            }

            mInfo = info;
        }

        @Override
        public void onMediaItem(MediaItem mediaItem) {
            mMPDBuilder.append(mediaItem);

            mUrlListBuilder.append(mediaItem);
        }

        @Override
        public void onSubItem(Subtitle item) {
            mMPDBuilder.append(item);
        }

        @Override
        public void onHlsUrl(Uri url) {
            mHlsUrl = url;
        }

        @Override
        public void onDashUrl(Uri url) {
            mDashUrl = url;
        }

        @Override
        public void onTrackingUrl(Uri url) {
            mTrackingUrl = url;
        }

        @Override
        public void doneVisiting() {
            if (mCounter != mContent.length) {
                mCounter++;
                return;
            }

            // callback on the last loop (resolve problems with async processing)

            if (mInfo != null) {
                mMediaFoundCallback.onInfoFound(mInfo);
            }

            if (mTrackingUrl != null) {
                mMediaFoundCallback.onTrackingUrlFound(mTrackingUrl);
            }

            if (mHlsUrl != null) { // live stream usually
                mMediaFoundCallback.onHLSFound(mHlsUrl);
            } else if (mDashUrl != null) { // dash live stream, contains more formats
                mMediaFoundCallback.onDashUrlFound(mDashUrl);
            } else if (!mMPDBuilder.isEmpty()) {
                mMediaFoundCallback.onDashMPDFound(mMPDBuilder.build());
            } else if (!mUrlListBuilder.isEmpty()) { // fallback to the simple formats
                mMediaFoundCallback.onUrlListFound(mUrlListBuilder.buildUriList());
            }

            mMediaFoundCallback.onDone();
        }
    }

    /**
     * One or multiple <em>get_video_info</em> files as a source
     * @param content get_video_info content
     */
    public SimpleYouTubeInfoParser(InputStream ...content) {
        mContent = new String[content.length];
        readContent(content);
    }

    private void readContent(InputStream[] content) {
        for (int i = 0; i < content.length; i++) {
            mContent[i] = Helpers.toString(content[i]);
        }
    }

    @Override
    public void parse(OnMediaFoundCallback mpdFoundCallback) {
        YouTubeInfoVisitor visitor = new MergeMediaVisitor(mpdFoundCallback);

        for (String content : mContent) {
            if (content == null)
                continue;

            YouTubeInfoVisitable visitable = new SimpleYouTubeInfoManager(content);
            visitable.accept(visitor);
        }
    }
}
