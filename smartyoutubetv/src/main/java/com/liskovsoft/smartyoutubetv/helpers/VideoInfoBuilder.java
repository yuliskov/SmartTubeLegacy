package com.liskovsoft.smartyoutubetv.helpers;

import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VideoInfoBuilder {
    private final InputStream mOriginStream;
    private List<Integer> mRemovedFormats = new ArrayList<>();
    private String mVideoInfo;

    public VideoInfoBuilder(InputStream stream) {
        mOriginStream = stream;
    }

    public void removeFormat(int itag) {
        mRemovedFormats.add(itag);
    }

    public InputStream get() {
        if (mRemovedFormats.isEmpty()) {
            return mOriginStream;
        }

        mVideoInfo = readAllStream();
        for (int itag : mRemovedFormats) {
            removeFormatFromString(itag);
        }
        return new ByteArrayInputStream(mVideoInfo.getBytes(Charset.forName("UTF8")));
    }

    private void removeFormatFromString(int itag) {
        Uri videoInfo = Uri.parse(mVideoInfo);
        String adaptiveFormats = videoInfo.getQueryParameter("adaptive_fmts");
        String adaptiveFormatsDecoded = Uri.decode(adaptiveFormats);
        String[] formats = adaptiveFormatsDecoded.split(",");
    }

    private String readAllStream() {
        Scanner s = new Scanner(mOriginStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }
}
