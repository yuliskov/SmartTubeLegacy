package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.io.InputStream;

public class SimpleYouTubeInfoParser3 implements YouTubeInfoParser3 {
    private final String mContent;
    private final String mType;

    private class CombineMediaVisitor extends YouTubeInfoVisitor2 {
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

    public SimpleYouTubeInfoParser3(InputStream stream, String type) {
        mContent = Helpers.toString(stream);
        mType = type;
    }

    @Override
    public void setOnMediaFoundCallback(OnMediaFoundCallback mpdFoundCallback) {
        YouTubeInfoVisitable2 visitable = new SimpleYouTubeInfoVisitable2(mContent);
        YouTubeInfoVisitor2 visitor = new CombineMediaVisitor(mType, mpdFoundCallback);
        visitable.accept(visitor);
    }
}
