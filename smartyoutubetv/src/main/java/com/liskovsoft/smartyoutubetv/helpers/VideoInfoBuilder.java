package com.liskovsoft.smartyoutubetv.helpers;

import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
        return new ByteArrayInputStream(mVideoInfo.getBytes(Charset.forName("UTF-8")));
    }

    private void removeFormatFromString(int itag) {
        Uri videoInfo = Uri.parse("http://example.com?" + mVideoInfo);
        String adaptiveFormats = videoInfo.getQueryParameter("adaptive_fmts");
        //String adaptiveFormatsDecoded = decode(adaptiveFormats);
        String[] formats = adaptiveFormats.split(",");
        for (String format : formats) {
            if (format.contains("itag=" + itag)) {
                String encode = Uri.encode(format);
                mVideoInfo = mVideoInfo.replace(encode + "%2C", "");
                mVideoInfo = mVideoInfo.replace("%2C" + encode, "");
            }
        }
    }

    private String readAllStream() {
        Scanner s = new Scanner(mOriginStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }
}
