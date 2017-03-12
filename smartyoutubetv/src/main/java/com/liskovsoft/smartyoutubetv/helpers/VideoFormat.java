package com.liskovsoft.smartyoutubetv.helpers;

import java.util.HashMap;
import java.util.Map;

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

public enum VideoFormat {
    // order is matter: first itag - mp4, second one - webm
    _144p_(160, 278), _240p_(133, 242), _360p_(134, 243), _480p_(135, 244), _720p_(136, 247), _1080p_(137, 248), _1440p_(264, 271), _2160p(266, 313), _Auto_();
    private static Map<Integer, VideoFormat> mStrValMap = new HashMap<>();

    private final int[] mITags;

    private VideoFormat(int ...iTags) {
        mITags = iTags;
    }

    static {
        for(final VideoFormat format : VideoFormat.values()) {
            int[] iTags = format.mITags;
            for (int iTag : iTags) {
                mStrValMap.put(iTag, format);
            }
        }
    }

    public int[] getITags() {
        return mITags;
    }
    
    @Override
    public String toString() {
        return super.toString().replace("_", "");
    }

    public static VideoFormat fromName(String name) {
        if (name == null) {
            return null;
        }
        return VideoFormat.valueOf(String.format("_%s_", name));
    }

    public static VideoFormat fromITag(String strITag) {
        if (strITag == null) {
            return null;
        }

        int iTag = Integer.valueOf(strITag);

        if(!mStrValMap.containsKey(iTag)) {
            return null;
        }

        return mStrValMap.get(iTag);
    }

    public int getResolution() {
        if (this == _Auto_) {
            return 999; // set 'AUTO' as rightmost value
        }

        int resolution = Integer.parseInt(toString().replace("p", ""));
        return resolution;
    }
}
