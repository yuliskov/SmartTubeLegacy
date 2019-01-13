package com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser;

import java.util.HashMap;
import java.util.Map;

/*

Video quality matrix:

format code  extension  resolution note
249          webm       audio only DASH audio   53k , opus @ 50k, 1.28MiB
250          webm       audio only DASH audio   67k , opus @ 70k, 1.48MiB
171          webm       audio only DASH audio  107k , vorbis@128k, 2.42MiB
251          webm       audio only DASH audio  119k , opus @160k, 2.84MiB
140          m4a        audio only DASH audio  128k , m4a_dash container, mp4a.40.2@128k, 3.49MiB
160          mp4        256x144    144p  110k , avc1.42c00c, 15fps, video only, 2.99MiB
278          webm       256x144    144p  114k , webm container, vp9, 30fps, video only, 2.59MiB
133          mp4        426x240    240p  253k , avc1.4d4015, 30fps, video only, 6.73MiB
242          webm       426x240    240p  267k , vp9, 30fps, video only, 5.59MiB
243          webm       640x360    360p  488k , vp9, 30fps, video only, 10.40MiB
134          mp4        640x360    360p  611k , avc1.4d401e, 30fps, video only, 11.82MiB
244          webm       854x480    480p  916k , vp9, 30fps, video only, 18.84MiB
135          mp4        854x480    480p 1123k , avc1.4d401f, 30fps, video only, 23.11MiB
247          webm       1280x720   720p 1905k , vp9, 30fps, video only, 37.94MiB
136          mp4        1280x720   720p 2253k , avc1.4d401f, 30fps, video only, 44.70MiB
302          webm       1280x720   720p60 3137k , vp9, 60fps, video only, 63.14MiB
298          mp4        1280x720   720p60 3325k , avc1.4d4020, 60fps, video only, 74.19MiB
248          webm       1920x1080  1080p 3196k , vp9, 30fps, video only, 67.74MiB
137          mp4        1920x1080  1080p 4224k , avc1.640028, 30fps, video only, 81.71MiB
303          webm       1920x1080  1080p60 5129k , vp9, 60fps, video only, 109.29MiB
299          mp4        1920x1080  1080p60 5537k , avc1.64002a, 60fps, video only, 128.89MiB
264          mp4        2560x1440  1440p 10125k , avc1.640032, 30fps, video only, 192.97MiB
271          webm       2560x1440  1440p 10498k , vp9, 30fps, video only, 209.84MiB
308          webm       2560x1440  1440p60 14676k , vp9, 60fps, video only, 319.24MiB
266          mp4        3840x2160  2160p 22834k , avc1.640033, 30fps, video only, 491.75MiB
313          webm       3840x2160  2160p 26094k , vp9, 30fps, video only, 483.65MiB
315          webm       3840x2160  2160p60 31943k , vp9, 60fps, video only, 701.52MiB
17           3gp        176x144    small , mp4v.20.3, mp4a.40.2@ 24k
36           3gp        320x180    small , mp4v.20.3, mp4a.40.2
43           webm       640x360    medium , vp8.0, vorbis@128k
18           mp4        640x360    medium , avc1.42001E, mp4a.40.2@ 96k
22           mp4        1280x720   hd720 , avc1.64001F, mp4a.40.2@192k (best)


*/

public enum VideoFormat {
    // order is matter: first itag - mp4, second one - webm, 0 - not found
    _144p_(160, 278), _240p_(133, 242), _360p_(134, 243), _480p_(135, 244), _720p_(136, 247), _720p60_(298, 302), 
    _1080p_(137, 248), _1080p60_(299, 303), _1440p_(264, 271), _1440p60_(0, 308), _2160p(266, 313), _2160p60(0, 315), _Auto_();
    
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
