package com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser;

import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

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

public class YouTubeVideoInfoBuilder implements VideoInfoBuilder {
    private final InputStream mOriginStream;
    private List<Integer> mRemovedFormats = new ArrayList<>();
    private Set<VideoFormat> mSupportedFormats = new TreeSet<>(new Comparator<VideoFormat>() {
        @Override
        public int compare(VideoFormat format1, VideoFormat format2) {
            return format1.getResolution() - format2.getResolution();
        }
    });
    private final String mVideoInfo;
    private String mResultVideoInfo;
    private VideoFormat mSelectedFormat;

    public YouTubeVideoInfoBuilder(InputStream stream) {
        mOriginStream = stream;

        Scanner s = new Scanner(mOriginStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        mVideoInfo = result;
        mResultVideoInfo = mVideoInfo;

        initSupportedFormats();
    }

    private void removeFormat(int itag) {
        mRemovedFormats.add(itag);
    }


    @Override
    public void removeFormat(VideoFormat format) {
        int[] iTags = format.getITags();
        for (int iTag : iTags) {
            removeFormat(iTag);
        }
    }

    @Override
    public InputStream get() {
        removeSelectedFormats();

        return new ByteArrayInputStream(mResultVideoInfo.getBytes(Charset.forName("UTF-8")));
    }

    private void fix4KVideo() {
        if (isNot4KVideo()) {
            return;
        }

        int[] iTags = mSelectedFormat.getITags();

        // pretend that there 720p video
        for (int i = 0; i < iTags.length; i++) {
            // itag%3D271 == itag=271
            mResultVideoInfo = mResultVideoInfo.replace("itag%3D" + iTags[i], "itag%3D" + VideoFormat._720p_.getITags()[i]);
            // itag%253D271 == itag%3D271
            mResultVideoInfo = mResultVideoInfo.replace("itag%253D" + iTags[i], "itag%253D" + VideoFormat._720p_.getITags()[i]);
            // size%3D2560x1440 == size=2560x1440
        }
        mResultVideoInfo = mResultVideoInfo.replace("size%3D2560x1440", "size%3D1280x720");
        mResultVideoInfo = mResultVideoInfo.replace("size%3D3840x2160", "size%3D1280x720");
    }

    private boolean isNot4KVideo() {
        if (mSelectedFormat == null)
            return true;
        return mSelectedFormat.getResolution() < VideoFormat._1440p_.getResolution();
    }

    private void removeSelectedFormats() {
        for (int iTag : mRemovedFormats) {
            removeFormatFromContent(iTag);
        }

        //fix4KVideo();
    }

    private void removeFormatFromContent(int itag) {
        String[] formats = getSupportedFormats(mResultVideoInfo);
        for (String format : formats) {
            if (format.contains("itag=" + itag)) {
                String encode = Uri.encode(format);
                // append comma (format separator) to format specification
                mResultVideoInfo = mResultVideoInfo
                        .replace(encode + "%2C", "")
                        .replace("%2C" + encode, "");
            }
        }
    }

    private String[] getSupportedFormats(String query) {
        Uri videoInfo = Uri.parse("http://example.com?" + query);
        String adaptiveFormats = videoInfo.getQueryParameter("adaptive_fmts");
        // stream may not contain dash formats
        if (adaptiveFormats == null) {
            return new String[]{};
        }
        return adaptiveFormats.split(",");
    }

    @Override
    public Set<VideoFormat> getSupportedFormats() {
        if (mSupportedFormats.size() != 0) {
            return mSupportedFormats;
        }

        return mSupportedFormats;
    }

    private void initSupportedFormats() {
        String[] formats = getSupportedFormats(mVideoInfo);

        for (String format : formats) {
            String iTag = findITagValue(format);
            VideoFormat fmt = VideoFormat.fromITag(iTag);
            if (fmt == null) { // pass audio formats
                continue;
            }
            mSupportedFormats.add(fmt);
        }

        mSupportedFormats = Collections.unmodifiableSet(mSupportedFormats);
    }

    private String findITagValue(String format) {
        Uri videoInfo = Uri.parse("http://example.com?" + format);
        String itag = videoInfo.getQueryParameter("itag");
        return itag;
    }

    /**
     * Removes all video formats but one that supplied.
     * Also all audio formats remain untouched.
     * @param format
     * @return
     */
    @Override
    public boolean selectFormat(VideoFormat format) {
        if (format == null) {
            return false;
        }
        if (!mSupportedFormats.contains(format)) {
            return false;
        }
        if (mSelectedFormat != null) {
            throw new UnsupportedOperationException("You can select format only once.");
        }
        mSelectedFormat = format;
        VideoFormat[] values = VideoFormat.values();
        for (VideoFormat value : values) {
            if (format == value) {
                continue;
            }
            int[] iTags = value.getITags();
            for (int iTag : iTags) {
                removeFormat(iTag);
            }
        }
        return true;
    }
}
