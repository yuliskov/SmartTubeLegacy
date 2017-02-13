package com.liskovsoft.smartyoutubetv.helpers;

import android.webkit.WebResourceResponse;
import okhttp3.Response;
import java.io.InputStream;

/*

Video quality matrix:

format code  extension  resolution note
249          webm       audio only DASH audio   52k , opus @ 50k (48000Hz), 5.66MiB
250          webm       audio only DASH audio   62k , opus @ 70k (48000Hz), 6.48MiB
171          webm       audio only DASH audio  105k , vorbis@128k (44100Hz), 10.24MiB
251          webm       audio only DASH audio  115k , opus @160k (48000Hz), 11.67MiB
140          m4a        audio only DASH audio  128k , m4a_dash container, mp4a.40.2@128k (44100Hz), 14.19MiB
278          webm       256x144    DASH video  114k , webm container, vp9, 25fps, video only, 9.66MiB
160          mp4        256x144    DASH video  117k , avc1.4d400c, 25fps, video only, 12.45MiB
242          webm       426x240    DASH video  232k , vp9, 25fps, video only, 11.71MiB
133          mp4        426x240    DASH video  255k , avc1.4d4015, 25fps, video only, 27.51MiB
243          webm       640x360    DASH video  403k , vp9, 25fps, video only, 22.04MiB
134          mp4        640x360    DASH video  590k , avc1.4d401e, 25fps, video only, 32.14MiB
244          webm       854x480    DASH video  640k , vp9, 25fps, video only, 35.64MiB
135          mp4        854x480    DASH video 1021k , avc1.4d401e, 25fps, video only, 68.81MiB
247          webm       1280x720   DASH video 1188k , vp9, 25fps, video only, 76.93MiB
136          mp4        1280x720   DASH video 1818k , avc1.4d401f, 25fps, video only, 140.87MiB
248          webm       1920x1080  DASH video 2158k , vp9, 25fps, video only, 160.61MiB
137          mp4        1920x1080  DASH video 3479k , avc1.640028, 25fps, video only, 275.97MiB
17           3gp        176x144    small , mp4v.20.3,  mp4a.40.2@ 24k
36           3gp        320x180    small , mp4v.20.3,  mp4a.40.2
43           webm       640x360    medium , vp8.0,  vorbis@128k
18           mp4        640x360    medium , avc1.42001E,  mp4a.40.2@ 96k
22           mp4        1280x720   hd720 , avc1.64001F,  mp4a.40.2@192k (best)


*/


public class VideoQualityInterceptor extends RequestInterceptor {
    @Override
    public boolean test(String url) {
        // trying to manipulate with video formats
        if (url.contains("get_video_info")) {
            return true;
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        Response response = doOkHttpRequest(url);
        VideoInfoBuilder videoInfoBuilder = new VideoInfoBuilder(response.body().byteStream());
        videoInfoBuilder.removeFormat(248);
        videoInfoBuilder.removeFormat(137);
        videoInfoBuilder.removeFormat(247);
        videoInfoBuilder.removeFormat(136);
        videoInfoBuilder.removeFormat(244);
        videoInfoBuilder.removeFormat(135);
        InputStream is = videoInfoBuilder.get();

        WebResourceResponse resourceResponse = new WebResourceResponse(
                getMimeType(response.body().contentType()),
                getCharset(response.body().contentType()),
                is
        );
        return resourceResponse;
    }
}
