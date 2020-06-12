/**
 * Description:
 * Intercepts user agent headers
 */

console.log("Scripts::Running script video_info_interceptor.js");

function VideoInfoInterceptor() {
    this.TAG = 'VideoInfoInterceptor';
    this.VIDEO_INFO_URL = '/get_video_info?';
    this.shouldIntercept = true;

    // this.modifyOpen = function(rawArgs) {
    //     var url = rawArgs[1];
    //
    //     if (url && url.indexOf(this.VIDEO_INFO_URL) >= 0) {
    //         switch (window.VideoStatsWatcherAddon.recentVideoType) {
    //             case window.VideoStatsWatcherAddon.VIDEO_TYPE_DEFAULT:
    //                 Log.d(this.TAG, "Fix age restrictions... " + url);
    //                 // unlock age restricted videos but locks some streams (use carefully!!!)
    //                 url = url.replace("&el=leanback", "");
    //                 url = url.replace("&ps=leanback", "");
    //                 break;
    //             case window.VideoStatsWatcherAddon.VIDEO_TYPE_LIVE:
    //             case window.VideoStatsWatcherAddon.VIDEO_TYPE_UPCOMING:
    //             case window.VideoStatsWatcherAddon.VIDEO_TYPE_UNDEFINED:
    //                 // stream unlocking should happening in ExoInterceptor.java
    //                 // otherwise you'll get playback errors in youtube
    //                 //url = url.replace("&c=TVHTML5", "&c=HTML5");
    //                 break;
    //         }
    //
    //         rawArgs[1] = url;
    //     }
    // };
}
