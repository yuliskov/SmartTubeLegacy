/**
 * Description:
 * Intercepts user agent headers
 */

console.log("Scripts::Running script video_info_interceptor.js");

function VideoInfoInterceptor() {
    this.TAG = 'VideoInfoInterceptor';
    this.VIDEO_INFO_URL = '/get_video_info?';
    this.shouldIntercept = true;

    this.modifyOpen = function(rawArgs) {
        if (window.VideoStatsWatcherAddon.recentVideoData) {
            var url = rawArgs[1];

            if (Utils.contains(url, this.VIDEO_INFO_URL)) {
                if (YouTubeUtils.isAgeRestrictedVideo(window.VideoStatsWatcherAddon.recentVideoData)) {
                    // unlock age restricted videos but locks some streams (use carefully!!!)
                    url = url.replace("&el=leanback", "");
                }

                rawArgs[1] = url;
            }
        }
    };
}
