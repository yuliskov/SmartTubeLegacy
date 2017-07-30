package com.liskovsoft.smartyoutubetv.youtubeinfoparser;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.TestHelpers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SimpleYouTubeInfoParserTest {
    private InputStream mYouTubeVideoInfo;
    private final static String sVideo = "https://r3---sn-4gxb5u-qo3s.googlevideo" +
            ".com/videoplayback?mime=video%2Fmp4&gir=yes&requiressl=yes&keepalive=yes&expire=1486847157&lmt=1486732669523566&beids=%5B9452306%5D" +
            "&clen=59599129&upn=B35v84KPddc&gcr=ua&source=youtube&key=yt6&ei=VSifWL7PIsbvdLa5oWg&pl=22&mn=sn-4gxb5u-qo3s&mm=31&ipbits=0&ip" +
            "=46.98.220.156&id=o-AJ3zQvDyh9Rht0kbbWk4ay4WTBxqSt1CyJxAZd8C8Tsx&sparams=clen%2Cdur%2Cei%2Cgcr%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits" +
            "%2Citag%2Ckeepalive%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Crequiressl%2Csource%2Cupn%2Cexpire&ms=au&itag=137&initcwndbps=3295000&dur" +
            "=242.575&mv=m&mt=1486825524";
    private final static String sAudio = "https://r3---sn-4gxb5u-qo3s.googlevideo" +
            ".com/videoplayback?mime=audio%2Fmp4&gir=yes&requiressl=yes&keepalive=yes&expire=1486847157&lmt=1486732653395805&beids=%5B9452306%5D" +
            "&clen=3854181&upn=B35v84KPddc&gcr=ua&source=youtube&key=yt6&ei=VSifWL7PIsbvdLa5oWg&pl=22&mn=sn-4gxb5u-qo3s&mm=31&ipbits=0&ip" +
            "=46.98.220.156&id=o-AJ3zQvDyh9Rht0kbbWk4ay4WTBxqSt1CyJxAZd8C8Tsx&sparams=clen%2Cdur%2Cei%2Cgcr%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits" +
            "%2Citag%2Ckeepalive%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Crequiressl%2Csource%2Cupn%2Cexpire&ms=au&itag=140&initcwndbps=3295000&dur" +
            "=242.625&mv=m&mt=1486825524";


    @Before
    public void setUp() throws Exception {
        mYouTubeVideoInfo = TestHelpers.openResource("get_video_info_origin");
    }

    @Test
    public void tryToExtractSomeUrls() throws Exception {
        YouTubeInfoParser parser = new SimpleYouTubeInfoParser(mYouTubeVideoInfo);
        Uri url = parser.getUrlByTag(YouTubeInfoParser.VIDEO_1080P_AVC);
        assertTrue(url.equals(Uri.parse(sVideo)));
        Uri url2 = parser.getUrlByTag(YouTubeInfoParser.AUDIO_128K_AAC);
        assertTrue(url2.equals(Uri.parse(sAudio)));
    }

}