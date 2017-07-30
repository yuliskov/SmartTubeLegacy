package com.liskovsoft.smartyoutubetv.youtubeinfoparser;

import android.net.Uri;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class SimpleYouTubeInfoParser implements YouTubeInfoParser {
    private final String mContent;

    private class UriVisitor implements YouTubeInfoVisitor {
        private final String mITag;
        private String mResult;

        UriVisitor(String iTag) {
            mITag = iTag;
        }

        @Override
        public void visitMediaItem(YouTubeMediaItem mediaItem) {
            if (mediaItem.getITag().equals(mITag)) {
                mResult = mediaItem.getUrl();
            }
        }

        public Uri getUri() {
            return Uri.parse(mResult);
        }
    }

    public SimpleYouTubeInfoParser(InputStream stream) {
        mContent = readStream(stream);
    }

    private String readStream(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }

    public SimpleYouTubeInfoParser(String content) {
        mContent = content;
    }

    @Override
    public List<String> getAllVideoLinks() {
        return null;
    }

    @Override
    public Uri getUrlByTag(String iTag) {
        SimpleYouTubeInfoVisitable visitable = new SimpleYouTubeInfoVisitable(mContent);
        UriVisitor visitor = new UriVisitor(iTag);
        visitable.accept(visitor);
        return visitor.getUri();
    }
}
