package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder.SimpleMPDBuilder;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.SimpleYouTubeInfoVisitable;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeSubParser.Subtitle;
import com.liskovsoft.smartyoutubetv.misc.Helpers;

import java.io.InputStream;

public class SimpleYouTubeInfoParser implements YouTubeInfoParser {
    private final String[] mContent;

    private class MergeMediaVisitor extends YouTubeInfoVisitor {
        private final OnMediaFoundCallback mMediaFoundCallback;
        private SimpleMPDBuilder mMPDBuilder;
        private int mCounter = 1;
        private GenericInfo mInfo;
        private Uri mHlsUrl;

        public MergeMediaVisitor(OnMediaFoundCallback mediaFoundCallback) {
            mMediaFoundCallback = mediaFoundCallback;
        }

        @Override
        public void onGenericInfo(GenericInfo info) {
            if (mMPDBuilder == null)
                mMPDBuilder = new SimpleMPDBuilder(info);

            mInfo = info;
        }

        @Override
        public void onMediaItem(MediaItem mediaItem) {
            mMPDBuilder.append(mediaItem);
        }

        @Override
        public void onSubItem(Subtitle item) {
            mMPDBuilder.append(item);
        }

        @Override
        public void onLiveItem(Uri hlsUrl) {
            mHlsUrl = hlsUrl;
        }

        @Override
        public void doneVisiting() {
            if (mCounter != mContent.length) {
                mCounter++;
                return;
            }

            // callback on the last loop (resolve problems with async processing)

            if (mInfo != null)
                mMediaFoundCallback.onInfoFound(mInfo);
            if (mHlsUrl != null)
                mMediaFoundCallback.onLiveUrlFound(mHlsUrl);
            if (!mMPDBuilder.isEmpty())
                mMediaFoundCallback.onDashMPDFound(mMPDBuilder.build());
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
            YouTubeInfoVisitable visitable = new SimpleYouTubeInfoVisitable(content);
            visitable.accept(visitor);
        }
    }
}
