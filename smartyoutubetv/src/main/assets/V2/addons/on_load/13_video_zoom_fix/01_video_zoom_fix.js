/**
 * Description:
 * Fix low-res videos only show a corner of the screen (zoomed video)
 */

console.log("Scripts::Running script video_zoom_fix.js");

function VideoZoomFixAddon() {
    this.run = function() {
        EventUtils.addPlaybackListener(this);
    };

    this.onPlaybackEvent = function() {
        // on any key event
        this.fixZoomedVideo();
    };

    this.fixZoomedVideo = function() {
        var myVideo = Utils.$('video');
        if (!myVideo)
            return;
        var isHeightBig = parseInt(myVideo.style.height) > window.innerHeight;
        var isWidthBig = parseInt(myVideo.style.width) > window.innerWidth;
        if (isHeightBig || isWidthBig) {
            console.log("VideoZoomFixAddon::video is zoomed out: fixing...");
            // do set width/height the same as viewport
            myVideo.style.height = window.innerHeight + 'px';
            myVideo.style.width = window.innerWidth + 'px';
            // element could be letterboxed
            myVideo.style.top = '0px';
            return;
        }
        console.log("VideoZoomFixAddon::everything seems normal: video not zoomed");
    };
}

if (!DeviceUtils.isExo())
    new VideoZoomFixAddon().run();