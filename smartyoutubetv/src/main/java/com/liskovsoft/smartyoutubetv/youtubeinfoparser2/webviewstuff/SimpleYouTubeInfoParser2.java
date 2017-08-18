package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.ITag;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.io.InputStream;
import java.util.Scanner;

public class SimpleYouTubeInfoParser2 implements YouTubeInfoParser2 {
    private final String mContent;
    private UrlFoundCallback mUrlFoundCallback;

    private class FindUriVisitor implements YouTubeInfoVisitor2 {
        private final YouTubeMediaItem mOriginItem;
        private YouTubeMediaItem mLastItem;

        FindUriVisitor(String iTag) {
            mOriginItem = new SimpleYouTubeMediaItem(iTag);
        }

        @Override
        public void visitMediaItem(YouTubeMediaItem mediaItem) {
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

    private class CombineMPDPlaylistVisitor implements YouTubeInfoVisitor2 {
        private final String mType;
        private final MPDPlaylistFoundCallback mMpdPlaylistFoundCallback;
        private final MyMPDBuilder mMPDBuilder;

        public CombineMPDPlaylistVisitor(String type, MPDPlaylistFoundCallback mpdPlaylistFoundCallback) {
            mType = type;
            mMpdPlaylistFoundCallback = mpdPlaylistFoundCallback;
            mMPDBuilder = new MyMPDBuilder();
        }

        @Override
        public void visitMediaItem(YouTubeMediaItem mediaItem) {
            if (mediaItem.belongsToType(ITag.AVC)) {
                mMPDBuilder.append(mediaItem);
            }
        }

        @Override
        public void doneVisiting() {
            mMpdPlaylistFoundCallback.onFound(mMPDBuilder.build());
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
    public void getMPDPlaylist(String type, MPDPlaylistFoundCallback mpdPlaylistFoundCallback) {
        YouTubeInfoVisitable2 visitable = new SimpleYouTubeInfoVisitable2(mContent);
        YouTubeInfoVisitor2 visitor = new CombineMPDPlaylistVisitor(type, mpdPlaylistFoundCallback);
        visitable.accept(visitor);
    }
}
