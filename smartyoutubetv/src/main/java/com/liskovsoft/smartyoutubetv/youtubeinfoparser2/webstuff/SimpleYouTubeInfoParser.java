package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.MyMPDBuilder;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.io.InputStream;

public class SimpleYouTubeInfoParser implements YouTubeInfoParser {
    private final String mContent;
    private final String mType;

    private class CombineMediaVisitor extends YouTubeInfoVisitor {
        private final String mType;
        private final OnMediaFoundCallback mMediaFoundCallback;
        private MyMPDBuilder mMPDBuilder;

        public CombineMediaVisitor(String type, OnMediaFoundCallback mediaFoundCallback) {
            mType = type;
            mMediaFoundCallback = mediaFoundCallback;
        }

        @Override
        public void onGenericInfo(YouTubeGenericInfo info) {
            mMPDBuilder = new MyMPDBuilder(info);
            mMediaFoundCallback.onInfoFound(info);
        }

        @Override
        public void onMediaItem(YouTubeMediaItem mediaItem) {
            if (mediaItem.belongsToType(mType)) {
                mMPDBuilder.append(mediaItem);
            }
        }

        @Override
        public void onLiveItem(Uri hlsUrl) {
            mMediaFoundCallback.onLiveFound(hlsUrl);
        }

        @Override
        public void doneVisiting() {
            mMediaFoundCallback.onVideoFound(mMPDBuilder.build());
        }
    }

    public SimpleYouTubeInfoParser(InputStream stream, String type) {
        mContent = Helpers.toString(stream);
        mType = type;
    }

    @Override
    public void setOnMediaFoundCallback(OnMediaFoundCallback mpdFoundCallback) {
        YouTubeInfoVisitable visitable = new SimpleYouTubeInfoVisitable(mContent);
        YouTubeInfoVisitor visitor = new CombineMediaVisitor(mType, mpdFoundCallback);
        visitable.accept(visitor);
    }
}
