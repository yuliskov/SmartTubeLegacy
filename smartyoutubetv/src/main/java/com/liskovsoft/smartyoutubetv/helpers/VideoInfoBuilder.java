package com.liskovsoft.smartyoutubetv.helpers;

import android.net.Uri;
import com.liskovsoft.browser.xwalk.XWalkInitHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*

Video quality matrix:

format code  extension  resolution note
249          webm       audio only DASH audio   58k , opus @ 50k (48000Hz), 1.91MiB
250          webm       audio only DASH audio   90k , opus @ 70k (48000Hz), 2.64MiB
140          m4a        audio only DASH audio  128k , m4a_dash container, mp4a.40.2@128k (44100Hz), 4.67MiB
251          webm       audio only DASH audio  165k , opus @160k (48000Hz), 5.14MiB
171          webm       audio only DASH audio  171k , vorbis@128k (44100Hz), 5.01MiB
160          mp4        256x144    DASH video  110k , avc1.4d400c, 30fps, video only, 3.85MiB
278          webm       256x144    DASH video  113k , webm container, vp9, 30fps, video only, 3.47MiB
133          mp4        426x240    DASH video  274k , avc1.4d4015, 30fps, video only, 8.60MiB
242          webm       426x240    DASH video  275k , vp9, 30fps, video only, 7.39MiB
243          webm       640x360    DASH video  514k , vp9, 30fps, video only, 13.66MiB
134          mp4        640x360    DASH video  632k , avc1.4d401e, 30fps, video only, 14.50MiB
244          webm       854x480    DASH video  943k , vp9, 30fps, video only, 24.52MiB
135          mp4        854x480    DASH video 1153k , avc1.4d401f, 30fps, video only, 28.44MiB
247          webm       1280x720   DASH video 1828k , vp9, 30fps, video only, 49.38MiB
136          mp4        1280x720   DASH video 2299k , avc1.4d401f, 30fps, video only, 55.05MiB
248          webm       1920x1080  DASH video 3239k , vp9, 30fps, video only, 87.46MiB
137          mp4        1920x1080  DASH video 4385k , avc1.640028, 30fps, video only, 102.19MiB
271          webm       2560x1440  DASH video 10210k , vp9, 30fps, video only, 270.64MiB
264          mp4        2560x1440  DASH video 12166k , avc1.640032, 30fps, video only, 273.52MiB
266          mp4        3840x2160  DASH video 23868k , avc1.640033, 30fps, video only, 669.87MiB
138          mp4        3840x2160  DASH video 24322k , avc1.640033, 30fps, video only, 573.82MiB
313          webm       3840x2160  DASH video 31379k , vp9, 30fps, video only, 678.65MiB
17           3gp        176x144    small , mp4v.20.3,  mp4a.40.2@ 24k
36           3gp        320x180    small , mp4v.20.3,  mp4a.40.2
43           webm       640x360    medium , vp8.0,  vorbis@128k
18           mp4        640x360    medium , avc1.42001E,  mp4a.40.2@ 96k
22           mp4        1280x720   hd720 , avc1.64001F,  mp4a.40.2@192k (best)


*/

public class VideoInfoBuilder {
    private final InputStream mOriginStream;
    private List<Integer> mRemovedFormats = new ArrayList<>();
    private String mVideoInfo;
    private Integer[] mSDItags = {160, 278, 133, 242, 243, 134, 244, 135};
    private Integer[] mHDItags = {247, 136, 248, 137};
    private Integer[] m4KITags = {271, 264, 266, 138, 313};
    private Integer[] mAllITags = {160, 278, 133, 242, 243, 134, 244, 135, 247, 136, 248, 137, 271, 264, 266, 138, 313};
    private boolean mEnable4K;

    public VideoInfoBuilder(InputStream stream) {
        mOriginStream = stream;
    }

    public void removeFormat(int itag) {
        mRemovedFormats.add(itag);
    }

    public void removeSDFormats() {
        for (int itag : mSDItags) {
            mRemovedFormats.add(itag);
        }
    }
    
    public void removeHDFormats() {
        for (int itag : mHDItags) {
            mRemovedFormats.add(itag);
        }
    }

    public void enable4K() {
        //removeSDFormats();
        //removeHDFormats();
        mEnable4K = true;
    }

    public InputStream get() {
        readAllContent();

        removeSelectedFormats();

        replaceHDFormatsWith4KFormats();
        
        return new ByteArrayInputStream(mVideoInfo.getBytes(Charset.forName("UTF-8")));
    }

    private void removeSelectedFormats() {
        for (int itag : mRemovedFormats) {
            removeFormatFromContent(itag);
        }
    }

    private void replaceHDFormatsWith4KFormats() {
        if (!mEnable4K) {
            return;
        }
        mVideoInfo = mVideoInfo.replace(String.valueOf(138), String.valueOf(137));
        mVideoInfo = mVideoInfo.replace(String.valueOf(264), String.valueOf(136));
    }

    private void removeFormatFromContent(int itag) {
        Uri videoInfo = Uri.parse("http://example.com?" + mVideoInfo);
        String adaptiveFormats = videoInfo.getQueryParameter("adaptive_fmts");
        String[] formats = adaptiveFormats.split(",");
        for (String format : formats) {
            if (format.contains("itag=" + itag)) {
                String encode = Uri.encode(format);
                mVideoInfo = mVideoInfo.replace(encode + "%2C", "");
                mVideoInfo = mVideoInfo.replace("%2C" + encode, "");
            }
        }
    }

    private void readAllContent() {
        Scanner s = new Scanner(mOriginStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        mVideoInfo = result;
    }

    public void setMaxFormat(String itagBoundry) {
        if (itagBoundry == null) {
            return;
        }

        int itagBoundryInt = Integer.parseInt(itagBoundry);

        boolean meetBoundry = false;

        // remove formats with quality above others
        for (int itag : mAllITags) {
            if (meetBoundry)
                mRemovedFormats.add(itag);
            if (itag == itagBoundryInt)
                meetBoundry = true;
        }
    }

    public void switchToFormat(String itagsWithDelimeters) {
        if (itagsWithDelimeters == null) {
            return;
        }

        String[] itags = itagsWithDelimeters.split(",");

        List<Integer> retainedFormats = new ArrayList<>();
        for (String itag : itags) {
            retainedFormats.add(Integer.parseInt(itag.trim()));
        }

        mRemovedFormats.addAll(Arrays.asList(mSDItags));
        mRemovedFormats.addAll(Arrays.asList(mHDItags));
        mRemovedFormats.addAll(Arrays.asList(m4KITags));

        mRemovedFormats.removeAll(retainedFormats);
    }

    public String getSupportedFormats() {
        return null;
    }
}
