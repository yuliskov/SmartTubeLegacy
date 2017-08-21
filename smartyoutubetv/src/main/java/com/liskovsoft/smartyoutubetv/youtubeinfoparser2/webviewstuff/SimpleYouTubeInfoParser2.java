package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.io.InputStream;
import java.util.Scanner;

public class SimpleYouTubeInfoParser2 implements YouTubeInfoParser2 {
    private final String mContent;
    private UrlFoundCallback mUrlFoundCallback;

    private class FindUriVisitor extends YouTubeInfoVisitor2 {
        private final YouTubeMediaItem mOriginItem;
        private YouTubeMediaItem mLastItem;

        FindUriVisitor(String iTag) {
            mOriginItem = new SimpleYouTubeMediaItem(iTag);
        }

        @Override
        public void onMediaItem(YouTubeMediaItem mediaItem) {
            if (mediaItem.compareTo(mOriginItem) <= 0 && mediaItem.compareTo(mLastItem) > 0) {
                mLastItem = mediaItem;
            }
        }

        @Override
        public void doneVisiting() {
            mUrlFoundCallback.onUrlFound(getUri());
        }

        public Uri getUri() {
            if (mLastItem == null) {
                return Uri.parse("");
            }
            return Uri.parse(mLastItem.getUrl());
        }
    }

    private class CombineMPDPlaylistVisitor extends YouTubeInfoVisitor2 {
        private final String mType;
        private final MPDFoundCallback mMpdFoundCallback;
        private MyMPDBuilder mMPDBuilder;

        public CombineMPDPlaylistVisitor(String type, MPDFoundCallback mpdFoundCallback) {
            mType = type;
            mMpdFoundCallback = mpdFoundCallback;
        }

        @Override
        public void onGenericInfo(YouTubeGenericInfo info) {
            mMPDBuilder = new MyMPDBuilder(info);
        }

        @Override
        public void onMediaItem(YouTubeMediaItem mediaItem) {
            if (mediaItem.belongsToType(mType)) {
                mMPDBuilder.append(mediaItem);
            }
        }

        @Override
        public void doneVisiting() {
            //mMpdFoundCallback.onFound(Helpers.toStream("Hello World"));
            mMpdFoundCallback.onFound(mMPDBuilder.build());
        }
    }

    public SimpleYouTubeInfoParser2(InputStream stream) {
        mContent = readStream(stream);
    }

    private String readStream(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }

    public SimpleYouTubeInfoParser2(String content) {
        mContent = content;
    }

    @Override
    public void getUrlByTag(String iTag, UrlFoundCallback urlFoundCallback) {
        mUrlFoundCallback = urlFoundCallback;
        YouTubeInfoVisitable2 visitable = new SimpleYouTubeInfoVisitable2(mContent);
        FindUriVisitor visitor = new FindUriVisitor(iTag);
        visitable.accept(visitor);
    }

    @Override
    public void getMPDByCodec(String type, MPDFoundCallback mpdFoundCallback) {
        YouTubeInfoVisitable2 visitable = new SimpleYouTubeInfoVisitable2(mContent);
        YouTubeInfoVisitor2 visitor = new CombineMPDPlaylistVisitor(type, mpdFoundCallback);
        visitable.accept(visitor);
    }
}
